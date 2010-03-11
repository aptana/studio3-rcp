package com.aptana.ruby.internal.debug.core.commands;

import java.io.IOException;

import com.aptana.ruby.internal.debug.core.DebuggerNotFoundException;
import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.BreakpointModificationReader;
import com.aptana.ruby.internal.debug.core.parsing.XmlStreamReader;

public class BreakpointCommand  extends AbstractCommand {
		
	public BreakpointCommand(String command) {
		super(command, true) ;
	}

	@Override
	protected XmlStreamReader createResultReader(AbstractReadStrategy readStrategy) {
		return new BreakpointModificationReader(readStrategy) ;
	}
	
	public BreakpointModificationReader getBreakpointAddedReader() {
		return (BreakpointModificationReader) getResultReader() ;
	}
	
	public int executeWithResult(AbstractDebuggerConnection connection) throws DebuggerNotFoundException, IOException {
		execute(connection) ;
		return getBreakpointAddedReader().readBreakpointNo() ;
	}
}
