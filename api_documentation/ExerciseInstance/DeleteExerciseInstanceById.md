Delete Exercise Instance by ID
---
* Delete an Exercise Inistance
* This also removes the child ExerciseInstanceSets
* Any UserExerciseInstances/UserExerciseInstanceSets that depend on this object continue to exist in the DB but are disassociated with the ExerciseInstanceSet and its children that are being removed.

**Method and Route**\
DELETE /api/exercise-instances/{id}


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
* All child objects to the ExerciseInstance will be removed from the db.
* Any UserExerciseInstances that were related to the removed ExerciseInstance will still exist in the db, without a relationship to a parent ExerciseInstance.