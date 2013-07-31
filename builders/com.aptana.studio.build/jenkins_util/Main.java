import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.aptana.core.util.Base64;
import com.aptana.core.util.IOUtil;

/**
 * Utility to "recover" encrypted values stored in Hudson/Jenkins. Handy for recovering passwords we've input into mask
 * passwords and forgotten.
 * 
 * @author cwilliams
 */
public class Main
{
	private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$
	private static final String MAGIC = "::::MAGIC::::"; //$NON-NLS-1$
	private static final String ALGORITHM = "AES"; //$NON-NLS-1$

	/**
	 * Contents of secret.key file under JEKINS_HOME folder
	 */
	private static final String SECRET_KEY_FILE_CONTENTS = "4d2b468acc3aeac8d47846f8e472bf4b49005cf5c711e28432c3154987641378"; //$NON-NLS-1$
	/**
	 * Contents of master.key file under jenkins "secrets" folder
	 */
	private static final String MASTER_KEY_CONTENTS = "788806b252128bc7951ddb7601dcb343fa0ab3439549939facc25bc88d183c7ea546cbb32ded8829bdb173fab9bdff6f0bad09108049c7de996ea91eb43544de97cf93af4b0783f3c0a9248b92ccc52c430dbd2c778bfbebcf14cf8141ae89d013b2237731c66e0fdf05df86736be293b802d1dc13586489b7882464c43d5629"; //$NON-NLS-1$
	/**
	 * The value we're attempting to decrypt. Found in JENKINS_HOME/jobs/<job-name>/config.xml
	 */
	private static final String STRING_TO_DECRYPT = "FBH9UYno2N5SIWV30iioXRAsG2AHqnDcImYx2wrFaFY="; //$NON-NLS-1$

	/**
	 * Grab the file from JENKINS_HOME/secrets/hudson.util.Secret to use here.
	 */
	public static void main(String[] args) throws Exception
	{
		// read master key and cipher
		SecretKey masterKey = toAes128Key(MASTER_KEY_CONTENTS);
		Cipher sym = getCipher(masterKey);
		InputStream is = new FileInputStream(new File("hudson.util.Secret")); //$NON-NLS-1$
		CipherInputStream cis = new CipherInputStream(is, sym);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IOUtil.pipe(cis, os);
		byte[] value = os.toByteArray();

		// truncate off ::MAGIC::
		int length = value.length - MAGIC.getBytes().length;
		byte[] payload = new byte[length];
		System.arraycopy(value, 0, payload, 0, length);
		SecretKeySpec secret = new SecretKeySpec(payload, 0, 128 / 8, ALGORITHM);

		// Decrypt our value using the master key and cipher.
		Cipher cipher = getCipher(secret);
		String s = decrypt(STRING_TO_DECRYPT, cipher);
		// Truncate off MAGIC!
		s = s.substring(0, s.length() - MAGIC.length());
		System.out.println(s); // Print out the decrypted value!
	}

	private static Cipher getCipher(SecretKey key)
	{
		try
		{
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher;
		}
		catch (GeneralSecurityException e)
		{
			throw new AssertionError(e);
		}
	}

	private static String tryDecrypt(Cipher cipher, byte[] in) throws UnsupportedEncodingException
	{
		try
		{
			return new String(cipher.doFinal(in), UTF_8);
		}
		catch (GeneralSecurityException e)
		{
			return null; // if the key doesn't match with the bytes, it can result in BadPaddingException
		}
	}

	/**
	 * Reverse operation of {@link #getEncryptedValue()}. Returns null if the given cipher text was invalid.
	 */
	private static String decrypt(String data, Cipher c)
	{
		if (data == null)
			return null;
		try
		{
			byte[] in = Base64.decode(data);
			String s = tryDecrypt(c, in);
			if (s != null)
				return s;

			// try our historical key for backward compatibility
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, toAes128Key(SECRET_KEY_FILE_CONTENTS));
			return tryDecrypt(cipher, in);
		}
		catch (GeneralSecurityException e)
		{
			return null;
		}
		catch (UnsupportedEncodingException e)
		{
			throw new Error(e); // impossible
		}
		catch (IOException e)
		{
			return null;
		}
	}

	/**
	 * Converts a string into 128-bit AES key.
	 * 
	 * @since 1.308
	 */
	private static SecretKey toAes128Key(String s)
	{
		try
		{
			// turn secretKey into 256 bit hash
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(s.getBytes(UTF_8));

			// Due to the stupid US export restriction JDK only ships 128bit version.
			return new SecretKeySpec(digest.digest(), 0, 128 / 8, ALGORITHM);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new Error(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new Error(e);
		}
	}
}
