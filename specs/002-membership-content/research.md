# Research: 002 - Membership Content Screen

This document resolves planning unknowns from `specs/002-membership-content/plan.md` and records the key design/technology decisions.

## Decision: Host the screen in the existing Membership bottom-nav tab

- **Decision**: Replace `MembershipPlaceholderScreen` with a real `MembershipScreen` (tab content) inside `MainScreen`.
- **Rationale**: Matches the existing app’s navigation model (Home/Membership/Account tabs) without introducing a new app-level route.
- **Alternatives considered**:
  - Adding a new Navigation-Compose destination: unnecessary for a tab-hosted screen in this milestone.

## Decision: Use a bundled JSON asset for membership copy

- **Decision**: Store membership content at `app/src/main/assets/mock/membership_content.json`.
- **Rationale**:
  - Keeps UI declarative and content-driven (same approach as Home).
  - Allows copy/design iteration without recompiling Kotlin constants.
  - Enables deterministic unit tests for parsing/mapping.
- **Alternatives considered**:
  - Hardcoding strings in the UI: fastest, but makes iteration harder and pushes “content” concerns into UI code.
  - `res/raw/`: type-safe resource id, but less flexible folder layout than `assets/mock/`.

## Decision: Placeholder icons until final assets are available

- **Decision**: Use Material Icons as neutral placeholders for:
  - membership plan icon (Classic/Black Card)
  - benefit bullet icon
- **Rationale**: Satisfies FR-006 (no broken layout without final assets).
- **Alternatives considered**:
  - Bundling temporary PNG assets: possible, but creates churn when final assets arrive.

## Decision: UI structure as a single vertically scrollable `LazyColumn`

- **Decision**: Render header + 2 section headers + 2 cards as `LazyColumn` items.
- **Rationale**: Ensures FR-007 (scroll), supports very small screens and long benefit lists, and avoids nested vertical scrolls.
- **Alternatives considered**:
  - `Column` + `verticalScroll`: simpler, but `LazyColumn` scales better if benefit lists grow.

## Decision: Minimal MVVM state model despite local content

- **Decision**: Use a ViewModel exposing `MembershipUiState` (`Loading`, `Content`, `Error`) with Retry.
- **Rationale**: Keeps consistency with the Home screen architecture and supports asset missing/malformed scenarios.
- **Alternatives considered**:
  - Direct synchronous load in composable: thinner, but mixes state + data acquisition into UI.

## Decision: Typed error modeling (Domain)

- **Decision**: Model membership load failures as a small sealed error type (e.g., `MembershipContentError` with `MissingData` / `InvalidData(Throwable)`).
- **Rationale**: Avoids implicit nulls and supports deterministic error UI.
- **Alternatives considered**:
  - Nullable returns: encourages implicit null handling and is less explicit.

