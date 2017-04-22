Get all UserWorkoutTemplates
---
* Get all UserWorkoutTemplates without child objects

**Method and Route**\
GET /api/user-workout-templates

**Request Headers**\
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
none
```

**Response Body**
```json
[
  {
    "id": 7601,
    "createdOn": "2017-04-01",
    "lastUpdated": "2017-04-01",
    "notes": "Test user workout template",
    "userDemographicId": 1051,
    "workoutTemplateId": 1151,
    "workoutTemplateName": "Blah"
  },
  {
    "id": 8001,
    "createdOn": "2017-03-30",
    "lastUpdated": "2017-03-30",
    "notes": "Test user workout template 2",
    "userDemographicId": 1051,
    "workoutTemplateId": null,
    "workoutTemplateName": null
  },
  {
    "id": 15101,
    "createdOn": "2017-04-01",
    "lastUpdated": "2017-04-01",
    "notes": "Test user workout template",
    "userDemographicId": 1051,
    "workoutTemplateId": 1151,
    "workoutTemplateName": "Blah"
  }
]
```
