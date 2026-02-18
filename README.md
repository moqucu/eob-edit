# eob-edit

A Java savegame editor for **Eye of the Beholder** (DOS, 1991) by Westwood/TSR. It reads and modifies the binary `EOBDATA.SAV` file at the byte level, letting you tweak character stats, inventory, and more through an interactive Swing GUI.

## Features

- **Stats editing** — Modify STR, INT, WIS, DEX, CON, CHA, HP (current/max), and AC for each character
- **Inventory management** — View and swap items across 14 inventory slots with item images and searchable dropdowns
- **6-character support** — Switch between all six party slots via a player selector
- **Unsaved changes tracking** — Title bar indicator and confirmation dialogs prevent accidental data loss
- **460+ item catalog** — Full item database with hex IDs cross-referenced against the [GameFAQs hex editing guide](src/main/resources/hex-editing-guide.txt)

## Prerequisites

- Java 8 or later
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

An example savegame is included at `src/main/resources/EOBDATA.SAV` for testing.

## Tests

```bash
mvn test
```

## Project Structure

```
src/main/java/com/github/martinfrank/eobedit/
  App.java              — Entry point, launches Swing GUI
  data/                 — SavegameFile, PlayerData, Items, Stat, enums
  event/                — Observer pattern for change tracking
  gui/                  — EditorFrame, StatsPanel, InventoryPanel
  image/                — ImageProvider (item/portrait PNGs)
  generate/             — ItemsGenerator (regenerates Items.java from items.txt)
  tools/                — ScreenShotSnipper (extracts item images from screenshots)

src/main/resources/
  items.txt             — Item catalog (hex IDs, types, descriptions)
  types.txt             — Item type definitions
  classes.txt           — Character class definitions
  item/                 — 469 item icon PNGs
  portrait/             — 55 portrait PNGs
  hex-editing-guide.txt — Reference: EoB1 hex codes (GameFAQs)
  EOBDATA.SAV           — Example savegame for testing
```

## Regenerating the Item Catalog

`Items.java` is generated code. To update it after editing `items.txt` or `types.txt`:

```bash
mvn compile exec:java -Dexec.mainClass="com.github.martinfrank.eobedit.generate.ItemsGenerator"
```

## License

GPL v3
