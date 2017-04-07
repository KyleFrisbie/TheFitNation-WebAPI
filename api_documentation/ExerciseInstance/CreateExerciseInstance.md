Create ExerciseInstance
---
* Create a new Exercise Instance with embedded ExerciseInstanceSets

**Method and Route**\
POST /api/exercise-instances


**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 2191a7f5-313f-471b-9c4f-807ffa630751

**Post Body**
```json
{
  "notes": "a suprise new workout!",
  "workoutInstanceId": 6901,
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
```

**Response Body**
```json
{
  "id": 12504,
  "notes": "a suprise new workout!",
  "workoutInstanceId": 6901,
  "exerciseId": 1401,
  "exerciseName": null,
  "repUnitId": 1201,
  "repUnitName": null,
  "effortUnitId": 1202,
  "effortUnitName": null,
  "exerciseInstanceSets": [
    {
      "id": 12560,
      "orderNumber": 1,
      "reqQuantity": 12,
      "effortQuantity": 45,
      "restTime": 30,
      "notes": "workoutinstance test 1",
      "exerciseInstanceId": 12504
    },
    {
      "id": 12561,
      "orderNumber": 2,
      "reqQuantity": 15,
      "effortQuantity": 40,
      "restTime": 30,
      "notes": "workoutinstance test 2",
      "exerciseInstanceId": 12504
    },
    {
      "id": 12562,
      "orderNumber": 5,
      "reqQuantity": 15,
      "effortQuantity": 10,
      "restTime": 0,
      "notes": "workoutinstance test 3",
      "exerciseInstanceId": 12504
    }
  ]
}
```

**Special Considerations**
* Like all other posts, the ID field for an ExerciseInstance must be null (or not present).
* Don't include id's for any of the ExerciseInstanceSets. This route requires all ExerciseInstanceSets to be new and existing ExerciseInstanceSets should not be added to an new ExerciseInstance.
* Valid id's are required for the following:
    1. workoutInstanceId
    2. exerciseId
    3. repUnitId
    4. effortUnitId
