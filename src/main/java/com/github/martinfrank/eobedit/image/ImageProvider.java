package com.github.martinfrank.eobedit.image;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.Portrait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ImageProvider {

    private static final String ITEM_DIR = "item/";
    private static final String PORTRAIT_DIR = "portrait/";
    private static final String GUI_DIR = "gui/";

    private static final String PNG = ".PNG";
    private static final String GUI_PAGE_A = "PAGE_A";
    private static final String GUI_PAGE_B = "PAGE_B";
    private static final String GUI_ITEM_BACK = "ITEM_BACK";
    private static final String PORT = "PORT_";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProvider.class);

    public BufferedImage getItem(Item item) {
        if (Items.isUsingGameData() && item.iconIndex >= 0) {
            BufferedImage icon = Items.getIcon(item.iconIndex);
            if (icon != null) return icon;
        }
        return loadImageOrNull(ITEM_DIR, item.getId() + PNG);
    }

    public BufferedImage getPortrait(Portrait portrait) {
        String id = (portrait.id < 0x10 ? "0" : "") + Integer.toHexString(portrait.id).toUpperCase(Locale.ROOT);
        return loadImageOrNull(PORTRAIT_DIR, PORT + id + PNG);
    }

    public BufferedImage getGuiPageA() {
        return loadImageOrNull(GUI_DIR, GUI_PAGE_A + PNG);
    }

    public BufferedImage getGuiPageB() {
        return loadImageOrNull(GUI_DIR, GUI_PAGE_B + PNG);
    }

    public BufferedImage getItemBackground() {
        return loadImageOrNull(GUI_DIR, GUI_ITEM_BACK + PNG);
    }

    private BufferedImage loadImageOrNull(String dir, String filename) {
        try {
            String resourceName = dir+filename;
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
            if(is == null){
                LOGGER.debug("cannot create input stream for image file {}{}, returned image=null!",dir,filename);
                return null;
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            LOGGER.debug("cannot read image file {}{}, returned image=null!",dir,filename);
            return null;
        }
    }
}
