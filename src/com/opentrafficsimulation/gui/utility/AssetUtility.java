/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opentrafficsimulation.gui.utility;

import com.opentrafficsimulation.gui.MainFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author macromania
 */
public class AssetUtility {

    private BufferedImage logo;

    public BufferedImage getLogo() {
        // Set window icon
        try {
            logo = ImageIO.read(this.getClass().getResource("/assets/IconImage.jpg"));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        return logo;
    }
}
