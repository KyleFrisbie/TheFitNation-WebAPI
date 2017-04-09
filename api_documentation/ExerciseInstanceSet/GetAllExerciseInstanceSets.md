Get All ExerciseInstanceSets
---
* Get all Exercise Instance Sets

**Method and Route**\
GET /api/exercise-instance-sets

**Request Headers**\
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
none
```

**Response Body**
```json
[
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
  },
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
    "id": 11201,
    "orderNumber": 5,
    "reqQuantity": 15,
    "effortQuantity": 10,
    "restTime": 0,
    "notes": "a string containing notes :)",
    "exerciseInstanceId": 6951
  },
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
  },
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
```
