Get UserWorkoutInstance by ID
---
* Returns a UserWorkoutInstance with all of its child objects.

**Method and Route**\
GET /api/user-workout-instances/{id}

**Request Headers**\
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
none
```

**Response Body**
```json
{
  "id": 7701,
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "wasCompleted": false,
  "notes": "2017-04-01",
  "userWorkoutTemplateId": 7601,
  "workoutInstanceId": null,
  "workoutInstanceName": null,
  "userExerciseInstances": [
    {
      "id": 7751,
      "notes": "blah",
      "userWorkoutInstanceId": 7701,
      "exerciseInstanceId": 6701,
      "userExerciseInstanceSets": [
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
      "id": 13751,
      "notes": "foo updated",
      "userWorkoutInstanceId": 7701,
      "exerciseInstanceId": 6701,
      "userExerciseInstanceSets": [
        {
          "id": 13801,
          "orderNumber": 1,
          "repQuantity": 10,
          "effortQuantity": 2,
          "restTime": 1,
          "notes": "Do it, and update it!!",
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
        },
        {
          "id": 13803,
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
}
```
