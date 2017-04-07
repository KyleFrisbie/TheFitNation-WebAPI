Get ExerciseInstance by ID
---
* Get an ExerciseInstance with embedded ExerciseInstanceSets

**Method and Route**\
GET /api/exercise-instances/{id}


**Request Headers**\
Authorization: Bearer 2191a7f5-313f-471b-9c4f-807ffa630751

**Post Body**
```javascript
none
```

**Response Body**
```json
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
}
```
