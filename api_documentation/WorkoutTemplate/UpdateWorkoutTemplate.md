Update existing WorkoutTemplate
---

**Method and Route**\
PUT /api/workout-templates

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "id": 9602,
  "name": "Silly Goose",
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
  "id": 12606,
  "name": "Silly Goose",
  "createdOn": "2017-03-31",
  "lastUpdated": "2017-03-31",
  "isPrivate": false,
  "notes": "blah",
  "userDemographicId": 1051,
  "skillLevelId": 1001,
  "skillLevelLevel": "Beginner"
}
```

**Special Considerations**
* Valid id's must be included for:\
    * userDemographicId
    * skillLevelId
