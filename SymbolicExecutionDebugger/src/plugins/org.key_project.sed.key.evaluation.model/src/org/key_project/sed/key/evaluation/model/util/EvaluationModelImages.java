/*******************************************************************************
 * Copyright (c) 2014 Karlsruhe Institute of Technology, Germany
 *                    Technical University Darmstadt, Germany
 *                    Chalmers University of Technology, Sweden
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Technical University Darmstadt - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.key_project.sed.key.evaluation.model.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.key_project.sed.key.evaluation.model.Activator;
import org.key_project.util.eclipse.BundleUtil;

/**
 * <p>
 * Provides the images of the Evaluation model.
 * </p>
 * <p>
 * To get an image use one of the constant defined in this class, e.g.<br>
 * {@code EvaluationModelImages.getImage(EvaluationModelImages.JML_LOGO)))}
 * </p>
 * @author Martin Hentschel
 */
public final class EvaluationModelImages {
    /**
     * The key for the JML logo.
     */
    public static final String JML_LOGO = "org.key_project.sed.key.evaluation.model.jmlLogo";
    
    /**
     * The key for the SED logo.
     */
    public static final String SED_LOGO = "org.key_project.sed.key.evaluation.model.sedLogo";
    
    /**
     * The key for the KeY logo.
     */
    public static final String KEY_LOGO = "org.key_project.sed.key.evaluation.model.keyLogo";
    
    /**
     * The key for the thanks image.
     */
    public static final String KEY_THANKS = "org.key_project.sed.key.evaluation.model.keyThanks";
    
    /**
     * Forbid instances.
     */
    private EvaluationModelImages() {
    }
    
    /**
     * Returns the {@link Image} for the given key. The images are shared
     * with other plug-ins. The caller is <b>not</b> responsible for the destruction.
     * For this reason it is forbidden to call {@link Image#dispose()} on
     * a used {@link Image}.
     * @param key The key that identifies the needed {@link Image}. Use one of the constants provided by this class.
     * @return The {@link Image} or {@code null} if it was not possible to get it.
     */
    public static Image getImage(String key) {
        ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
        Image image = imageRegistry.get(key);
        if (image == null) {
            synchronized (imageRegistry) { // Make sure that the image is created only once
               image = imageRegistry.get(key); // Make sure that the image is still not available
               if (image == null) { 
                  image = createImage(key);
                  if (image != null) {
                     imageRegistry.put(key, image);
                  }
               }
            }
        }
        return image;
    }
    
    /**
     * Returns the {@link ImageDescriptor} for the given key.
     * @param key The key.
     * @return The {@link ImageDescriptor} or {@code null} if not available.
     */
    public static ImageDescriptor getImageDescriptor(String key) {
       ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
       ImageDescriptor descriptor = imageRegistry.getDescriptor(key);
       if (descriptor == null) {
          synchronized (imageRegistry) { // Make sure that the image is created only once
             descriptor = imageRegistry.getDescriptor(key); // Make sure that the image is still not available
             if (descriptor == null) {
                Image image = createImage(key);
                imageRegistry.put(key, image);
                descriptor = imageRegistry.getDescriptor(key);
             }
          } 
       }
       return descriptor;
    }

    /**
     * Creates an {@link Image} for the given key.
     * @param key The key that identifies the image. Use one of the constants provided by this class.
     * @return The created {@link Image} or {@code null} if it was not possible.
     */
    protected static Image createImage(String key) {
        // Compute path to image in bundle.
        String path = null;
        if (JML_LOGO.equals(key)) {
           path = "data/understandingProofAttempts/icons/jml-writing-16x16.png";
        }
        else if (SED_LOGO.equals(key)) {
           path = "data/understandingProofAttempts/icons/symbolic_debug.gif";
        }
        else if (KEY_LOGO.equals(key)) {
           path = "data/understandingProofAttempts/icons/logo16.gif";
        }
        else if (KEY_THANKS.equals(key)) {
           path = "data/understandingProofAttempts/icons/Thanks.png";
        }
        // Load image if possible
        if (path != null) {
           InputStream in = null;
           try {
              in = BundleUtil.openInputStream(Activator.PLUGIN_ID, path);
              return new Image(Display.getDefault(), in);
           }
           catch (IOException e) {
              LogUtil.getLogger().logError(e);
              return null;
           }
           finally {
              try {
                 if (in != null) {
                    in.close();
                }
             }
             catch (IOException e) {
                LogUtil.getLogger().logError(e);
             }
           }
        }
        else {
           return null;
        }
    }

   /**
    * Returns a scaled version of the {@link Image} registered under the given key.
    * @param key The key.
    * @param scaleFactor The scale factor.
    * @return The new image.
    */
   public static ImageData getImage(String key, int scaleFactor) {
      Image image = getImage(key);
      if (image != null) {
         if (scaleFactor < 0) {
            scaleFactor = scaleFactor * -1;
         }
         ImageData data = image.getImageData();
         int newWidth = data.width / 100 * scaleFactor;
         int newHeight = data.height / 100 * scaleFactor;
         return data.scaledTo(newWidth, newHeight);
      }
      else {
         return null;
      }
   }
}