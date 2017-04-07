Delete WorkoutTemplate by id
---

**Method and Route**\
DELETE /api/workout-templates/{id}

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
* Removes all child objects from the db when the WorkoutTemplate is removed.
* Any UserWorkoutTemplates that were related to the removed WorkoutTemplate will still exist, without a reference to a parent WorkoutTemplate.
