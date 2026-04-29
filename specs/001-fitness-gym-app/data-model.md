# Data Model: 001 - Fitness Gym Android App (Mock Home Content)

This data model describes the Domain entities for the Home screen and the DTO/JSON shape used by the bundled mock dataset.

## Domain layer
### HomeContent

Top-level Home screen content.

```kotlin
data class HomeContent(
    val hero: HeroSection,
    val findAClub: FindAClubSection,
    val membershipTypes: MembershipTypesSection,
    val awards: AwardsSection,
)
```

#### HeroSection

```kotlin
data class HeroSection(
    val headline: String,
    val subheadline: String,
    val body: String,
    val primaryCtaLabel: String?,
    val secondaryCtaLabel: String?,
)
```

#### FindAClubSection

```kotlin
data class FindAClubSection(
    val title: String,
    val description: String?,
    val ctaLabel: String?,
    val clubs: List<ClubSummary>,
)

data class ClubSummary(
    val id: String,
    val name: String,
    val area: String,
    val shortDescription: String,
)
```

#### MembershipTypesSection

```kotlin
data class MembershipTypesSection(
    val title: String,
    val description: String?,
    val plans: List<MembershipPlanSummary>,
)

data class MembershipPlanSummary(
    val id: String,
    val name: String,
    val priceFrom: String?,
    val bullets: List<String>,
)
```

#### AwardsSection

```kotlin
data class AwardsSection(
    val title: String,
    val items: List<AwardItem>,
)

data class AwardItem(
    val id: String,
    val title: String,
    val description: String,
)
```

### Validation rules (Domain)

- All IDs are **non-empty**.
- Section titles are **non-empty**.
- Hero `headline`, `subheadline`, and `body` are **non-empty**.
- Lists may be empty, but the UI should show a friendly empty state if a section has no items.
- No nullable Domain objects; optional fields are explicitly nullable (`description`, `ctaLabel`, `primaryCtaLabel`, `secondaryCtaLabel`, `priceFrom`).

## Data layer DTOs (Kotlinx Serialization)

DTOs are designed to be tolerant of extra JSON fields (`ignoreUnknownKeys = true`). Map DTO → Domain in a dedicated mapper.

Recommended DTO layout:

```kotlin
@Serializable
data class HomeContentDto(
    val hero: HeroSectionDto,
    val findAClub: FindAClubSectionDto,
    val membershipTypes: MembershipTypesSectionDto,
    val awards: AwardsSectionDto,
)
```

## State transitions

Not applicable for this milestone (content is static).

## Notes on loading

- The JSON file is bundled with the app (assets) and read by the Data layer.
- Domain is accessed via `HomeContentRepository` + `GetHomeContentUseCase`.


