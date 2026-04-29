# Data Model: 002 - Membership Content Screen

This data model describes the Domain entities for the Membership content screen and the DTO/JSON shape used by the bundled mock dataset.

## Domain layer

### MembershipContent

Top-level membership screen content.

```kotlin
data class MembershipContent(
    val header: MembershipHeader,
    val sections: List<MembershipSection>,
)
```

#### MembershipHeader

```kotlin
data class MembershipHeader(
    val title: String,
    val body: String?,
)
```

#### MembershipSection

A logical grouping rendered with a section header.

```kotlin
data class MembershipSection(
    val id: String,
    val title: String,
    val plans: List<MembershipPlan>,
)
```

#### MembershipPlan

```kotlin
data class MembershipPlan(
    val id: String,
    val name: String,
    val tagline: String?,
    val benefits: List<MembershipBenefit>,
    val iconKey: String?, // null => show neutral placeholder
)
```

#### MembershipBenefit

```kotlin
data class MembershipBenefit(
    val id: String,
    val text: String,
)
```

### Validation rules (Domain)

- IDs are **non-empty**.
- Section titles and plan names are **non-empty**.
- Benefits list MAY be empty, but the UI should remain stable (still render the card).
- No nullable Domain objects; optional fields are explicitly nullable (`body`, `tagline`, `iconKey`).

## Data layer DTOs (Kotlinx Serialization)

DTOs are designed to tolerate extra JSON fields (`ignoreUnknownKeys = true`). Map DTO → Domain in a dedicated mapper.

Recommended DTO layout:

```kotlin
@Serializable
data class MembershipContentDto(
    val header: MembershipHeaderDto,
    val sections: List<MembershipSectionDto>,
)
```

## State transitions

Not applicable (static content). UI state transitions are described in the implementation plan (`Loading → Content` or `Loading → Error`).

## Notes on loading

- The JSON file is bundled with the app (assets) and read by the Data layer.
- Domain is accessed via `MembershipContentRepository` + `GetMembershipContentUseCase`.

