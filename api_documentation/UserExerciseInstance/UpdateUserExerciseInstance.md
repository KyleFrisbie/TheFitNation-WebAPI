Update UserExerciseInstance
---
* Update an existing UserExerciseInstance
* Add/remove/or update embedded UserExerciseInstanceSets

**Method and Route**\
PUT /api/user-exercise-instances


**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer aee6e0b8-fc84-4e73-8357-d6a3073c5536

**Post Body**
```json
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

**Response Body**
```json
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
```

**Special Considerations**
* Like all other PUTS, the ID field for an UserExerciseInstance must be included.
* Valid id's are required for the following:\
    1. UserWorkoutInstance
    2. ExerciseInstance
* New UserExerciseInstanceSets can be added by including a UserExerciseInstanceSet object without an id.
* UserExerciseInstanceSets can be updated by providing the id for an existing UserExerciseInstanceSet within the UserExerciseInstanceSet object. This child must already be related to the parent you are updating.
* A UserExerciseInstanceSet can be removed from the list of Sets simply by removing it from the list.
