Create Workout Instance
---
* Create a WorkoutInstance with all new child objects.

**Method and Route**\
POST /api/workout-instances

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
{
    "name": "Demo Embedded Workout Post",
    "createdOn": "2017-03-23",
    "lastUpdated": "2017-03-23",
    "restBetweenInstances": 120,
    "orderNumber": 1,
    "notes": "Chest day",
    "workoutTemplateId": 1151,
    "workoutTemplateName": "Anonymous",
    "exerciseInstances": [
      {
        "notes": "Text Exercise Instance in Workout Post",
        "exerciseId": 1401,
        "exerciseName": "Curl",
        "repUnitId": 1201,
        "repUnitName": "Reps",
        "effortUnitId": 1202,
        "effortUnitName": "Pounds",
        "exerciseInstanceSets": [
          {
            "orderNumber": 1,
            "reqQuantity": 12,
            "effortQuantity": 45,
            "restTime": 30,
            "notes": "workoutinstance test 1",
            "exerciseInstanceId": 7401
          },
          {
            "orderNumber": 2,
            "reqQuantity": 15,
            "effortQuantity": 40,
            "restTime": 30,
            "notes": "workoutinstance test 2",
            "exerciseInstanceId": 7401
          },
          {
            "orderNumber": 5,
            "reqQuantity": 15,
            "effortQuantity": 10,
            "restTime": 0,
            "notes": "workoutinstance test 3",
            "exerciseInstanceId": 7401
          }
        ]
      }
    ]
  }
```

**Response Body**
```javascript
{
  "id": 12454,
  "name": "Demo Embedded Workout Post",
  "createdOn": "2017-03-23",
  "lastUpdated": "2017-03-23",
  "restBetweenInstances": 120,
  "orderNumber": 1,
  "notes": "Chest day",
  "workoutTemplateId": 1151,
  "workoutTemplateName": "Blah",
  "exerciseInstances": [
    {
      "id": 12506,
      "notes": "Text Exercise Instance in Workout Post",
      "workoutInstanceId": 12454,
      "exerciseId": 1401,
      "exerciseName": null,
      "repUnitId": 1201,
      "repUnitName": null,
      "effortUnitId": 1202,
      "effortUnitName": null,
      "exerciseInstanceSets": [
        {
          "id": 12568,
          "orderNumber": 1,
          "reqQuantity": 12,
          "effortQuantity": 45,
          "restTime": 30,
          "notes": "workoutinstance test 1",
          "exerciseInstanceId": 12506
        },
        {
          "id": 12569,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 30,
          "notes": "workoutinstance test 2",
          "exerciseInstanceId": 12506
        },
        {
          "id": 12570,
          "orderNumber": 5,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "workoutinstance test 3",
          "exerciseInstanceId": 12506
        }
      ]
    }
  ]
}
```

**Special Considerations**
* Must have valid id's for:\
..* workoutTemplateId
..* exerciseId
..* repUnitId
..* effortUnitId
* The foreign key id's required in the child objects will be automatically generated/added on the server side.