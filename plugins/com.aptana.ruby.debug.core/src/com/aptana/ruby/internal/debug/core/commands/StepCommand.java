package com.aptana.ruby.internal.debug.core.commands;

import java.io.IOException;

import com.aptana.ruby.internal.debug.core.DebuggerNotFoundException;
import com.aptana.ruby.internal.debug.core.SuspensionPoint;
import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.SuspensionReader;
import com.aptana.ruby.internal.debug.core.parsing.XmlStreamReader;
import com.aptana.ruby.internal.debug.core.parsing.XmlStreamReaderException;
import org.xmlpull.v1.XmlPullParserException;

public class StepCommand extends AbstractCommand {

	
	public StepCommand(String command) {
		super(command, false);
	}

	@Override
	protected XmlStreamReader createResultReader(AbstractReadStrategy readStrategy) {
		return new SuspensionReader(readStrategy);
	}
	
	public SuspensionReader getSuspensionReader() {
		return (SuspensionReader) getResultReader() ;
	}
	
	public SuspensionPoint readSuspension(AbstractDebuggerConnection debuggerConnection) throws DebuggerNotFoundException, IOException, XmlPullParserException, XmlStreamReaderException {
		execute(debuggerConnection);
		return getSuspensionReader().readSuspension() ;
	}

}
