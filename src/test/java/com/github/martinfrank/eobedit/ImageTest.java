package com.github.martinfrank.eobedit;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.Portrait;
import com.github.martinfrank.eobedit.image.ImageProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class ImageTest {

    private ImageProvider imageProvider = new ImageProvider();

@Ignore
public void testItems() {
        for (Item it : Items.ITEMS) {
            System.out.println("item " + it);
            Assert.assertNotNull(imageProvider.getItem(it));
        }
    }

    @Test
    public void testPortraits() {
        for (Portrait port : Portrait.values()) {
            System.out.println("portrait " + port);
            Assert.assertNotNull(imageProvider.getPortrait(port));
        }
    }

    @Test
    public void testGui() {
        Assert.assertNotNull(imageProvider.getGuiPageA());
        Assert.assertNotNull(imageProvider.getGuiPageB());

    }
}
