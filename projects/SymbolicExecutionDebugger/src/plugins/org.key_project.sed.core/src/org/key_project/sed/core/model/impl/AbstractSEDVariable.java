package org.key_project.sed.core.model.impl;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.key_project.sed.core.model.ISEDDebugTarget;
import org.key_project.sed.core.model.ISEDVariable;
import org.key_project.sed.core.util.LogUtil;

/**
 * Provides a basic implementation of {@link ISEDVariable}.
 * @author Martin Hentschel
 * @see ISEDVariable
 */
public abstract class AbstractSEDVariable extends AbstractSEDDebugElement implements ISEDVariable {
   /**
    * Constructor.
    * @param target The {@link ISEDDebugTarget} in that this element is contained.
    */
   public AbstractSEDVariable(ISEDDebugTarget target) {
      super(target);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean supportsValueModification() {
      return false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean verifyValue(IValue value) throws DebugException {
      return false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setValue(String expression) throws DebugException {
      throw new DebugException(LogUtil.getLogger().createErrorStatus("Value modification is not supported."));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setValue(IValue value) throws DebugException {
      throw new DebugException(LogUtil.getLogger().createErrorStatus("Value modification is not supported."));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean verifyValue(String expression) throws DebugException {
      return false;
   }
}