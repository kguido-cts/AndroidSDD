<!--
Sync Impact Report

- Version change: template -> 1.0.0
- Modified principles:
  - Template placeholder 1 -> I. Kotlin-First & Null-Safe Code
  - Template placeholder 2 -> II. Compose-Only UI (No XML Views)
  - Template placeholder 3 -> III. Clean Architecture + MVVM Layering
  - Template placeholder 4 -> IV. Dependency Inversion via Hilt
  - Template placeholder 5 -> V. Quality, Security & Documentation Gates
- Added sections:
  - Technology & Platform Requirements
  - Development Workflow & Quality Gates
- Removed sections: None
- Templates requiring updates:
  - .specify/templates/plan-template.md (updated)
  - .specify/templates/spec-template.md (no changes needed)
  - .specify/templates/tasks-template.md (updated)
  - .specify/templates/checklist-template.md (no changes needed)
  - .specify/templates/commands/*.md (not present in repo)
- Follow-up TODOs:
  - Create README.md with setup + run instructions (constitution requires it).
-->

# AndroidSDD Constitution

## Core Principles

### I. Kotlin-First & Null-Safe Code

- Production code MUST be written in Kotlin.
- Kotlin null-safety MUST be used intentionally:
  - Avoid the `!!` operator.
  - Prefer non-nullable types and explicit modeling (`sealed class`, `Result`,
	etc.) over implicit nulls.
  - When a non-null contract is required at a boundary, prefer `requireNotNull()`
	/ `checkNotNull()` with a clear message.
- Naming MUST follow standard Kotlin/Android conventions.
- Formatting MUST follow the official Kotlin Style Guide (use IDE formatting).

Rationale: idiomatic Kotlin and strict null modeling prevent runtime crashes and
keep code consistent across the project.

### II. Compose-Only UI (No XML Views)

- UI MUST be built with Jetpack Compose.
- XML-based Views, ViewBinding, DataBinding, and legacy view inflation MUST NOT
  be introduced.
- Interop (`AndroidView`) MUST be the exception and requires justification in
  the PR description.

Rationale: one UI paradigm reduces complexity and avoids split patterns.

### III. Clean Architecture + MVVM Layering

- The codebase MUST follow Clean Architecture with clear layers:
  - **Domain**: business rules (use cases), pure Kotlin, no Android framework
	dependencies.
  - **Data**: repository implementations, remote/local data sources, DTOs, and
	mappers.
  - **UI**: Compose screens, ViewModels, UI state/events.
- Dependency direction MUST be inward:
  - UI depends on Domain.
  - Data depends on Domain via interfaces (Domain defines repository contracts).
  - Domain MUST NOT depend on UI or Data.
- UI layer MUST use MVVM:
  - ViewModels expose immutable UI state and one-off events.
  - Compose UI remains declarative (no business logic in composables).

Rationale: clear boundaries keep business rules testable and changes localized.

### IV. Dependency Inversion via Hilt

- Dependency Inversion is mandatory; depend on abstractions, not concretions.
- Hilt MUST be used for dependency injection (no service locators).
- Android framework types (`Context`, `Resources`, `Activity`) MUST NOT leak into
  Domain. If needed, wrap them behind interfaces provided by outer layers.

Rationale: DI + abstractions enables unit testing, swapping implementations, and
keeping Domain pure.

### V. Quality, Security & Documentation Gates

- **TDD (business logic)**: Domain/business logic MUST be developed test-first
  (red -> green -> refactor). UI wiring may be implemented after business logic,
  but MUST remain thin.
- **Testing standards**:
  - Unit tests: JUnit 5 + MockK.
  - UI tests: Jetpack Compose Testing APIs.
  - Business logic test coverage MUST be >= 80% (Domain layer and any
	non-trivial logic in Data).
- **Security**:
  - Secrets (API keys, tokens) MUST NOT be hardcoded or committed.
  - Secrets MUST be sourced from `local.properties` and managed via a secrets
	Gradle plugin (or equivalent build-time injection).
  - Networking MUST enforce HTTPS for all connections.
- **Documentation**:
  - Public classes and functions MUST have KDoc.
  - The repository MUST maintain a `README.md` with setup and run instructions.

Rationale: these gates prevent regressions, leaks, and undocumented behavior.

## Technology & Platform Requirements

- **Min SDK**: Android API 26.
- **Networking**: Retrofit + OkHttp.
- **JSON parsing**: Kotlinx Serialization.
- **Image loading**: Coil.
- **Storage**: Room database MAY be used when persistence is required; do not
  introduce it preemptively.

Notes:
- Prefer central dependency management (e.g., version catalog) and avoid
  duplicating versions across modules.

## Development Workflow & Quality Gates

- Every change MUST be made via a PR.
- PRs MUST include:
  - a clear architectural placement (Domain/Data/UI) and justification,
  - tests for new or changed business logic,
  - a note if any exception to principles is required.
- Before merging, contributors MUST:
  - run unit tests,
  - run relevant Compose UI tests for affected screens,
  - ensure formatting matches the Kotlin Style Guide.

If a principle cannot be followed, the PR MUST include a written exception with
trade-offs and an issue to remove the exception later.

## Governance

- This Constitution is the highest-level engineering policy for AndroidSDD. If
  another document conflicts with it, the Constitution wins.
- Amendments MUST be made via PR and MUST include:
  1. the proposed text change,
  2. impact analysis (what breaks, what must migrate),
  3. updates to dependent templates/docs as needed.
- Versioning MUST follow semantic versioning:
  - MAJOR: breaking governance changes or principle removal/redefinition.
  - MINOR: new principle/section or materially expanded guidance.
  - PATCH: clarifications, wording, typo fixes.
- Review expectations:
  - Every PR reviewer MUST explicitly consider compliance with the Core
	Principles.
  - Repeated exceptions require a constitution amendment or a refactor plan.

**Version**: 1.0.0 | **Ratified**: 2026-04-28 | **Last Amended**: 2026-04-28
