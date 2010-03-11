package com.aptana.ruby.internal.debug.core.commands;

import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.EvalReader;
import com.aptana.ruby.internal.debug.core.parsing.XmlStreamReader;

public class EvalCommand extends AbstractCommand {

	public EvalCommand(String command, boolean isControl) {
		super(command, isControl);
	}

	@Override
	protected XmlStreamReader createResultReader(AbstractReadStrategy readStrategy) {
		return new EvalReader(readStrategy);
	}

	public EvalReader getEvalReader() {
		return (EvalReader) getResultReader() ;
	}
}
