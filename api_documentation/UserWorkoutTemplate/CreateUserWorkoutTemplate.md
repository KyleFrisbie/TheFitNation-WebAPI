Create new UserWorkoutTemplate
---
* Creates a new UserWorkoutTemplate without child objects

**Method and Route**\
POST /api/user-workout-templates

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "notes": "New user workout template <3",
  "userDemographicId": 1051,
  "workoutTemplateId": 1151,
  "workoutTemplateName": "Blah"
}
```

**Response Body**
```json
{
  "id": 15102,
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "notes": "New user workout template <3",
  "userDemographicId": 1051,
  "workoutTemplateId": 1151,
  "workoutTemplateName": null
}
```

**Special Considerations**
* If this UserWorkoutTemplate is based on a WorkoutTemplate, a workoutTemplateId must be included.
* Valid id's must be included for:
    1. userDemographicId
    2. workoutTemplateId (if included)
* To create any UserWorkoutInstances to associate with a UserWorkoutTemplate, use the UserWorkoutInstance endpoint.
