# Contract: Membership Content JSON (bundled mock dataset)

This contract defines the JSON structure consumed by the app to render the Membership content screen.

## File location

- **Path (in app module)**: `app/src/main/assets/mock/membership_content.json`
- **Encoding**: UTF-8

## Contract goals

- Stable, content-driven shape for the Membership screen.
- Forward compatible: the app should ignore unknown fields.

## JSON shape

```json
{
  "header": {
    "title": "Membership",
    "body": "Compare Classic and Black Card memberships."
  },
  "sections": [
    {
      "id": "classic_section",
      "title": "Classic",
      "plans": [
        {
          "id": "classic",
          "name": "Classic",
          "tagline": "Best for everyday gym-goers",
          "iconKey": null,
          "benefits": [
            { "id": "classic_b1", "text": "Unlimited gym access" },
            { "id": "classic_b2", "text": "Access to your home club" },
            { "id": "classic_b3", "text": "Free fitness orientation" }
          ]
        }
      ]
    },
    {
      "id": "black_card_section",
      "title": "Black Card",
      "plans": [
        {
          "id": "black_card",
          "name": "Black Card",
          "tagline": "Best for committed athletes",
          "iconKey": null,
          "benefits": [
            { "id": "black_b1", "text": "All-club access" },
            { "id": "black_b2", "text": "Guest privileges" },
            { "id": "black_b3", "text": "Premium perks (placeholder)" }
          ]
        }
      ]
    }
  ]
}
```

## Field rules

- All `id` fields MUST be non-empty strings.
- `header.title` MUST be a non-empty string.
- `header.body` MAY be null or omitted.
- `sections` MUST be present (MAY be empty).
- `section.title` MUST be non-empty.
- `plans` MUST be present (MAY be empty).
- `plan.name` MUST be non-empty.
- `plan.tagline` MAY be null or omitted.
- `plan.iconKey` MAY be null or omitted; when null/omitted the UI MUST show a neutral placeholder icon.
- `benefits` MUST be present (MAY be empty). Benefit text SHOULD be non-empty for display.

## Error handling expectations

If the file is missing or cannot be parsed:

- The app MUST show a user-friendly error state (no crash).
- The UI MUST provide a retry action.

