Delete Workout Instances by ID
---

**Method and Route**\
DELETE /api/workout-instances/{id}

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
* All child objects to the WorkoutInstance will be removed from the db.
* Any UserWorkoutInstances that were related to the removed WorkoutInstance will still exist in the db, without a relationship to a parent WorkoutInstance.