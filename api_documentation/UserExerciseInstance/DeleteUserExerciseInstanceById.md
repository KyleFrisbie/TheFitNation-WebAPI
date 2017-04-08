Delete UserExerciseInstance by ID
---
* Delete a UserExerciseInstance
* This also removes the child UserExerciseInstanceSets
* Disassociates the deleted UserExerciseInstance from its parent UserWorkoutInstance and ExerciseInstance (if it exists).

**Method and Route**\
DELETE /api/user-exercise-instances/{id}


**Request Headers**\
Authorization: Bearer 2191a7f5-313f-471b-9c4f-807ffa630751

**Post Body**
```javascript
none
```

**Response Body**
```javascript
none
```

**Special Considerations**
* All child objects to the UserExerciseInstance will be removed from the db.
* Any parent objects (UserWorkoutInstance, and optionally, an ExerciseInstance) will be updated so they are no longer referencing the removed UserExerciseInstance.
