Create UserExerciseInstance
---
* Create a new UserExerciseInstance with embedded UserExerciseInstanceSets

**Method and Route**\
POST /api/user-exercise-instances


**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 2191a7f5-313f-471b-9c4f-807ffa630751

**Post Body**
```json
{
  "notes": "foo",
  "userWorkoutInstanceId": 7701,
  "exerciseInstanceId": 6701,
  "userExerciseInstanceSets": [
    {
      "orderNumber": 1,
      "repQuantity": 10,
      "effortQuantity": 2,
      "restTime": 1,
      "notes": "Do it!!"
    },
    {
      "orderNumber": 2,
      "repQuantity": 10,
      "effortQuantity": 2,
      "restTime": 1,
      "notes": "Do it, again!!"
    }
  ]
}
```

**Response Body**
```json
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
```

**Special Considerations**
* Like all other posts, the ID field for an UserExerciseInstance must be null (or not present).
* Don't include id's for any of the UserExerciseInstanceSets. This route requires all UserExerciseInstanceSets to be new and existing UserExerciseInstanceSets should not be added to an new UserExerciseInstance.
* Valid id's are required for the following:
    1. userWorkoutInstance
    2. exerciseInstance
