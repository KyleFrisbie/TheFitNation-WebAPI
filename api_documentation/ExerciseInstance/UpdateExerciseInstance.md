Update ExerciseInstance
---
* Update an existing Exercise Instance
* Add/remove/or update embedded Exercise Instance Sets

**Method and Route**\
PUT /api/exercise-instances


**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer aee6e0b8-fc84-4e73-8357-d6a3073c5536

**Post Body**
```json
{
  "id": 9703,
  "notes": "Text Exercise Instance in Workout Post",
  "workoutInstanceId": 6901,
  "exerciseId": 1401,
  "exerciseName": null,
  "repUnitId": 1201,
  "repUnitName": null,
  "effortUnitId": 1202,
  "effortUnitName": null,
  "exerciseInstanceSets": [
    {
      "orderNumber": 2,
      "reqQuantity": 15,
      "effortQuantity": 40,
      "restTime": 30,
      "notes": "workoutinstance test 2",
      "exerciseInstanceId": 9703
    },
    {
      "id": 9761,
      "orderNumber": 5,
      "reqQuantity": 15,
      "effortQuantity": 10,
      "restTime": 0,
      "notes": "workoutinstance test 3",
      "exerciseInstanceId": 9703
    },
    {
      "id": 9759,
      "orderNumber": 1,
      "reqQuantity": 12,
      "effortQuantity": 45,
      "restTime": 30,
      "notes": "workoutinstance test 1",
      "exerciseInstanceId": 9703
    }
  ]
}
```

**Response Body**
```json
{
  "id": 12505,
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
      "id": 12563,
      "orderNumber": 2,
      "reqQuantity": 15,
      "effortQuantity": 40,
      "restTime": 30,
      "notes": "workoutinstance test 2",
      "exerciseInstanceId": 12505
    },
    {
      "id": 12564,
      "orderNumber": 5,
      "reqQuantity": 15,
      "effortQuantity": 10,
      "restTime": 0,
      "notes": "workoutinstance test 3",
      "exerciseInstanceId": 12505
    },
    {
      "id": 12565,
      "orderNumber": 1,
      "reqQuantity": 12,
      "effortQuantity": 45,
      "restTime": 30,
      "notes": "workoutinstance test 1",
      "exerciseInstanceId": 12505
    }
  ]
}
```

**Special Considerations**
* Like all other PUTS, the ID field for an ExerciseInstance must be included.
* Valid id's are required for the following:
    1. workoutInstanceId
    2. exerciseId
    3. repUnitId
    4. effortUnitId
* New ExerciseInstanceSets can be added by including an ExerciseInstanceSet object without an id.
* ExerciseInstanceSets can be updated by providing the id for an existing ExerciseInstanceSet within the ExerciseInstanceSet object. This child must already be related to the parent you are updating.
* An ExerciseInstanceSet can be removed from the list of Sets simply by removing it from the list.
