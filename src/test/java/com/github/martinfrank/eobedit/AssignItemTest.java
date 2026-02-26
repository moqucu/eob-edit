package com.github.martinfrank.eobedit;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.SavegameFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Verifies that SavegameFile.assignItem() correctly allocates a free global item
 * slot instead of writing the prototype index directly into the inventory slot.
 *
 * Regression test for: Kenku Eggs (and any item) assigned to wrong global item
 * slot — shows as incorrect item (issue #12).
 */
public class AssignItemTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    /** Minimal valid savegame: 6 chars × 243 bytes + 64 gap bytes + 500 items × 14 bytes. */
    private static final int CHAR_BLOCK   = SavegameFile.AMOUNT_CHARACTERS * SavegameFile.CHARACTER_DATA_LENGTH;
    private static final int TOTAL_SIZE   = SavegameFile.GLOBAL_ITEMS_OFFSET
                                            + SavegameFile.GLOBAL_ITEM_COUNT * SavegameFile.GLOBAL_ITEM_SIZE;

    private File saveFile;
    private SavegameFile sgf;

    @Before
    public void setUp() throws IOException {
        byte[] data = new byte[TOTAL_SIZE];
        // Mark character 0 as active (DATA_INDICATOR at offset 1 within char block)
        data[1] = 1;
        saveFile = tmp.newFile("EOBDATA.SAV");
        Files.write(saveFile.toPath(), data);

        sgf = new SavegameFile();
        sgf.load(saveFile);
    }

    /**
     * Builds a minimal Item prototype that mimics KENKU_EGG_A (game-data style,
     * where id[] encodes the prototype index 0xB6 = 182).
     */
    private Item kenkuEggPrototype() {
        // id bytes: low=0xB6, high=0x00  →  prototype index 182
        Item proto = new Item(182, "Kenku Egg", Items.ItemType.ITEM, 7);
        proto.nameId   = 42;   // arbitrary name-table index
        proto.nameUnid = 41;   // different unidentified name-table index
        proto.rawType  = 0;    // IT = 0
        return proto;
    }

    @Test
    public void assignItemWritesToFreeGlobalSlot() {
        Item proto = kenkuEggPrototype();

        // proto id encodes index 182 — that must NOT end up in the inventory slot
        int protoIndex = (proto.id[0] & 0xFF) | ((proto.id[1] & 0xFF) << 8);
        Assert.assertEquals(182, protoIndex);

        sgf.assignItem(0, 0, proto);

        int storedGlobalIdx = sgf.getPlayer(0).getInventoryIndex(0);

        // The inventory slot must NOT contain the bare prototype index
        Assert.assertNotEquals(
                "Inventory slot must not hold the prototype index directly",
                protoIndex, storedGlobalIdx);

        // It must point to a valid, non-zero global slot
        Assert.assertTrue("Global index must be > 0", storedGlobalIdx > 0);
        Assert.assertTrue("Global index must be within pool",
                storedGlobalIdx < SavegameFile.GLOBAL_ITEM_COUNT);
    }

    @Test
    public void assignItemPopulatesGlobalSlotCorrectly() {
        Item proto = kenkuEggPrototype();
        sgf.assignItem(0, 0, proto);

        int storedGlobalIdx = sgf.getPlayer(0).getInventoryIndex(0);
        var gi = sgf.getGlobalItems()[storedGlobalIdx];

        Assert.assertEquals("nameId must match prototype",    proto.nameId,   gi.nameId);
        Assert.assertEquals("nameUnid must match prototype",  proto.nameUnid, gi.nameUnid);
        Assert.assertEquals("type must match prototype",      proto.rawType,  gi.type);
        Assert.assertEquals("icon must match prototype",      proto.iconIndex, gi.icon);
        Assert.assertEquals("level must be 0xFF (carried)",  0xFF,           gi.level);
    }

    @Test
    public void clearInventorySlotZerosGlobalEntry() {
        Item proto = kenkuEggPrototype();
        sgf.assignItem(0, 0, proto);

        int storedGlobalIdx = sgf.getPlayer(0).getInventoryIndex(0);
        Assert.assertTrue(storedGlobalIdx > 0);

        sgf.clearInventorySlot(0, 0);

        Assert.assertEquals("Inventory slot must be 0 after clear",
                0, sgf.getPlayer(0).getInventoryIndex(0));
        Assert.assertTrue("Global item entry must be empty after clear",
                sgf.getGlobalItems()[storedGlobalIdx].isEmpty());
    }
}
