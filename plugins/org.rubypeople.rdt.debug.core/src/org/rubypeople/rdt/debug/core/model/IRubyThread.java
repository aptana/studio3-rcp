package org.rubypeople.rdt.debug.core.model;

import org.eclipse.debug.core.model.IThread;

public interface IRubyThread extends IThread
{

	void queueRunnable(Runnable runnable);

}
