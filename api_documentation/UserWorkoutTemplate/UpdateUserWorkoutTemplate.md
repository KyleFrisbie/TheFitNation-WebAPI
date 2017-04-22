Update existing UserWorkoutTemplate
---

**Method and Route**\
PUT /api/user-workout-templates

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "id": 15102,
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "notes": "updated user workout template <3",
  "userDemographicId": 1051,
  "workoutTemplateId": 1151,
  "workoutTemplateName": null
}
```

**Response Body**
```json
{
  "id": 15102,
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "notes": "updated user workout template <3",
  "userDemographicId": 1051,
  "workoutTemplateId": 1151,
  "workoutTemplateName": "Blah"
}
```

**Special Considerations**
* Valid id's must be included for:
    * userDemographicId
    * workoutTemplateId (if included)
