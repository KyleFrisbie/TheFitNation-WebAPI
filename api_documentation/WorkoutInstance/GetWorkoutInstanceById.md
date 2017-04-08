Get Workout Instance by ID
---
* Returns a WorkoutInstance with all of its child objects.

**Method and Route**\
GET /api/workout-instances/{id}

**Request Headers**\
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
none
```

**Response Body**
```json
{
  "id": 6901,
  "name": "Test Workout Post",
  "createdOn": "2017-03-23",
  "lastUpdated": "2017-03-23",
  "restBetweenInstances": 120,
  "orderNumber": 1,
  "notes": "Chest day",
  "workoutTemplateId": 1151,
  "workoutTemplateName": "Blah",
  "exerciseInstances": [
    {
      "id": 10401,
      "notes": "Text Exercise Instance in Workout Post",
      "workoutInstanceId": 6901,
      "exerciseId": 1401,
      "exerciseName": "Curl",
      "repUnitId": 1201,
      "repUnitName": "Reps",
      "effortUnitId": 1202,
      "effortUnitName": "Weight",
      "exerciseInstanceSets": [
        {
          "id": 10456,
          "orderNumber": 1,
          "reqQuantity": 12,
          "effortQuantity": 45,
          "restTime": 30,
          "notes": "workoutinstance test 1",
          "exerciseInstanceId": 10401
        },
        {
          "id": 10452,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 30,
          "notes": "workoutinstance test 2",
          "exerciseInstanceId": 10401
        },
        {
          "id": 10453,
          "orderNumber": 5,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "workoutinstance test 3",
          "exerciseInstanceId": 10401
        }
      ]
    },
    {
      "id": 11652,
      "notes": "a suprise new workout!",
      "workoutInstanceId": 6901,
      "exerciseId": 1401,
      "exerciseName": "Curl",
      "repUnitId": 1201,
      "repUnitName": "Reps",
      "effortUnitId": 1202,
      "effortUnitName": "Weight",
      "exerciseInstanceSets": [
        {
          "id": 11701,
          "orderNumber": 1,
          "reqQuantity": 12,
          "effortQuantity": 45,
          "restTime": 30,
          "notes": "workoutinstance test 1",
          "exerciseInstanceId": 11652
        },
        {
          "id": 11702,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 30,
          "notes": "workoutinstance test 2",
          "exerciseInstanceId": 11652
        },
        {
          "id": 11703,
          "orderNumber": 5,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "workoutinstance test 3",
          "exerciseInstanceId": 11652
        }
      ]
    },
    {
      "id": 6951,
      "notes": "Mike sees this",
      "workoutInstanceId": 6901,
      "exerciseId": 1401,
      "exerciseName": "Curl",
      "repUnitId": 1201,
      "repUnitName": "Reps",
      "effortUnitId": 1202,
      "effortUnitName": "Weight",
      "exerciseInstanceSets": [
        {
          "id": 11201,
          "orderNumber": 5,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "a string containing notes :)",
          "exerciseInstanceId": 6951
        },
        {
          "id": 12566,
          "orderNumber": 5,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "a string containing notes :)",
          "exerciseInstanceId": 6951
        },
        {
          "id": 12567,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 30,
          "notes": "snack pack",
          "exerciseInstanceId": 6951
        },
        {
          "id": 10652,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 30,
          "notes": "deleted and replaced",
          "exerciseInstanceId": 6951
        },
        {
          "id": 9101,
          "orderNumber": 1,
          "reqQuantity": 12,
          "effortQuantity": 45,
          "restTime": 30,
          "notes": "workoutinstance dfadsf",
          "exerciseInstanceId": 6951
        }
      ]
    },...
  ]
}
```
