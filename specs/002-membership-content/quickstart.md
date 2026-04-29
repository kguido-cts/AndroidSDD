# Quickstart: 002 - Membership Content Screen

## Prerequisites

- Android Studio (latest stable)
- JDK 11

## Run the app

1. Open the repository root folder in Android Studio.
2. Select the `app` run configuration.
3. Run on an emulator/device (min SDK 26).

Expected flow:

- App starts on **Landing**.
- Tap **Continue as guest** → goes to **Main**.
- Tap the **Membership** bottom-nav tab → opens the **Membership Content** screen.

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

- Location: `app/src/main/assets/mock/membership_content.json`
- Contract: `specs/002-membership-content/contracts/membership-content-json.md`

If the JSON file is missing or malformed, the Membership screen should show an error state with a retry action.

