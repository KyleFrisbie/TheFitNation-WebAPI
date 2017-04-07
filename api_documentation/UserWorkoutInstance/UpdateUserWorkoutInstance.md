Update UserWorkoutInstance
---
* Updates a WorkoutInstance

**Method and Route**\
PUT /api/user-workout-instances

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "id": 14952,
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "wasCompleted": false,
  "notes": "2017-04-01",
  "userWorkoutTemplateId": 7601,
  "workoutInstanceId": null,
  "workoutInstanceName": null,
  "userExerciseInstances": [
    {
      "id": 15002,
      "notes": "blah",
      "userWorkoutInstanceId": 14952,
      "exerciseInstanceId": 6701,
      "userExerciseInstanceSets": [
        {
          "orderNumber": 2,
          "repQuantity": 10,
          "effortQuantity": 10,
          "restTime": 30,
          "notes": "New userExerciseInstanceSet",
          "userExerciseInstanceId": 15002,
          "exerciseInstanceSetId": null
        },
        {
          "id": 15054,
          "orderNumber": 1,
          "repQuantity": 190,
          "effortQuantity": 190,
          "restTime": 300,
          "notes": "UpdatedExerciseInstanceSet",
          "userExerciseInstanceId": 15002,
          "exerciseInstanceSetId": null
        },
        {
          "id": 15055,
          "orderNumber": 3,
          "repQuantity": 19,
          "effortQuantity": 19,
          "restTime": 1290,
          "notes": "Set that stays the same",
          "userExerciseInstanceId": 15002,
          "exerciseInstanceSetId": null
        }
      ]
    }
  ]
}
```

**Response Body**
```javascript
{
  "id": 14952,
  "createdOn": "2017-04-01",
  "lastUpdated": "2017-04-01",
  "wasCompleted": false,
  "notes": "2017-04-01",
  "userWorkoutTemplateId": 7601,
  "workoutInstanceId": null,
  "workoutInstanceName": null,
  "userExerciseInstances": [
    {
      "id": 15002,
      "notes": "blah",
      "userWorkoutInstanceId": 14952,
      "exerciseInstanceId": 6701,
      "userExerciseInstanceSets": [
        {
          "id": 15057,
          "orderNumber": 2,
          "repQuantity": 10,
          "effortQuantity": 10,
          "restTime": 30,
          "notes": "New userExerciseInstanceSet",
          "userExerciseInstanceId": 15002,
          "exerciseInstanceSetId": null
        },
        {
          "id": 15054,
          "orderNumber": 1,
          "repQuantity": 190,
          "effortQuantity": 190,
          "restTime": 300,
          "notes": "UpdatedExerciseInstanceSet",
          "userExerciseInstanceId": 15002,
          "exerciseInstanceSetId": null
        },
        {
          "id": 15055,
          "orderNumber": 3,
          "repQuantity": 19,
          "effortQuantity": 19,
          "restTime": 1290,
          "notes": "Set that stays the same",
          "userExerciseInstanceId": 15002,
          "exerciseInstanceSetId": null
        }
      ]
    }
  ]
}
```

**Special Considerations**
* Must have valid id's for:
    1. userWorkoutTemplateId
    2. exerciseInstanceId (if included)
    3. exerciseInstanceSetId (if included)
* Child objects refer to the list of UserExerciseInstances, and UserExerciseInstanceSets.
    * Child objects can be deleted from the UserWorkoutTemplate simply by removing them from the list of children.
    * Child objects can be updated by providing a valid id for a child that already exists
    * New children can be added by adding new objects to the list without an id.
    * The foreign key id's required in any new child objects will be automatically generated/added on the server side.
