Get All ExerciseInstances
---
* Get all ExerciseInstance with embedded ExerciseInstanceSets

**Method and Route**\
GET /api/exercise-instances


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
    "id": 6701,
    "notes": "Text Exercise Instance in Workout Post",
    "workoutInstanceId": 6651,
    "exerciseId": 1401,
    "exerciseName": "Curl",
    "repUnitId": 1201,
    "repUnitName": "Reps",
    "effortUnitId": 1202,
    "effortUnitName": "Weight",
    "exerciseInstanceSets": []
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
  },
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
    "id": 10854,
    "notes": "Text Exercise Instance in Workout Post number 2",
    "workoutInstanceId": 10701,
    "exerciseId": 1401,
    "exerciseName": "Curl",
    "repUnitId": 1201,
    "repUnitName": "Reps",
    "effortUnitId": 1202,
    "effortUnitName": "Weight",
    "exerciseInstanceSets": [
      {
        "id": 10912,
        "orderNumber": 5,
        "reqQuantity": 15,
        "effortQuantity": 10,
        "restTime": 0,
        "notes": "workoutinstance test 3",
        "exerciseInstanceId": 10854
      },
      {
        "id": 10910,
        "orderNumber": 1,
        "reqQuantity": 12,
        "effortQuantity": 45,
        "restTime": 30,
        "notes": "workoutinstance test 1 updated",
        "exerciseInstanceId": 10854
      },
      {
        "id": 10911,
        "orderNumber": 2,
        "reqQuantity": 15,
        "effortQuantity": 40,
        "restTime": 30,
        "notes": "workoutinstance test 2",
        "exerciseInstanceId": 10854
      }
    ]
  },
  {
    "id": 10853,
    "notes": "Text 2",
    "workoutInstanceId": 10701,
    "exerciseId": 1401,
    "exerciseName": "Curl",
    "repUnitId": 1201,
    "repUnitName": "Reps",
    "effortUnitId": 1202,
    "effortUnitName": "Weight",
    "exerciseInstanceSets": []
  },
  {
    "id": 11351,
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
        "id": 11401,
        "orderNumber": 1,
        "reqQuantity": 12,
        "effortQuantity": 45,
        "restTime": 30,
        "notes": "workoutinstance test 1",
        "exerciseInstanceId": 11351
      },
      {
        "id": 11402,
        "orderNumber": 2,
        "reqQuantity": 15,
        "effortQuantity": 40,
        "restTime": 30,
        "notes": "workoutinstance test 2",
        "exerciseInstanceId": 11351
      },
      {
        "id": 11403,
        "orderNumber": 5,
        "reqQuantity": 15,
        "effortQuantity": 10,
        "restTime": 0,
        "notes": "workoutinstance test 3",
        "exerciseInstanceId": 11351
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
  }
]
```
