package org.key_project.sed.key.evaluation.model.definition;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.key_project.util.java.CollectionUtil;
import org.key_project.util.java.IFilter;
import org.key_project.util.java.ObjectUtil;

public abstract class AbstractEvaluation {
   private final String name;
   
   private final List<Tool> tools;
   
   private final List<AbstractForm> forms;

   public AbstractEvaluation(String name) {
      this.name = name;
      this.tools = computeTools();
      this.forms = (List<AbstractForm>)computeForms();
      for (AbstractForm form : forms) {
         form.setEvaluation(this);
      }
   }

   protected abstract List<Tool> computeTools();

   protected abstract List<AbstractForm> computeForms();

   public String getName() {
      return name;
   }
   
   public Tool[] getTools() {
      return tools.toArray(new Tool[tools.size()]);
   }
   
   public AbstractForm[] getForms() {
      return forms.toArray(new AbstractForm[forms.size()]);
   }

   public int countForms() {
      return forms.size();
   }
   
   public static AbstractEvaluation getEvaluationForName(String name) {
      if (UnderstandingProofAttemptsEvaluation.INSTANCE.getName().equals(name)) {
         return UnderstandingProofAttemptsEvaluation.INSTANCE;
      }
      else if (TestEvaluation.INSTANCE.getName().equals(name)) {
         return TestEvaluation.INSTANCE;
      }
      else {
         return null;
      }
   }

   public AbstractForm getForm(final String formName) {
      return CollectionUtil.search(forms, new IFilter<AbstractForm>() {
         @Override
         public boolean select(AbstractForm element) {
            return ObjectUtil.equals(formName, element.getName());
         }
      });
   }

   public Tool getTool(final String toolName) {
      return CollectionUtil.search(tools, new IFilter<Tool>() {
         @Override
         public boolean select(Tool element) {
            return ObjectUtil.equals(toolName, element.getName());
         }
      });
   }
   
   /**
    * Checks if the user interface is available.
    * @return {@code true} user interface available, {@code false} user interface not available.
    */
   protected static boolean isUIAvailable() {
      return Display.getDefault() != null && !Display.getDefault().isDisposed();
   }

}