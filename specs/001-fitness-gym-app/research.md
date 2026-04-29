# Research: 001 - Fitness Gym Android App (Landing + Main Nav + Home Mock)

This document resolves planning unknowns from the implementation plan and records technology decisions.

## Decision: Navigation with `androidx.navigation:navigation-compose`

- **Decision**: Use `androidx.navigation:navigation-compose` to implement:
  - `Landing` (entry)
  - `LoginPlaceholder` (coming soon)
  - `Main` (bottom navigation host: Home/Membership/Account)
- **Rationale**: Standard Compose navigation solution; supports nested graphs; widely documented; fits single-activity Compose architecture.
- **Alternatives considered**:
  - Manual state-based navigation (sealed screen state): simpler but grows messy with deeper navigation.
  - Third-party navigation libs: unnecessary for this milestone.

## Decision: Bottom navigation using Material3 `NavigationBar`

- **Decision**: Use `Scaffold` + `NavigationBar` + `NavigationBarItem`.
- **Rationale**: Material3-first; consistent UI; integrates well with `NavController` (or tab state).
- **Alternatives considered**:
  - Material2 `BottomNavigation`: not preferred given Material3 usage.

## Decision: Bundled JSON stored under assets

- **Decision**: Store mock dataset at `app/src/main/assets/mock/home_content.json`.
- **Rationale**:
  - Easy to bundle and organize by feature (`assets/mock/...`).
  - Load by relative path string; good for iteration.
- **Alternatives considered**:
  - `res/raw/`: type-safe resource id and simpler reference, but less flexible folder structure.

## Decision: JSON parsing via Kotlinx Serialization

- **Decision**: Use `kotlinx-serialization-json` with a configured `Json` instance:
  - `ignoreUnknownKeys = true`
  - `isLenient = true` (optional; only if mock content contains leniencies)
- **Rationale**: Constitution requirement; compile-time safety; easy DTO mapping.
- **Alternatives considered**:
  - Moshi/Gson: not aligned with constitution.

## Decision: Clean Architecture boundary for asset access

- **Decision**: Hide Android `Context`/`AssetManager` behind a Data-layer implementation of a small interface (e.g., `AssetReader`).
- **Rationale**: Keeps Domain pure (no Android framework dependencies). Enables unit tests by swapping in a fake `AssetReader`.
- **Alternatives considered**:
  - Reading assets directly in ViewModel/UI: violates layering.

## Decision: Error handling model for missing/malformed JSON

- **Decision**: Treat read/parse errors as a recoverable `Result.failure` with typed exceptions (or a sealed error type).
- **Rationale**: Spec edge case requires a user-friendly error state and retry.
- **Alternatives considered**:
  - Returning nullable content: encourages implicit nulls; not aligned with strict null-safety.

## Decision: Unit test stack: JUnit 5 + MockK

- **Decision**: Use JUnit Jupiter for JVM unit tests and MockK for mocking.
- **Rationale**: Constitution requirement; good Kotlin support.
- **Implementation note**:
  - Configure Gradle to run unit tests on the JUnit Platform: `testOptions.unitTests.all { useJUnitPlatform() }`.
- **Alternatives considered**:
  - JUnit4: present in the template project but conflicts with constitution for business logic.

## Decision: Compose UI test focus

- **Decision**: Add Compose UI tests for:
  - Landing actions → Main
  - bottom navigation switching
  - Home sections visibility
- **Rationale**: Ensures navigation and placeholder stability without relying on manual QA.
- **Alternatives considered**:
  - Only unit tests: insufficient for navigation/UI acceptance scenarios.

