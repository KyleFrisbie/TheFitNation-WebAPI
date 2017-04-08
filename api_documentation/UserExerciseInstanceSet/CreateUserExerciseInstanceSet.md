Create UserExerciseInstance Set
---

**Method and Route**\
POST /api/user-exercise-instance-sets

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "orderNumber": 1,
  "repQuantity": 190,
  "effortQuantity": 190,
  "restTime": 1290,
  "notes": "Mike is cool!",
  "userExerciseInstanceId": 7751,
  "exerciseInstanceSetId": null
}
```

**Response Body**
```json
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
```

**Special Considerations**\
* A valid id must be provided for userExerciseInstanceId, this is the UserExerciseInstance that the newly created UserExerciseInstanceSet will be related to.
