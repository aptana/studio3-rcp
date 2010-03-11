package org.rubypeople.rdt.internal.debug.core.model;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IExpression;
import org.eclipse.debug.core.model.IValue;
import org.rubypeople.rdt.debug.core.RdtDebugCorePlugin;
import org.rubypeople.rdt.debug.core.model.IRubyVariable;

//see RubyDebugTarget for the reason why PlatformObject is being extended
public class RubyExpression extends PlatformObject implements IExpression {

  private IRubyVariable inspectionResult;
  private String expression;
  public RubyExpression(String expression, IRubyVariable inspectionResult) {
    this.inspectionResult = inspectionResult;
    this.expression = expression;

  }

  public String getExpressionText() {
    return expression;
  }

  public IValue getValue() {
    try
	{
		return inspectionResult.getValue();
	}
	catch (DebugException e)
	{
		RdtDebugCorePlugin.log(e);
		return null;
	}
  }

  public IDebugTarget getDebugTarget() {    
    return inspectionResult.getDebugTarget();
  }

  public void dispose() {

  }

  public String getModelIdentifier() {
    return this.getDebugTarget().getModelIdentifier();
  }

  public ILaunch getLaunch() {
    return this.getDebugTarget().getLaunch();
  }

}
