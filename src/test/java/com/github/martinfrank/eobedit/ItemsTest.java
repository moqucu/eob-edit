package com.github.martinfrank.eobedit;

import com.github.martinfrank.eobedit.data.Items;
import org.junit.Assert;
import org.junit.Test;

public class ItemsTest {

    @Test
    public void testNoItemsWithoutGameData() {
        // Without game data loaded, getAllItems() returns an empty array
        // and getItem() returns null.
        Assert.assertEquals(0, Items.getAllItems().length);
        Assert.assertNull(Items.getItem(new byte[]{1, 0}));
    }

}
