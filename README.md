# AndroidSDD — PeakFit Fitness Gym App

Android app built with Jetpack Compose, Clean Architecture, and Hilt.

## Milestone 1 — Landing + Main Nav + Home (Mock)

### Features

- **Landing screen**: Two CTAs — Continue as Guest (→ Main) and Log In (→ placeholder).
- **Main screen**: Material3 bottom navigation with Home, Membership (placeholder), and Account (placeholder) tabs.
- **Home screen**: Vertically scrollable `LazyColumn` with four sections driven by a bundled JSON asset:
  1. **Hero** — full-bleed gradient, headline, and CTAs.
  2. **Find a Club** — section title, description, and a featured club card.
  3. **Membership Types** — horizontally scrollable plan cards with bullets.
  4. **Awards** — blue-background section with a `LazyRow` of award cards.
- **Error handling**: Missing/malformed JSON shows a user-friendly error state with a Retry button.

### Tech stack

| Layer | Technology |
|-------|-----------|
| UI | Jetpack Compose (Material 3) |
| State | ViewModel + StateFlow |
| DI | Hilt |
| Navigation | Navigation-Compose |
| Serialization | Kotlinx Serialization |
| Unit tests | JUnit 5 Jupiter + MockK |
| UI tests | Jetpack Compose Testing |
| Coverage | Kover (≥ 80% business logic) |

### Running verification

```bash
bash scripts/verify.sh
```

This script runs:
1. JVM unit tests + Kover coverage verification (≥ 80% for Domain + Data).
2. Instrumented/Compose UI tests (`connectedAndroidTest` — requires a connected device or emulator).

### Project structure

```
app/src/main/java/com/android/androidsdd/
├── di/                          # Hilt modules
├── domain/
│   ├── model/home/              # Domain models + HomeContentError
│   ├── repository/              # HomeContentRepository interface
│   └── usecase/                 # GetHomeContentUseCase
├── data/
│   ├── datasource/              # AssetReader interface + AndroidAssetReader
│   ├── dto/home/                # Kotlinx Serialization DTOs
│   ├── mapper/home/             # DTO → Domain mapper
│   └── repository/              # AssetHomeContentRepository
└── ui/
    ├── navigation/              # AppNavGraph, Destinations, MainTabs
    ├── screens/
    │   ├── landing/             # LandingScreen
    │   ├── login/               # LoginPlaceholderScreen
    │   ├── main/                # MainScreen (bottom nav)
    │   ├── home/                # HomeScreen, HomeViewModel, HomeUiState
    │   │   └── sections/        # HomeHeroSection, FindAClubSection, MembershipTypesSection, AwardsSection
    │   ├── membership/          # MembershipPlaceholderScreen
    │   └── account/             # AccountPlaceholderScreen
    └── TestTags.kt              # Stable semantics tags for UI tests
```

### Mock data

Bundled at `app/src/main/assets/mock/home_content.json`.
Contract defined in `specs/001-fitness-gym-app/contracts/home-content-json.md`.


Android sample project using Kotlin + Jetpack Compose.

## Requirements

- Android Studio (latest stable)
- JDK 11

## Run

Open the repository in Android Studio and run the `app` configuration on an emulator/device (min SDK 26).

## Tests

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Current feature plan

- `specs/001-fitness-gym-app/plan.md`

