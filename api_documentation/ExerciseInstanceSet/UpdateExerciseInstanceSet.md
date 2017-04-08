Update ExerciseInstanceSet
---

**Method and Route**\
PUT /api/exercise-instance-sets

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "id": 9764,
  "orderNumber": 2,
  "reqQuantity": 15,
  "effortQuantity": 40,
  "restTime": 30,
  "notes": "snack pack",
  "exerciseInstanceId": 6951
}
```

**Response Body**
```json
{
  "id": 12567,
  "orderNumber": 2,
  "reqQuantity": 15,
  "effortQuantity": 40,
  "restTime": 30,
  "notes": "snack pack",
  "exerciseInstanceId": 6951
}
```

**Special Considerations**\
* A valid id must be provided for the id field.
* Don't try and change the ExerciseInstance an ExerciseInstanceSet is associated with by altering the exerciseInstanceId.
