Get UserExerciseInstance by ID
---
* Get a UserExerciseInstance with embedded UserExerciseInstanceSets

**Method and Route**\
GET /api/user-exercise-instances/{id}


**Request Headers**\
Authorization: Bearer 2191a7f5-313f-471b-9c4f-807ffa630751

**Post Body**
```javascript
none
```

**Response Body**
```json
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
}
```
