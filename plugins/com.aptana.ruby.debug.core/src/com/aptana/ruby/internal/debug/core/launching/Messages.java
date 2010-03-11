package com.aptana.ruby.internal.debug.core.launching;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{

	private static final String BUNDLE_NAME = "com.aptana.ruby.internal.debug.core.launching.messages"; //$NON-NLS-1$
	
	public static String SocketAttachConnector_Connecting____1;
	public static String SocketAttachConnector_Configuring_connection____1;
	public static String SocketAttachConnector_Port_unspecified_for_remote_connection__2;
	public static String SocketAttachConnector_Hostname_unspecified_for_remote_connection__4;
	public static String SocketAttachConnector_Establishing_connection____2;
	public static String SocketAttachConnector_Failed_to_connect_to_remote_VM_1;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
