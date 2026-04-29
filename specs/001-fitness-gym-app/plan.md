# Implementation Plan: 001 - Fitness Gym Android App (Home Tab: 4 Sections)

**Branch**: `001-fitness-gym-app` | **Date**: 2026-04-28 | **Spec**: `specs/001-fitness-gym-app/spec.md`
**Input**: Feature specification from `specs/001-fitness-gym-app/spec.md`

## Summary

Implement the Milestone-1 app shell (Landing → Main + bottom navigation) and a **Home tab** whose content matches the provided four design sections and is **vertically scrollable**.

Home tab design references (project root):

- Section 1 (Header/Hero): `android_sdd_home/home_1_header.png`
- Section 2 (Find a club): `android_sdd_home/home_2_find_club.png`
- Section 3 (Membership types): `android_sdd_home/home_3_membersship_type.png`
- Section 4 (Awards): `android_sdd_home/home_4_awards.png`

## Technical Context

**Language/Version**: Kotlin (Android)
**Primary Dependencies**: Jetpack Compose (Material 3), Hilt, Navigation-Compose, Kotlinx Serialization, Coil
**Storage**: N/A (bundled JSON only)
**Testing**: JUnit 5, MockK, Jetpack Compose Testing APIs
**Target Platform**: Android (min SDK 26)
**Project Type**: Android mobile app (single-activity, Compose)
**Performance Goals**: Smooth scroll (avoid heavy recompositions in `LazyColumn`)
**Constraints**:

- Compose-only UI (no XML)
- Clean Architecture + MVVM
- Domain must remain Android-framework-free (asset reading behind an interface)

## Constitution Check (GATE)

Status: **PASS** (no exceptions required).

- [x] UI uses Jetpack Compose only (no XML Views).
- [x] Clean Architecture layers + MVVM for UI state.
- [x] DI via Hilt; Domain stays Android-framework-free.
- [x] Business logic uses unit tests (JUnit 5 + MockK) for Domain / non-trivial Data.
- [x] Compose UI tests cover navigation + Home content states.
- [x] No secrets hardcoded; networking not required for Home milestone.

## Home Tab: Layout & Behavior (aligned to designs)

### Scroll model (must-have)

- The Home tab MUST be implemented as a single **vertically scrollable** surface using `LazyColumn`.
- Each section is rendered as a distinct `LazyColumn` item.
- Sections may embed their own horizontal scrollers (e.g., awards cards via `LazyRow`) without breaking the main vertical scroll.

### Section 1 — Header/Hero (`home_1_header.png`)

- Full-bleed blue gradient background (`Brush.linearGradient`).
- Center-aligned headline + supporting copy.
- Two CTAs:
  - Primary filled button: “Find a Club”
  - Secondary outlined button: “View Memberships”
- CTAs are a `Row` that can wrap/stack on narrow widths.

### Section 2 — Find a club (`home_2_find_club.png`)

- Light/white background section following the hero.
- Contains a section title and a primary action for discovering clubs.
- UI should be implemented as a composable section with:
  - header row (title + optional supporting text)
  - a prominent card/CTA area (e.g., location teaser and a “Find a club” action)
  - optional list/preview items (future enhancement) while still being fully driven by mock JSON.

### Section 3 — Membership types (`home_3_membersship_type.png`)

- Section title and membership plan previews.
- Each plan is a card with name, (optional) price, and bullet benefits.
- Preferred rendering:
  - `LazyRow` for membership plan cards (works well for multiple plans)
  - fallback to a vertical list when there are 1–2 items (implementation can decide based on design fit).
- Provide an interaction to “View memberships” (navigates to Membership tab placeholder for now).

### Section 4 — Awards (`home_4_awards.png`)

- Blue background section (solid/gradient) with a title.
- Display a set of award/highlight cards.
- The design suggests **multiple award cards in a row**; implement as a `LazyRow` of cards.

### Navigation behavior from Home

- Primary CTA “Find a Club” should route to an in-app next step for this milestone (e.g., snackbar / placeholder screen / future route).
- “View Memberships” should switch to the Membership tab placeholder.

## Data source (mocked)

- Home screen content MUST load from a bundled JSON file.
- Planned location: `app/src/main/assets/mock/home_content.json`
- Contract: `specs/001-fitness-gym-app/contracts/home-content-json.md`

## Project Structure

### Documentation (this feature)

```text
specs/001-fitness-gym-app/
├── spec.md
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── home-content-json.md
└── tasks.md
```

### Source Code (repository root)

```text
app/src/main/java/com/android/androidsdd/
├── data/
├── domain/
└── ui/
    ├── navigation/
    └── screens/
        └── home/
            ├── HomeScreen.kt           # hosts LazyColumn
            └── sections/               # Home section composables
                ├── HomeHeroSection.kt
                ├── FindAClubSection.kt
                ├── MembershipTypesSection.kt
                └── AwardsSection.kt
```

**Structure Decision**: Use Clean Architecture packages under `app/src/main/java/com/android/androidsdd/`.

## Testing strategy (Home-specific)

- Compose UI tests should verify:
  - Home screen is vertically scrollable
  - Each section renders (hero, find club, membership types, awards)
  - CTAs trigger the correct placeholder navigation or messages
  - Error state + retry if mock JSON is missing/malformed

## Complexity Tracking

No constitution violations required for this milestone.
