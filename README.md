# eob-edit

A Java savegame editor for **Eye of the Beholder** (DOS, 1991) by Westwood/TSR. It reads and modifies the binary `EOBDATA.SAV` file at the byte level, letting you tweak character stats, inventory, and more through an interactive Swing GUI.

## Features

- **Stats editing** — Modify STR, INT, WIS, DEX, CON, CHA, HP (current/max), and AC for each character
- **Inventory management** — View and swap items across 27 inventory slots (including equipment and pack) with item images and searchable dropdowns
- **Searchable selection** — Click on any item icon or use the "..." button to open a searchable dialog, making it easy to find specific items among the 400+ available prototypes
- **6-character support** — Switch between all six party slots via a player selector
- **Unsaved changes tracking** — Title bar indicator and confirmation dialogs prevent accidental data loss
- **Game data loading** — Load items directly from the game's PAK files (ITEM.DAT, EOBPAL.COL, ITEMICN.CPS) for authoritative item names, types, and icons
- **Persistent settings** — Game data path is remembered across sessions

## Prerequisites

- Java 25 or later
- Maven 3.x

## Build

```bash
mvn clean package
```

This produces a runnable fat JAR at `target/eob-edit-0.1.0.jar`.

## Run

Open the GUI with a file chooser:

```bash
java -jar target/eob-edit-0.1.0.jar
```

Or open a savegame directly:

```bash
java -jar target/eob-edit-0.1.0.jar /path/to/EOBDATA.SAV
```

To also load item data from the original game files (highly recommended for inventory editing):

```bash
java -jar target/eob-edit-0.1.0.jar /path/to/EOBDATA.SAV --game-data /path/to/eob-game-directory
```

You can also set the game data path at runtime via **Settings > Set Game Data Path...**. The path is persisted automatically for future sessions.

An example savegame is included at `src/main/resources/EOBDATA.SAV` for testing.

## Tests

```bash
mvn test
```

## Project Structure

```
src/main/java/com/github/martinfrank/eobedit/
  App.java              — Entry point, launches Swing GUI
  data/                 — SavegameFile, PlayerData, GlobalItem, Items, Stat, enums
  event/                — Observer pattern for change tracking
  gui/                  — EditorFrame, StatsPanel, InventoryPanel
  image/                — ImageProvider (item/portrait PNGs)
  pak/                  — PakReader, CpsDecoder, EobItemLoader (game file parsing)
  tools/                — ScreenShotSnipper (extracts item images from screenshots)

src/main/resources/
  item/                 — Fallback item icon PNGs
  portrait/             — Fallback portrait PNGs
  hex-editing-guide.txt — Reference: EoB1 hex codes (GameFAQs)
  EOBDATA.SAV           — Example savegame for testing
```

## License

GPL v3
