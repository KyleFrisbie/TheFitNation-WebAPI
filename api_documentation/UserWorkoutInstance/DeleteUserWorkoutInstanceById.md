Delete UserWorkoutInstances by ID
---

**Method and Route**\
DELETE /api/user-workout-instances/{id}

**Request Headers**\
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
none
```

**Response Body**
```javascript
none
```

**Special Considerations**
* All child objects to the UserWorkoutInstance will be removed from the db.
* Parent objects (WorkoutTemplate, ExerciseInstance) that had a reference to the removed UserWorkoutInstance, will be updated.
