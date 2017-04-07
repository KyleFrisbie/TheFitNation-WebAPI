Create new WorkoutTemplate
---
* Creates a new WorkoutTemplate without child objects

**Method and Route**\
POST /api/workout-templates

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "name": "TestWorkout TEmplate 2",
  "createdOn": "2017-03-31",
  "lastUpdated": "2017-03-31",
  "isPrivate": false,
  "notes": "blah",
  "userDemographicId": 1051,
  "skillLevelId": 1001,
  "skillLevelLevel": "Beginner"
}
```

**Response Body**
```json
{
  "id": 12605,
  "name": "TestWorkout TEmplate 2",
  "createdOn": "2017-03-31",
  "lastUpdated": "2017-03-31",
  "isPrivate": false,
  "notes": "blah",
  "userDemographicId": 1051,
  "skillLevelId": 1001,
  "skillLevelLevel": null
}
```

**Special Considerations**
* Valid id's must be included for:
    1. userDemographicId
    2. skillLevelId
* To create any WorkoutInstances to associate with a WorkoutTemplate, use the WorkoutInstance endpoint.
