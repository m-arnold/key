package org.key_project.sed.key.evaluation.model.definition;


public abstract class AbstractPage {
   private final String name;
   
   private final String title;
   
   private final String message;
   
   private AbstractForm form;
   
   private final boolean wrapLayout;
   
   public AbstractPage(String name, String title, String message, boolean wrapLayout) {
      assert name != null;
      this.name = name;
      this.title = title;
      this.message = message;
      this.wrapLayout = wrapLayout;
   }
   
   public boolean isReadonly() {
      return false;
   }

   public String getName() {
      return name;
   }

   public String getTitle() {
      return title;
   }

   public String getMessage() {
      return message;
   }

   public AbstractForm getForm() {
      return form;
   }

   public boolean isWrapLayout() {
      return wrapLayout;
   }

   public void setForm(AbstractForm form) {
      assert this.form == null : "Form is already set.";
      this.form = form;
   }
}