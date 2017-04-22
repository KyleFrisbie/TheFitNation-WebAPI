Get All UserExerciseInstances
---
* Get all UserExerciseInstance with embedded UserExerciseInstanceSets

**Method and Route**\
GET /api/user-exercise-instances


**Request Headers**\
Accept: application/json
Authorization: Bearer 2191a7f5-313f-471b-9c4f-807ffa630751

**Post Body**
```javascript
none
```

**Response Body**
```json
[
  {
    "id": 7751,
    "notes": "blah",
    "userWorkoutInstanceId": 7701,
    "exerciseInstanceId": 6701,
    "userExerciseInstanceSets": [
      {
        "id": 8501,
        "orderNumber": 2,
        "repQuantity": 10,
        "effortQuantity": 10,
        "restTime": 30,
        "notes": "Mike bets this fails",
        "userExerciseInstanceId": 7751,
        "exerciseInstanceSetId": null
      },
      {
        "id": 13001,
        "orderNumber": 1,
        "repQuantity": 190,
        "effortQuantity": 190,
        "restTime": 1290,
        "notes": "Mike is cool!",
        "userExerciseInstanceId": 7751,
        "exerciseInstanceSetId": null
      },
      {
        "id": 13002,
        "orderNumber": 1,
        "repQuantity": 19,
        "effortQuantity": 19,
        "restTime": 1290,
        "notes": "Updated stuff",
        "userExerciseInstanceId": 7751,
        "exerciseInstanceSetId": null
      },
      {
        "id": 13101,
        "orderNumber": 1,
        "repQuantity": 190,
        "effortQuantity": 190,
        "restTime": 1290,
        "notes": "Mike is cool!",
        "userExerciseInstanceId": 7751,
        "exerciseInstanceSetId": null
      }
    ]
  },
  {
    "id": 8551,
    "notes": "post new exercise instance",
    "userWorkoutInstanceId": 7701,
    "exerciseInstanceId": 6701,
    "userExerciseInstanceSets": []
  },
  {
    "id": 8951,
    "notes": "post new exercise instance",
    "userWorkoutInstanceId": 7701,
    "exerciseInstanceId": 6701,
    "userExerciseInstanceSets": [
      {
        "id": 9001,
        "orderNumber": 1,
        "repQuantity": 10,
        "effortQuantity": 2,
        "restTime": 1,
        "notes": "Do it!!",
        "userExerciseInstanceId": 8951,
        "exerciseInstanceSetId": null
      },
      {
        "id": 9002,
        "orderNumber": 2,
        "repQuantity": 10,
        "effortQuantity": 2,
        "restTime": 1,
        "notes": "Do it!!",
        "userExerciseInstanceId": 8951,
        "exerciseInstanceSetId": null
      }
    ]
  },
  {
    "id": 10151,
    "notes": "foo",
    "userWorkoutInstanceId": 7701,
    "exerciseInstanceId": 6701,
    "userExerciseInstanceSets": [
      {
        "id": 10201,
        "orderNumber": 1,
        "repQuantity": 10,
        "effortQuantity": 2,
        "restTime": 1,
        "notes": "Do it!!",
        "userExerciseInstanceId": 10151,
        "exerciseInstanceSetId": null
      },
      {
        "id": 10202,
        "orderNumber": 2,
        "repQuantity": 10,
        "effortQuantity": 2,
        "restTime": 1,
        "notes": "Do it!!",
        "userExerciseInstanceId": 10151,
        "exerciseInstanceSetId": null
      }
    ]
  },
  {
    "id": 8101,
    "notes": "Test exercise instance",
    "userWorkoutInstanceId": 8051,
    "exerciseInstanceId": null,
    "userExerciseInstanceSets": [
      {
        "id": 8151,
        "orderNumber": 1,
        "repQuantity": 13,
        "effortQuantity": 13,
        "restTime": 223,
        "notes": "exercise set",
        "userExerciseInstanceId": 8101,
        "exerciseInstanceSetId": null
      },
      {
        "id": 8251,
        "orderNumber": 2,
        "repQuantity": 190,
        "effortQuantity": 190,
        "restTime": 1290,
        "notes": "Mike is cool!",
        "userExerciseInstanceId": 8101,
        "exerciseInstanceSetId": null
      },
      {
        "id": 8351,
        "orderNumber": 3,
        "repQuantity": 1,
        "effortQuantity": 1,
        "restTime": 1,
        "notes": "1",
        "userExerciseInstanceId": 8101,
        "exerciseInstanceSetId": null
      }
    ]
  },
  {
    "id": 13751,
    "notes": "foo",
    "userWorkoutInstanceId": 7701,
    "exerciseInstanceId": 6701,
    "userExerciseInstanceSets": [
      {
        "id": 13801,
        "orderNumber": 1,
        "repQuantity": 10,
        "effortQuantity": 2,
        "restTime": 1,
        "notes": "Do it!!",
        "userExerciseInstanceId": 13751,
        "exerciseInstanceSetId": null
      },
      {
        "id": 13802,
        "orderNumber": 2,
        "repQuantity": 10,
        "effortQuantity": 2,
        "restTime": 1,
        "notes": "Do it, again!!",
        "userExerciseInstanceId": 13751,
        "exerciseInstanceSetId": null
      }
    ]
  }
]
```
