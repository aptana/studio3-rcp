package com.aptana.editor.idl;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aptana.editor.idl.parsing.lexer.IDLTokenType;

public class IDLSouceScannerTests
{
	private IDLSourceScanner _scanner;

	@Before
	public void setUp() throws Exception
	{

		this._scanner = new IDLSourceScanner()
		{
			/*
			 * (non-Javadoc)
			 * @see com.aptana.editor.dtd.DTDSourceScanner#createToken(com.aptana.editor.dtd.parsing.lexer.DTDTokenType)
			 */
			@Override
			protected IToken createToken(IDLTokenType type)
			{
				return new Token(type);
			}
		};
	}

	@After
	public void tearDown() throws Exception
	{
		this._scanner = null;
	}

	/**
	 * typeTests
	 * 
	 * @param source
	 * @param types
	 */
	public void typeTests(String source, IDLTokenType... types)
	{
		IDocument document = new Document(source);

		this._scanner.setRange(document, 0, source.length());

		for (IDLTokenType type : types)
		{
			IToken token = this._scanner.nextToken();
			Object data = token.getData();

			assertEquals(type, data);
		}
	}

	@Test
	public void testString()
	{
		String source = "\"This is a string\""; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.STRING);
	}

	@Test
	public void testDocumentationComment()
	{
		String source = "/** this is a documentation comment */"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.DOC_COMMENT);
	}

	@Test
	public void testMultilineComment()
	{
		String source = "/* this is a multi-line comment */"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.MULTILINE_COMMENT);
	}

	@Test
	public void testDoubleColon()
	{
		String source = "::"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.DOUBLE_COLON);
	}

	@Test
	public void testEllipsis()
	{
		String source = "..."; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.ELLIPSIS);
	}

	@Test
	public void testAny()
	{
		String source = "any"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.ANY);
	}

	@Test
	public void testAttribute()
	{
		String source = "attribute"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.ATTRIBUTE);
	}

	@Test
	public void testBoolean()
	{
		String source = "boolean"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.BOOLEAN);
	}

	@Test
	public void testCaller()
	{
		String source = "caller"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.CALLER);
	}

	@Test
	public void testConst()
	{
		String source = "const"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.CONST);
	}

	@Test
	public void testCreator()
	{
		String source = "creator"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.CREATOR);
	}

	@Test
	public void testDeleter()
	{
		String source = "deleter"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.DELETER);
	}

	@Test
	public void testDOMString()
	{
		String source = "DOMString"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.DOMSTRING);
	}

	@Test
	public void testDouble()
	{
		String source = "double"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.DOUBLE);
	}

	@Test
	public void testException()
	{
		String source = "exception"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.EXCEPTION);
	}

	@Test
	public void testFalse()
	{
		String source = "false"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.FALSE);
	}

	@Test
	public void testFloat()
	{
		String source = "float"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.FLOAT);
	}

	@Test
	public void testGetRaises()
	{
		String source = "getraises"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.GETRAISES);
	}

	@Test
	public void testGetter()
	{
		String source = "getter"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.GETTER);
	}

	@Test
	public void testImplements()
	{
		String source = "implements"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.IMPLEMENTS);
	}

	@Test
	public void testIn()
	{
		String source = "in"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.IN);
	}

	@Test
	public void testInterface()
	{
		String source = "interface"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.INTERFACE);
	}

	@Test
	public void testLong()
	{
		String source = "long"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.LONG);
	}

	@Test
	public void testModule()
	{
		String source = "module"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.MODULE);
	}

	@Test
	public void testObject()
	{
		String source = "Object"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.OBJECT);
	}

	@Test
	public void testOctet()
	{
		String source = "octet"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.OCTET);
	}

	@Test
	public void testOmittable()
	{
		String source = "omittable"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.OMITTABLE);
	}

	@Test
	public void testOptional()
	{
		String source = "optional"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.OPTIONAL);
	}

	@Test
	public void testRaises()
	{
		String source = "raises"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.RAISES);
	}

	@Test
	public void testReadOnly()
	{
		String source = "readonly"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.READONLY);
	}

	@Test
	public void testSequence()
	{
		String source = "sequence"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.SEQUENCE);
	}

	@Test
	public void testSetRaises()
	{
		String source = "setraises"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.SETRAISES);
	}

	@Test
	public void testSetter()
	{
		String source = "setter"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.SETTER);
	}

	@Test
	public void testShort()
	{
		String source = "short"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.SHORT);
	}

	@Test
	public void testStringifier()
	{
		String source = "stringifier"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.STRINGIFIER);
	}

	@Test
	public void testTrue()
	{
		String source = "true"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.TRUE);
	}

	@Test
	public void testTypeDef()
	{
		String source = "typedef"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.TYPEDEF);
	}

	@Test
	public void testUnsigned()
	{
		String source = "unsigned"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.UNSIGNED);
	}

	@Test
	public void testVoid()
	{
		String source = "void"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.VOID);
	}

	@Test
	public void testLeftCurly()
	{
		String source = "{"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.LCURLY);
	}

	@Test
	public void testRightCurly()
	{
		String source = "}"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.RCURLY);
	}

	@Test
	public void testSemicolon()
	{
		String source = ";"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.SEMICOLON);
	}

	@Test
	public void testColon()
	{
		String source = ":"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.COLON);
	}

	@Test
	public void testLessThan()
	{
		String source = "<"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.LESS_THAN);
	}

	@Test
	public void testGreaterThan()
	{
		String source = ">"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.GREATER_THAN);
	}

	@Test
	public void testEqual()
	{
		String source = "="; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.EQUAL);
	}

	@Test
	public void testLeftParen()
	{
		String source = "("; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.LPAREN);
	}

	@Test
	public void testRightParen()
	{
		String source = ")"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.RPAREN);
	}

	@Test
	public void testComma()
	{
		String source = ","; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.COMMA);
	}

	@Test
	public void testLeftBracket()
	{
		String source = "["; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.LBRACKET);
	}

	@Test
	public void testRightBracket()
	{
		String source = "]"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.RBRACKET);
	}

	@Test
	public void testQuestion()
	{
		String source = "?"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.QUESTION);
	}

	@Test
	public void testIntegerNumber()
	{
		String source = "10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeIntegerNumber()
	{
		String source = "-10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testHexNumber()
	{
		String source = "0x10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeHexNumber()
	{
		String source = "-0x10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testOctalNumber()
	{
		String source = "007"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testOctalHexNumber()
	{
		String source = "-007"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testDoubleNumber()
	{
		String source = "10.10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testDoubleNumber2()
	{
		String source = ".10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeDoubleNumber()
	{
		String source = "-10.10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeDoubleNumber2()
	{
		String source = "-.10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testScientificNotation()
	{
		String source = "1.10e10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testScientificNotation2()
	{
		String source = "1.10e-10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testScientificNotation3()
	{
		String source = "1.10e+10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeScientificNotation()
	{
		String source = "1.10e10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeScientificNotation2()
	{
		String source = "1.10e-10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}

	@Test
	public void testNegativeScientificNotation3()
	{
		String source = "1.10e+10"; //$NON-NLS-1$

		this.typeTests(source, IDLTokenType.NUMBER);
	}
}
