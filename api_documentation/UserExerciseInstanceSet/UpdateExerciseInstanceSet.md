Update UserExerciseInstanceSet
---

**Method and Route**\
PUT /api/user-exercise-instance-sets

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "id": 13002,
  "orderNumber": 1,
  "repQuantity": 19,
  "effortQuantity": 19,
  "restTime": 1290,
  "notes": "Updated stuff",
  "userExerciseInstanceId": 7751,
  "exerciseInstanceSetId": null
}
```

**Response Body**
```json
{
  "id": 13002,
  "orderNumber": 1,
  "repQuantity": 19,
  "effortQuantity": 19,
  "restTime": 1290,
  "notes": "Updated stuff",
  "userExerciseInstanceId": 7751,
  "exerciseInstanceSetId": null
}
```

**Special Considerations**
* A valid id must be provided for the id field.
* If you attempt to update a UserExerciseInstanceSet that does not exist in the DB, a new one will be created, and a new Id will be returned with the created object.
* Don't try and change the UserExerciseInstance or ExerciseInstanceSet that a UserExerciseInstanceSet is associated with by altering the foreign keys for those fields.
