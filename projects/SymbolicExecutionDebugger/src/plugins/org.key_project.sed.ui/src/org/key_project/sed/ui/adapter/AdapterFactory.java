package org.key_project.sed.ui.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.internal.ui.views.launch.LaunchView;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Provides a {@link TabbedPropertySheetPage} for given {@link LaunchView}s.
 * @author Martin Hentschel
 */
@SuppressWarnings("restriction")
public class AdapterFactory implements IAdapterFactory {
   /**
    * The used contributor ID in tabbed property sheet pages for the {@link LaunchView}.
    */
   public static final String LAUNCH_VIEW_CONTRIBUTOR_ID = "org.key_project.sed.launchViewPropertyContributor";

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Object adaptableObject, Class adapterType) {
      if (IPropertySheetPage.class.equals(adapterType)){
         if (adaptableObject instanceof LaunchView) {
            ITabbedPropertySheetPageContributor contributor = new ITabbedPropertySheetPageContributor() {
               @Override
               public String getContributorId() {
                  return LAUNCH_VIEW_CONTRIBUTOR_ID;
               }
            };
            return new TabbedPropertySheetPage(contributor, true);
         }
      }
      return null;
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("rawtypes")
   @Override
   public Class[] getAdapterList() {
      return new Class[] {IPropertySheetPage.class};
   }
}