# Tasks: 001 - Fitness Gym Android App (Landing + Main Nav + Home Mock)

**Input**: Design documents from `/specs/001-fitness-gym-app/`
- Required: `specs/001-fitness-gym-app/spec.md`, `specs/001-fitness-gym-app/plan.md`
- Optional (used): `specs/001-fitness-gym-app/research.md`, `specs/001-fitness-gym-app/data-model.md`, `specs/001-fitness-gym-app/quickstart.md`, `specs/001-fitness-gym-app/contracts/home-content-json.md`
- Constitution: `.specify/memory/constitution.md`

**Scope reminders** (from plan/spec):
- Add required deps: Hilt, `navigation-compose`, Kotlinx Serialization, JUnit 5 + MockK
- Navigation: Landing → Main
- Main: bottom nav with placeholders (Home/Membership/Account)
- Home: load bundled JSON (`app/src/main/assets/mock/home_content.json`) through Clean Architecture layers (Domain/Data/UI)
- Home UI: vertically scrollable `LazyColumn` with 4 sections aligned to `android_sdd_home/*` design refs (Hero, Find a club, Membership types, Awards)
- Tests: TDD-first for business logic (Domain + non-trivial Data); Compose UI tests for navigation and Home rendering + error/retry

**Phase mapping note (plan.md ↔ tasks.md)**

- `plan.md` Phase 0/1 artifacts (Research/Design) are already produced under `specs/001-fitness-gym-app/`.
- `tasks.md` **Phase 1 (Setup)** maps to `plan.md` step **1** (deps + test infra + coverage gate).
- `tasks.md` **Phase 2 (Foundational)** maps to `plan.md` step **2–3** prerequisites (routes/tabs/test tags) needed before full UI work.
- `tasks.md` **Phases 3–5 (US1–US3)** map to `plan.md` steps **2–6** (Landing → bottom-nav → Home from JSON).
- `tasks.md` **Phase 6 (Polish)** maps to `plan.md` cross-cutting gates (KDoc, verification scripts, docs).

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Add required dependencies, configure tests, and enable Hilt application setup.

- [X] T001 Update dependency catalog in `gradle/libs.versions.toml` (add Hilt, navigation-compose, hilt-navigation-compose, kotlinx-serialization-json, JUnit5 Jupiter, MockK, hilt-android-testing, and a coverage tool such as Kover)
- [X] T002 Update plugin catalog usage in `build.gradle.kts` (add `com.google.dagger.hilt.android` + Kotlin serialization + coverage (Kover) plugin aliases as `apply false`)
- [X] T003 Configure module build in `app/build.gradle.kts` (apply Hilt + kapt + Kotlin serialization + coverage plugin; add required dependencies; enable `testOptions.unitTests.all { useJUnitPlatform() }`; configure coverage verification >= 80% for business logic packages (Domain + non-trivial Data) per constitution; add `androidTestImplementation` + `kaptAndroidTest` for Hilt testing; set `testInstrumentationRunner` to `com.android.androidsdd.HiltTestRunner`)
- [X] T004 Create Hilt instrumentation runner in `app/src/androidTest/java/com/android/androidsdd/HiltTestRunner.kt`
- [X] T005 Create Hilt application class in `app/src/main/java/com/android/androidsdd/AndroidSDDApp.kt`
- [X] T006 Register Hilt application in `app/src/main/AndroidManifest.xml` (set `<application android:name=".AndroidSDDApp">`)
- [X] T007 [P] Add JUnit5 smoke test in `app/src/test/java/com/android/androidsdd/JUnit5SmokeTest.kt`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Shared scaffolding needed by *all* user stories (DI primitives + navigation constants + stable UI test tags).

**⚠️ CRITICAL**: No user story work should start until this phase is complete.

- [X] T008 [P] Add coroutine dispatcher qualifiers in `app/src/main/java/com/android/androidsdd/di/Qualifiers.kt` (e.g., `@IoDispatcher`)
- [X] T009 Add DI module skeleton in `app/src/main/java/com/android/androidsdd/di/AppModule.kt` (provide `Json { ignoreUnknownKeys = true }` and the IO dispatcher)
- [X] T010 [P] Define stable semantics tags in `app/src/main/java/com/android/androidsdd/ui/TestTags.kt` (used by all Compose UI tests)
- [X] T011 [P] Define app route constants in `app/src/main/java/com/android/androidsdd/ui/navigation/Destinations.kt` (Landing/Login/Main + tab routes)
- [X] T012 [P] Define bottom-tab metadata in `app/src/main/java/com/android/androidsdd/ui/navigation/MainTabs.kt` (Home/Membership/Account)

**Checkpoint**: Foundation ready — user story work can proceed.

---

## Phase 3: User Story 1 - Enter the app from a landing screen (Priority: P1) 🎯 MVP

**Goal**: App launches to Landing with two actions; Guest goes to Main (Home selected); Login shows a clearly-labeled placeholder.

**Independent Test**: Launch app, verify Landing buttons; tap Guest → Main shown; tap Login → Login placeholder shown; no duplicate navigation/crash on rapid taps.

### Tests (Compose UI)

- [X] T013 [P] [US1] Add navigation UI test in `app/src/androidTest/java/com/android/androidsdd/ui/navigation/LandingNavigationTest.kt` (Landing buttons visible; Guest → Main; Login → LoginPlaceholder; LoginPlaceholder includes action to return and/or continue as guest)

### Implementation

- [X] T014 [P] [US1] Implement `LandingScreen` in `app/src/main/java/com/android/androidsdd/ui/screens/landing/LandingScreen.kt` (uses `TestTags`, prevents rapid multi-tap navigation)
- [X] T015 [P] [US1] Implement `LoginPlaceholderScreen` in `app/src/main/java/com/android/androidsdd/ui/screens/login/LoginPlaceholderScreen.kt` (clearly labeled "coming soon"; provides explicit actions to go back and/or continue as guest)
- [X] T016 [P] [US1] Implement app nav graph in `app/src/main/java/com/android/androidsdd/ui/navigation/AppNavGraph.kt` (Landing/Login/Main destinations)
- [X] T017 [P] [US1] Implement minimal Main placeholder in `app/src/main/java/com/android/androidsdd/ui/screens/main/MainScreen.kt` (temporary content until US2)
- [X] T018 [US1] Update activity root in `app/src/main/java/com/android/androidsdd/MainActivity.kt` (annotate `@AndroidEntryPoint`; replace `Greeting` with `rememberNavController()` + `AppNavGraph`)

---

## Phase 4: User Story 2 - Navigate the main app using bottom navigation (Priority: P1)

**Goal**: Main screen uses bottom navigation with Home/Membership/Account; Membership and Account show placeholders.

**Independent Test**: From Main screen, bottom bar shows 3 tabs; tapping each shows correct placeholder/content; returning to Home works; selection remains stable.

### Tests (Compose UI)

- [X] T019 [P] [US2] Add bottom-nav UI test in `app/src/androidTest/java/com/android/androidsdd/ui/navigation/MainBottomNavTest.kt` (switching tabs works; selected tab persists after activity recreate/rotation)

### Implementation

- [X] T020 [P] [US2] Implement placeholder screen in `app/src/main/java/com/android/androidsdd/ui/screens/membership/MembershipPlaceholderScreen.kt`
- [X] T021 [P] [US2] Implement placeholder screen in `app/src/main/java/com/android/androidsdd/ui/screens/account/AccountPlaceholderScreen.kt`
- [X] T022 [P] [US2] Create initial Home screen stub in `app/src/main/java/com/android/androidsdd/ui/screens/home/HomeScreen.kt` (will be expanded in US3)
- [X] T023 [US2] Upgrade `MainScreen` to bottom-nav scaffold in `app/src/main/java/com/android/androidsdd/ui/screens/main/MainScreen.kt` (Material3 `NavigationBar`, 3 tabs, Home default; selected tab state saved/restored across rotation)
- [X] T024 [US2] Update navigation wiring in `app/src/main/java/com/android/androidsdd/ui/navigation/AppNavGraph.kt` to route `Destinations.Main` to the bottom-nav `MainScreen`

---

## Phase 5: User Story 3 - View the Home screen content (mocked) (Priority: P1)

**Goal**: Home loads bundled JSON offline via Clean Architecture (Domain/Data/UI) and renders a vertically scrollable Home screen with 4 sections (Hero, Find a club, Membership types, Awards). Missing/malformed JSON shows an error state with retry.

**Independent Test**: Launch app offline → Home renders content from assets; simulate data failure → error UI shows and retry is available.

### Tests for User Story 3 (TDD-first for business logic) ⚠️

> Write these tests FIRST, ensure they FAIL before implementation.

- [X] T025 [P] [US3] Add use case unit tests in `app/src/test/java/com/android/androidsdd/domain/usecase/GetHomeContentUseCaseTest.kt`
- [X] T026 [P] [US3] Add mapper unit tests in `app/src/test/java/com/android/androidsdd/data/mapper/home/HomeContentMapperTest.kt` (includes: optional fields omitted/null; lists empty but valid)
- [X] T027 [P] [US3] Add repository unit tests in `app/src/test/java/com/android/androidsdd/data/repository/AssetHomeContentRepositoryTest.kt` (success + success with empty lists + missing + parse error)
- [X] T028 [P] [US3] Add Hilt test binding module in `app/src/androidTest/java/com/android/androidsdd/di/FakeHomeContentRepositoryModule.kt` (bind fake success/error repos for UI tests)
- [X] T029 [P] [US3] Add Home route Compose UI tests in `app/src/androidTest/java/com/android/androidsdd/ui/screens/home/HomeRouteTest.kt` (success render of 4 sections + empty-state render + error state + retry; verify vertical scroll reaches lower sections)

### Implementation (Domain → Data → UI)

- [X] T030 [P] [US3] Implement Domain models in `app/src/main/java/com/android/androidsdd/domain/model/home/HomeContent.kt` (per `data-model.md`)
- [X] T031 [P] [US3] Add repository contract in `app/src/main/java/com/android/androidsdd/domain/repository/HomeContentRepository.kt`
- [X] T032 [P] [US3] Add typed error model in `app/src/main/java/com/android/androidsdd/domain/model/home/HomeContentError.kt` (missing/invalid data)
- [X] T033 [US3] Implement use case in `app/src/main/java/com/android/androidsdd/domain/usecase/GetHomeContentUseCase.kt` (driven by T025)
- [X] T034 [P] [US3] Add bundled dataset in `app/src/main/assets/mock/home_content.json` (match `contracts/home-content-json.md`)
- [X] T035 [P] [US3] Implement Kotlinx DTOs in `app/src/main/java/com/android/androidsdd/data/dto/home/HomeContentDto.kt`
- [X] T036 [P] [US3] Implement DTO→Domain mapper in `app/src/main/java/com/android/androidsdd/data/mapper/home/HomeContentMapper.kt` (driven by T026)
- [X] T037 [P] [US3] Add asset reading boundary in `app/src/main/java/com/android/androidsdd/data/datasource/AssetReader.kt` (interface)
- [X] T038 [P] [US3] Implement Android asset reader in `app/src/main/java/com/android/androidsdd/data/datasource/AndroidAssetReader.kt` (Context/AssetManager)
- [X] T039 [US3] Implement repository in `app/src/main/java/com/android/androidsdd/data/repository/AssetHomeContentRepository.kt` (read + parse off-main using injected IO dispatcher; map errors to typed failures)
- [X] T040 [US3] Wire production bindings in `app/src/main/java/com/android/androidsdd/di/AppModule.kt` (bind `AssetReader`, `HomeContentRepository`, and provide configured `Json`)
- [X] T041 [P] [US3] Define UI state model in `app/src/main/java/com/android/androidsdd/ui/screens/home/HomeUiState.kt` (Loading/Content/Error)
- [X] T042 [P] [US3] Implement `HomeViewModel` in `app/src/main/java/com/android/androidsdd/ui/screens/home/HomeViewModel.kt` (load on init + retry)
- [X] T043 [US3] Implement Home UI rendering in `app/src/main/java/com/android/androidsdd/ui/screens/home/HomeScreen.kt` as a `LazyColumn` hosting 4 section composables under `app/src/main/java/com/android/androidsdd/ui/screens/home/sections/` (`HomeHeroSection.kt`, `FindAClubSection.kt`, `MembershipTypesSection.kt`, `AwardsSection.kt`) (testTags for hero/sections; error state + retry; friendly empty-state UI for valid-but-empty lists)
- [X] T044 [US3] Integrate Home into Main in `app/src/main/java/com/android/androidsdd/ui/screens/main/MainScreen.kt` (use `hiltViewModel()`; host snackbar; wire Hero "View Memberships" CTA and Membership Types interactions to switch to Membership tab; wire "Find a club" CTA to an in-app placeholder/snackbar)
- [X] T045 [US3] Update tab switching helpers in `app/src/main/java/com/android/androidsdd/ui/navigation/MainTabs.kt` (support programmatic switch from Home interactions)

---

## Phase 6: Polish & Cross-Cutting Concerns

- [X] T046 [P] Add/verify KDoc for new public types in `app/src/main/java/com/android/androidsdd/**` (at minimum: `AndroidSDDApp.kt`, `AppModule.kt`, `GetHomeContentUseCase.kt`, repository + mapper + ViewModel)
- [X] T047 [P] Add local verification script in `scripts/verify.sh` (run `./gradlew test`, coverage verification (>=80% business logic), and `./gradlew connectedAndroidTest`)
- [X] T048 [P] Create/update `README.md` to document verification (how to run `scripts/verify.sh`) and any new quality gates added in this milestone

---

## Dependencies & Execution Order

### Phase Dependencies

- Phase 1 (Setup) → Phase 2 (Foundational) → User Stories → Phase 6 (Polish)

### User Story Dependency Graph

- **US1** (Landing entry) must land on a Main destination (placeholder is fine) → enables UI flow testing.
- **US2** (Bottom nav) upgrades Main to the real navigation model → required before full Home integration.
- **US3** (Home JSON content) depends on US2’s Main tabs (Home is a tab) and on Phase 2 DI primitives.

Recommended order:

1. Setup (Phase 1)
2. Foundational (Phase 2)
3. US1 → US2 → US3
4. Polish

### Parallel Opportunities (examples)

- Phase 1: T001 and T007 can run in parallel.
- Phase 2: T008–T012 are parallelizable.
- US1: T014–T017 are parallelizable once `TestTags` + `Destinations` exist.
- US3: T025–T029 can be started in parallel (tests), then T030–T038 can be implemented in parallel before wiring tasks (T039–T045).

---

## Parallel Example: US3 (Home content)

- Tests (parallel):
  - Task: "T025 GetHomeContentUseCaseTest" (Domain)
  - Task: "T026 HomeContentMapperTest" (Data)
  - Task: "T027 AssetHomeContentRepositoryTest" (Data)

- Implementation building blocks (parallel):
  - Task: "T030 Domain models" + "T031 repository contract" + "T032 error model"
  - Task: "T035 DTOs" + "T036 mapper" + "T037 AssetReader interface"

---

## Implementation Strategy

### MVP First (US1)

1. Complete Phase 1 + Phase 2.
2. Implement US1 (Landing → Main placeholder) and its Compose UI test.
3. Validate US1 independently.

### Incremental Delivery

- US1: Entry flow works
- US2: Main navigation model works with placeholders
- US3: Home becomes functional offline with error handling + retry
