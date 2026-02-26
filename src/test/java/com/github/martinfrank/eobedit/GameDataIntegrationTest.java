package com.github.martinfrank.eobedit;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.SavegameFile;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Integration test using the real game files from /Users/seikenberg/Downloads/Game.
 * Skipped automatically when those files are not present.
 *
 * Regression test for issue #12: item assignment must route through
 * SavegameFile.assignItem() so that the correct global item slot is allocated,
 * rather than writing the prototype index directly into the inventory slot.
 */
public class GameDataIntegrationTest {

    private static final File GAME_DIR    = new File("/Users/seikenberg/Downloads/Game");
    private static final File SAVE_FILE   = new File(GAME_DIR, "EOBDATA.SAV");

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Before
    public void requireGameFiles() {
        Assume.assumeTrue("Game directory not present — skipping integration test",
                GAME_DIR.isDirectory() && SAVE_FILE.exists());
    }

    @Before
    public void loadGameData() throws IOException {
        Items.loadFromGameData(GAME_DIR);
        Assert.assertTrue("Expected items to be loaded from game data",
                Items.isUsingGameData());
    }

    // ------------------------------------------------------------------
    // helpers
    // ------------------------------------------------------------------

    private Item findKenkuEgg() {
        return Arrays.stream(Items.getAllItems())
                .filter(it -> it.description != null &&
                              it.description.toLowerCase().contains("kenku egg"))
                .findFirst()
                .orElse(null);
    }

    private SavegameFile loadSaveCopy() throws IOException {
        // Work on a disposable copy so we never touch the original
        File copy = tmp.newFile("EOBDATA_COPY.SAV");
        Files.write(copy.toPath(), Files.readAllBytes(SAVE_FILE.toPath()));
        SavegameFile sgf = new SavegameFile();
        sgf.load(copy);
        return sgf;
    }

    // ------------------------------------------------------------------
    // tests
    // ------------------------------------------------------------------

    @Test
    public void kenkuEggExistsInGameData() {
        Item egg = findKenkuEgg();
        Assert.assertNotNull("Kenku Egg must be present in game data", egg);
        System.out.println("Found item: " + egg.description +
                           "  id=" + egg.getId() +
                           "  nameId=" + egg.nameId +
                           "  nameUnid=" + egg.nameUnid +
                           "  rawType=" + egg.rawType +
                           "  iconIndex=" + egg.iconIndex);
    }

    @Test
    public void assignKenkuEggAllocatesNewGlobalSlot() throws IOException {
        Item egg = findKenkuEgg();
        Assume.assumeNotNull(egg);

        int protoIndex = (egg.id[0] & 0xFF) | ((egg.id[1] & 0xFF) << 8);

        SavegameFile sgf = loadSaveCopy();
        // Use character 0, pack slot 0 (slot index 2 = "Pack 1")
        int slot = 2;
        sgf.assignItem(0, slot, egg);

        int storedGlobalIdx = sgf.getPlayer(0).getInventoryIndex(slot);

        System.out.println("Kenku Egg prototype index : " + protoIndex);
        System.out.println("Stored global item index  : " + storedGlobalIdx);

        Assert.assertNotEquals(
                "Inventory slot must NOT hold the bare prototype index",
                protoIndex, storedGlobalIdx);
        Assert.assertTrue("Global index must be > 0", storedGlobalIdx > 0);
        Assert.assertTrue("Global index must be within pool",
                storedGlobalIdx < SavegameFile.GLOBAL_ITEM_COUNT);
    }

    @Test
    public void globalSlotHasCorrectItemData() throws IOException {
        Item egg = findKenkuEgg();
        Assume.assumeNotNull(egg);

        SavegameFile sgf = loadSaveCopy();
        int slot = 2;
        sgf.assignItem(0, slot, egg);

        int storedGlobalIdx = sgf.getPlayer(0).getInventoryIndex(slot);
        var gi = sgf.getGlobalItems()[storedGlobalIdx];

        System.out.println("GlobalItem nameId=" + gi.nameId +
                           " nameUnid=" + gi.nameUnid +
                           " type=" + gi.type +
                           " icon=" + gi.icon +
                           " level=0x" + Integer.toHexString(gi.level));

        Assert.assertEquals("nameId must match prototype",   egg.nameId,    gi.nameId);
        Assert.assertEquals("nameUnid must match prototype", egg.nameUnid,  gi.nameUnid);
        Assert.assertEquals("type must match prototype",     egg.rawType,   gi.type);
        Assert.assertEquals("icon must match prototype",     egg.iconIndex, gi.icon);
        Assert.assertEquals("level must be 0xFF (carried)", 0xFF,          gi.level);
    }

    @Test
    public void savedAndReloadedSlotPreservesKenkuEgg() throws IOException {
        Item egg = findKenkuEgg();
        Assume.assumeNotNull(egg);

        SavegameFile sgf = loadSaveCopy();
        int slot = 2;
        sgf.assignItem(0, slot, egg);
        sgf.save();

        // Reload from disk
        SavegameFile sgf2 = new SavegameFile();
        sgf2.load(sgf.getPlayer(0) != null ? new File(tmp.getRoot(), "EOBDATA_COPY.SAV") : SAVE_FILE);

        // Locate the copy we just saved
        File savedFile = new File(tmp.getRoot(), "EOBDATA_COPY.SAV");
        sgf2 = new SavegameFile();
        sgf2.load(savedFile);
        Items.loadFromGameData(GAME_DIR); // re-apply game data after reload

        int reloadedGlobalIdx = sgf2.getPlayer(0).getInventoryIndex(slot);
        var gi = sgf2.getGlobalItems()[reloadedGlobalIdx];

        System.out.println("After save+reload: globalIdx=" + reloadedGlobalIdx +
                           " nameId=" + gi.nameId + " type=" + gi.type);

        Assert.assertEquals("nameId must survive round-trip",   egg.nameId,   gi.nameId);
        Assert.assertEquals("nameUnid must survive round-trip", egg.nameUnid, gi.nameUnid);
        Assert.assertEquals("type must survive round-trip",     egg.rawType,  gi.type);
    }
}
