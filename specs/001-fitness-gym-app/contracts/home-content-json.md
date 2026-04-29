# Contract: Home Content JSON (bundled mock dataset)

This contract defines the JSON structure consumed by the app to render the Home screen.

## File location

- **Path (in app module)**: `app/src/main/assets/mock/home_content.json`
- **Encoding**: UTF-8

## Contract goals

- Stable shape for the Home screen sections.
- Forward compatible: the app should ignore unknown fields.

## JSON shape

```json
{
  "hero": {
    "headline": "Train Harder.",
    "subheadline": "Live Better.",
    "body": "Join PeakFit — over 50 premium clubs nationwide designed to push your limits and reward your effort.",
    "primaryCtaLabel": "Find a Club",
    "secondaryCtaLabel": "View Memberships"
  },
  "findAClub": {
    "title": "Find a club",
    "description": "Discover a PeakFit near you.",
    "ctaLabel": "Find a club",
    "clubs": [
      {
        "id": "club_downtown",
        "name": "Downtown",
        "area": "Central",
        "shortDescription": "Cardio, weights, and classes"
      }
    ]
  },
  "membershipTypes": {
    "title": "Membership types",
    "description": "Choose a plan that fits your goals.",
    "plans": [
      {
        "id": "basic",
        "name": "Basic",
        "priceFrom": "$19.99/mo",
        "bullets": ["Gym access", "Locker rooms"]
      }
    ]
  },
  "awards": {
    "title": "Awards",
    "items": [
      {
        "id": "award_1",
        "title": "Best Gym 2025",
        "description": "Recognized for community impact and member results."
      },
      {
        "id": "award_2",
        "title": "Top Trainers",
        "description": "Certified coaches with proven track records."
      },
      {
        "id": "award_3",
        "title": "50+ Clubs",
        "description": "Premium clubs nationwide with modern facilities."
      }
    ]
  }
}
```

## Field rules

- All `id` fields MUST be non-empty strings.
- Titles MUST be non-empty strings.
- `hero.headline`, `hero.subheadline`, and `hero.body` MUST be non-empty strings.
- `description`, `ctaLabel`, `primaryCtaLabel`, `secondaryCtaLabel`, and `priceFrom` MAY be null or omitted (app should handle gracefully).
- Lists MAY be empty.

## Error handling expectations

If the file is missing or cannot be parsed:

- The app MUST show a user-friendly error state (no crash).
- The UI MUST provide a retry action.

