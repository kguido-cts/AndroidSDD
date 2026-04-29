# Tasks: 002 - Membership Content Screen

**Input**: Design documents from `specs/002-membership-content/`
- Required: `specs/002-membership-content/spec.md`, `specs/002-membership-content/plan.md`
- Optional (used): `specs/002-membership-content/data-model.md`, `specs/002-membership-content/research.md`, `specs/002-membership-content/quickstart.md`, `specs/002-membership-content/contracts/membership-content-json.md`
- Constitution: `.specify/memory/constitution.md`

**Scope reminders** (from plan/spec):
- Replace the Membership tab placeholder with a real Membership Content screen.
- Content is loaded from a bundled JSON asset: `app/src/main/assets/mock/membership_content.json`.
- Clean Architecture + MVVM: Domain (models/use case) → Data (DTO/mapper/repo) → UI (ViewModel/Compose).
- UI is a single vertically scrollable `LazyColumn` with: Header → Section header (Classic) → Classic card → Section header (Black Card) → Black Card card.
- Must provide stable semantics test tags for root/header/section headers/cards.
- Tests required: JVM unit tests for repository/mapping (+ ViewModel where non-trivial) and Compose UI tests for render + scroll + error/retry.

---

## Phase 1: Setup (Assets + Contract)

**Purpose**: Ensure the membership JSON contract is finalized and a sample asset exists for development and tests.

- [ ] T001 Update/confirm JSON contract in `specs/002-membership-content/contracts/membership-content-json.md` (ensure optional/null rules for `header.body`, `plan.tagline`, and `plan.iconKey` are explicit and match DTO defaults). (Verify: contract example JSON parses as valid JSON; rules mention how null/omitted fields behave.)
- [ ] T002 Add mock dataset `app/src/main/assets/mock/membership_content.json` matching `specs/002-membership-content/contracts/membership-content-json.md` (include both Classic and Black Card, with ≥3 benefits each; set `iconKey` to `null` to exercise placeholder icon path). (Verify: app builds; file is present at runtime; JSON is valid.)

---

## Phase 2: Foundational (Membership Test Tags)

**Purpose**: Add stable semantics tags up-front so UI implementation and Compose tests can be written against fixed identifiers.

- [ ] T003 [P] Add Membership semantics tags to `app/src/main/java/com/android/androidsdd/ui/TestTags.kt` (add: `MEMBERSHIP_SCREEN`, `MEMBERSHIP_LOADING`, `MEMBERSHIP_ERROR`, `MEMBERSHIP_RETRY_BTN`, `MEMBERSHIP_HEADER`, `MEMBERSHIP_SECTION_HEADER_CLASSIC`, `MEMBERSHIP_SECTION_HEADER_BLACK_CARD`, `MEMBERSHIP_PLAN_CARD_CLASSIC`, `MEMBERSHIP_PLAN_CARD_BLACK_CARD`). (Verify: project compiles; no duplicate constant names.)

**Checkpoint**: Membership feature prerequisites are ready.

---

## Phase 3: User Story 1 - View Membership Options (Priority: P1) 🎯 MVP

**Goal**: User can open the Membership tab and see a header plus two labeled sections containing the Classic and Black Card membership details, loaded from the bundled JSON asset.

**Independent Test**: Launch app → Continue as guest → tap Membership tab → header + both membership sections render; if asset is missing/malformed, an error UI with Retry is shown (no crash).

### Tests (JVM unit tests — Data/UI)

- [ ] T004 [P] [US1] Add mapper unit tests in `app/src/test/java/com/android/androidsdd/data/mapper/membership/MembershipContentMapperTest.kt` (covers: full mapping; optional/null fields map to null; empty lists map to empty lists). (Verify: `./gradlew test --tests '*MembershipContentMapperTest'` passes.)
- [ ] T005 [P] [US1] Add repository unit tests in `app/src/test/java/com/android/androidsdd/data/repository/AssetMembershipContentRepositoryTest.kt` (covers: success maps domain; missing asset throws `MembershipContentError.MissingData`; malformed JSON throws `MembershipContentError.InvalidData`). (Verify: `./gradlew test --tests '*AssetMembershipContentRepositoryTest'` passes.)
- [ ] T006 [P] [US1] Add ViewModel unit tests in `app/src/test/java/com/android/androidsdd/ui/screens/membership/MembershipViewModelTest.kt` (covers: init loads `Loading→Content`; failure yields `Loading→Error`; retry triggers reload to `Content`). (Verify: `./gradlew test --tests '*MembershipViewModelTest'` passes.)

### Implementation (Domain → Data → UI)

- [ ] T007 [P] [US1] Implement Domain models in `app/src/main/java/com/android/androidsdd/domain/model/membership/MembershipContent.kt` (`MembershipContent`, `MembershipHeader`, `MembershipSection`, `MembershipPlan`, `MembershipBenefit`; follow `specs/002-membership-content/data-model.md`). (Verify: app compiles; models are Android-framework-free.)
- [ ] T008 [P] [US1] Implement typed error model in `app/src/main/java/com/android/androidsdd/domain/model/membership/MembershipContentError.kt` (`MissingData`, `InvalidData(Throwable)`) mirroring the Home pattern. (Verify: error types are throwable and usable in ViewModel catch blocks.)
- [ ] T009 [P] [US1] Add repository contract in `app/src/main/java/com/android/androidsdd/domain/repository/MembershipContentRepository.kt` (`suspend fun getMembershipContent(): MembershipContent`). (Verify: compiles; KDoc notes typed error.)
- [ ] T010 [US1] Implement use case in `app/src/main/java/com/android/androidsdd/domain/usecase/GetMembershipContentUseCase.kt`. (Verify: `./gradlew test` still passes.)

- [ ] T011 [P] [US1] Implement Kotlinx Serialization DTOs in `app/src/main/java/com/android/androidsdd/data/dto/membership/MembershipContentDto.kt` (DTO shape matches contract; use defaults like `emptyList()` for lists; optional fields nullable with defaults). (Verify: `Json { ignoreUnknownKeys = true }` can decode the sample JSON.)
- [ ] T012 [P] [US1] Implement DTO→Domain mapper in `app/src/main/java/com/android/androidsdd/data/mapper/membership/MembershipContentMapper.kt` (driven by T004). (Verify: T004 passes.)
- [ ] T013 [US1] Implement asset-backed repository in `app/src/main/java/com/android/androidsdd/data/repository/AssetMembershipContentRepository.kt` (read `mock/membership_content.json` via `AssetReader`; decode with injected `Json`; map via `MembershipContentMapper`; run on injected IO dispatcher; map errors to `MembershipContentError`). (Verify: T005 passes.)

- [ ] T014 [US1] Wire production DI in `app/src/main/java/com/android/androidsdd/di/AppModule.kt` (bind `AssetMembershipContentRepository` to `MembershipContentRepository`; keep existing `HomeContentRepository` binding). (Verify: app builds; Hilt graph compiles.)

- [ ] T015 [P] [US1] Define UI state in `app/src/main/java/com/android/androidsdd/ui/screens/membership/MembershipUiState.kt` (`Loading`, `Content(MembershipContent)`, `Error(message: String)`). (Verify: compiles.)
- [ ] T016 [US1] Implement `MembershipViewModel` in `app/src/main/java/com/android/androidsdd/ui/screens/membership/MembershipViewModel.kt` (load on init via `GetMembershipContentUseCase`; expose `StateFlow<MembershipUiState>`; implement `retry()`; map `MembershipContentError` to user-facing messages). (Verify: T006 passes.)

- [ ] T017 [P] [US1] Implement Membership screen route in `app/src/main/java/com/android/androidsdd/ui/screens/membership/MembershipScreen.kt` (collect state via `collectAsStateWithLifecycle`; show loading indicator + error UI with retry + content UI; set root semantics tag `TestTags.MEMBERSHIP_SCREEN`). (Verify: manual run shows Loading briefly then Content; toggling repo failure in tests shows Error.)
- [ ] T018 [P] [US1] Implement header section composable in `app/src/main/java/com/android/androidsdd/ui/screens/membership/sections/MembershipHeaderSection.kt` (renders title + optional body; uses `TestTags.MEMBERSHIP_HEADER`; no fixed heights). (Verify: header is visible and body wraps when long.)
- [ ] T019 [P] [US1] Implement section header composable in `app/src/main/java/com/android/androidsdd/ui/screens/membership/sections/MembershipSectionHeader.kt` (title, spacing, optional divider; allow passing a semantics tag so Classic/Black Card headers can be tagged distinctly). (Verify: both headers render with distinct tags.)
- [ ] T020 [P] [US1] Implement membership plan card composable in `app/src/main/java/com/android/androidsdd/ui/screens/membership/sections/MembershipPlanCard.kt` (consistent layout: placeholder plan icon, name, optional tagline, benefits list; benefit rows include a placeholder icon and wrapping text). (Verify: card renders with empty benefits list without crashing.)
- [ ] T021 [US1] Implement `LazyColumn` content layout in `app/src/main/java/com/android/androidsdd/ui/screens/membership/MembershipScreen.kt` as 5 top-level items (Header, Classic header, Classic card, Black Card header, Black Card card) and apply `TestTags` to section headers/cards (`MEMBERSHIP_SECTION_HEADER_CLASSIC`, `MEMBERSHIP_PLAN_CARD_CLASSIC`, `MEMBERSHIP_SECTION_HEADER_BLACK_CARD`, `MEMBERSHIP_PLAN_CARD_BLACK_CARD`). (Verify: `performScrollTo()` can reach the Black Card card.)

- [ ] T022 [US1] Replace Membership tab placeholder wiring in `app/src/main/java/com/android/androidsdd/ui/screens/main/MainScreen.kt` (use `val membershipViewModel: MembershipViewModel = hiltViewModel()`; render `MembershipScreen(viewModel = membershipViewModel, ...)` instead of `MembershipPlaceholderScreen`). (Verify: running app and tapping Membership shows the new content screen.)
- [ ] T023 [US1] Update bottom-nav test in `app/src/androidTest/java/com/android/androidsdd/ui/navigation/MainBottomNavTest.kt` to assert `TestTags.MEMBERSHIP_SCREEN` (instead of `TestTags.MEMBERSHIP_PLACEHOLDER_SCREEN`). (Verify: `./gradlew connectedAndroidTest` passes this test.)

### Tests (Compose UI — instrumented)

- [ ] T024 [P] [US1] Extend the Hilt test module in `app/src/androidTest/java/com/android/androidsdd/di/FakeHomeContentRepositoryModule.kt` to also provide a fake `MembershipContentRepository` and a toggle state object (e.g., `FakeMembershipContentRepositoryState.shouldFail`) to simulate success/error for Membership UI tests. (Verify: all existing androidTests still compile; Hilt injection succeeds.)
- [ ] T025 [P] [US1] Add Membership route UI tests in `app/src/androidTest/java/com/android/androidsdd/ui/screens/membership/MembershipRouteTest.kt`:
  - Success: navigate Landing → Main; tap Membership tab; assert header + both section headers render; scroll to Black Card card.
  - Error/retry: set `FakeMembershipContentRepositoryState.shouldFail = true`; open Membership; assert error + retry; set `shouldFail = false`; tap retry; assert content visible.
  (Verify: `./gradlew connectedAndroidTest` passes.)

---

## Phase 4: User Story 2 - Compare Classic vs Black Card (Priority: P2)

**Goal**: The two membership cards share a consistent, comparable structure so differences are easy to see.

**Independent Test**: On the Membership screen, both cards present the same fields (icon, name, optional tagline, benefits list) in the same order; user can identify ≥3 benefits for each.

- [ ] T026 [US2] Ensure `MembershipScreen` renders plans using the shared `MembershipPlanCard` composable for both Classic and Black Card (no one-off layouts), in `app/src/main/java/com/android/androidsdd/ui/screens/membership/MembershipScreen.kt`. (Verify: code only uses one card composable; UI looks consistent across both plans.)
- [ ] T027 [US2] Harden plan-card rendering for missing optional fields in `app/src/main/java/com/android/androidsdd/ui/screens/membership/sections/MembershipPlanCard.kt` (when `tagline == null`, omit the tagline row without leaving awkward spacing; when `iconKey == null`, show a neutral placeholder icon). (Verify: sample JSON with `tagline` omitted still renders cleanly; no crash.)
- [ ] T028 [US2] Add a Compose UI assertion in `app/src/androidTest/java/com/android/androidsdd/ui/screens/membership/MembershipRouteTest.kt` that verifies at least three benefit texts for Classic and Black Card are displayed (use the fake repository content strings). (Verify: `./gradlew connectedAndroidTest` passes.)

---

## Phase 5: User Story 3 - Readable on Mobile Screens (Priority: P3)

**Goal**: Membership content remains readable and accessible on small screens and with long benefit text.

**Independent Test**: No horizontal scrolling is required; long benefit text wraps; user can still scroll to reach all content.

- [ ] T029 [US3] Update text/layout constraints in `app/src/main/java/com/android/androidsdd/ui/screens/membership/sections/MembershipPlanCard.kt` to favor wrapping readability (avoid `maxLines` truncation for benefit text; use `Modifier.fillMaxWidth()` and sensible padding; ensure benefit row icon does not force text clipping). (Verify: with a very long benefit string in fake data, the full text is still visible across multiple lines.)
- [ ] T030 [US3] Add a Compose UI test case in `app/src/androidTest/java/com/android/androidsdd/ui/screens/membership/MembershipRouteTest.kt` that uses fake content with a very long benefit string and asserts it is displayed (after scroll if needed). (Verify: `./gradlew connectedAndroidTest` passes.)

---

## Phase 6: Polish & Cross-Cutting Concerns

- [ ] T031 [P] Add/verify KDoc for new public types in `app/src/main/java/com/android/androidsdd/**` (at minimum: `MembershipContentError`, `MembershipContentRepository`, `GetMembershipContentUseCase`, `AssetMembershipContentRepository`, `MembershipViewModel`). (Verify: code review for KDoc presence; build passes.)
- [ ] T032 [P] Update developer docs in `specs/002-membership-content/quickstart.md` (or repo `README.md` if that’s the convention) to mention the Membership screen verification flow and how to run the new tests. (Verify: docs include `./gradlew test` and `./gradlew connectedAndroidTest` and the expected navigation path.)

---

## Dependencies & Execution Order

### Phase Dependencies

- Phase 1 (Assets + Contract) → Phase 2 (Test Tags) → US1 → US2 → US3 → Polish

### User Story Dependency Graph

- **US1** is the MVP: required before any comparison/readability refinements are meaningful.
- **US2** depends on US1’s end-to-end screen being functional.
- **US3** depends on US1/US2 UI being present to validate readability and add wrap-focused tests.

Recommended order:

1. Phase 1
2. Phase 2
3. US1
4. US2
5. US3
6. Phase 6

### Parallel Opportunities (examples)

- Phase 1: T001 and T002 can be done in parallel.
- US1 tests: T004–T006 are parallelizable.
- US1 implementation building blocks: T007–T012 are parallelizable before wiring tasks (T013–T016).
- UI components: T018–T020 are parallelizable once `MembershipContent` models exist.

---

## Parallel Example: US1 (Membership options)

- Tests (parallel):
  - Task: "T004 MembershipContentMapperTest" (Data)
  - Task: "T005 AssetMembershipContentRepositoryTest" (Data)
  - Task: "T006 MembershipViewModelTest" (UI)

- Implementation (parallel):
  - Task: "T007–T010 Domain models + error + repo contract + use case"
  - Task: "T011–T012 DTOs + mapper"

---

## Implementation Strategy

### MVP First (US1)

1. Add the asset JSON + confirm the contract (Phase 1).
2. Add Membership test tags (Phase 2).
3. Implement end-to-end load + render + error/retry, and replace the placeholder screen (US1).
4. Validate with `./gradlew test` and `./gradlew connectedAndroidTest`.

### Incremental Delivery

- US1: Membership tab becomes functional (content-driven, scrollable, error-safe).
- US2: Card structure becomes clearly comparable.
- US3: Readability/wrapping and long-text handling are validated by tests.

