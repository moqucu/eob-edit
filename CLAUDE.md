# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Eye of the Beholder Savegame Editor — a Java tool for reading and modifying Eye of the Beholder (DOS RPG) save files. It operates at the byte level on binary savegame files (6 characters per file, 243 bytes each, with fixed offsets for attributes like name, stats, inventory).

## Build & Test Commands

```bash
# Build
mvn clean compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=ImageTest

# Run a single test method
mvn test -Dtest=ImageTest#testPortraits

# Package
mvn package

# Run (requires savegame file path argument)
java -jar target/eob-edit-0.1.0.jar <savegame-file>
```

## Architecture

- **`data/`** — Core domain: `SavegameFile` handles binary I/O, `PlayerData` wraps per-character byte ranges with getter/setter methods at fixed offsets. `Items` is a large generated catalog (~460 items with type, class restrictions, image references). Enums: `Race`, `Profession`, `Alignment`, `Portrait`.
- **`event/`** — Observer pattern (`PlayerDataChangeEventListener`, `ChangeEventType`) for tracking unsaved modifications.
- **`image/`** — `ImageProvider` loads item/portrait/GUI images from classpath resources.
- **`generate/`** — `ItemsGenerator` regenerates `Items.java` from `resources/items.txt` and `resources/types.txt`.
- **`tools/`** — `ScreenShotSnipper` extracts item images from game screenshots.
- **`App.java`** — Entry point; loads savegame, modifies player data, saves.

## Key Conventions

- Java 8 source/target compatibility (Maven compiler settings)
- JUnit 4 for tests (not JUnit 5)
- Item images in `resources/item/` (469 PNGs), portraits in `resources/portrait/` (55 PNGs)
- `Items.java` is generated code — edit `items.txt`/`types.txt` and run `ItemsGenerator` instead of modifying directly
- License: GPL v3
