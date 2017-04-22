Delete an ExerciseInstanceSet by Id
---

**Method and Route**\
DELETE /api/exercise-instance-sets/{id}


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

**Special Considerations**\
* Deleting an ExerciseInstanceSet will also disassociate it from its parent ExerciseInstance and any UserExerciseInstanceSets that were referencing it.
