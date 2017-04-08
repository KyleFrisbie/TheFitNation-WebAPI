Create ExerciseInstanceSet
---

**Method and Route**\
POST /api/exercise-instance-sets

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```json
{
  "effortQuantity": 10,
  "exerciseInstanceId": 6951,
  "notes": "a string containing notes :)",
  "orderNumber": 5,
  "reqQuantity": 15,
  "restTime": 0
}
```

**Response Body**
```json
{
  "id": 12566,
  "orderNumber": 5,
  "reqQuantity": 15,
  "effortQuantity": 10,
  "restTime": 0,
  "notes": "a string containing notes :)",
  "exerciseInstanceId": 6951
}
```

**Special Considerations**\
* A valid id must be provided for exerciseInstanceId, this is the ExerciseInstance that the newly created ExerciseInstanceSet will be related to.
