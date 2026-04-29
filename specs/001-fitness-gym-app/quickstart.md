# Quickstart: 001 - Fitness Gym Android App (Milestone 1)

## Prerequisites

- Android Studio (latest stable)
- JDK 11 (matches current Gradle config)

## Run the app

1. Open the project root folder in Android Studio:
   - Open the repository root folder (this repository)
2. Select the `app` run configuration.
3. Run on an emulator/device (min SDK 26).

Expected flow:

- App starts on **Landing**.
- Tap **Continue as guest** → goes to **Main** with **Home** selected.
- Tap **Log in** → shows **Login coming soon** placeholder.

## Run tests

### JVM unit tests (Domain/Data)

From repo root:

```bash
./gradlew test
```

### Compose UI tests (instrumented)

```bash
./gradlew connectedAndroidTest
```

## Mock JSON dataset

- Location (planned): `app/src/main/assets/mock/home_content.json`
- Contract: `specs/001-fitness-gym-app/contracts/home-content-json.md`

This milestone’s Home tab is composed of 4 vertically scrollable sections (matching the design refs under `android_sdd_home/`):

1. Hero/Header (blue gradient + 2 CTAs)
2. Find a club
3. Membership types
4. Awards

If the JSON file is missing or malformed, the Home screen should show an error state with a retry action.


