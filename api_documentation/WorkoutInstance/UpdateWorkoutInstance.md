Update Workout Instance
---
* Updates a WorkoutInstance

**Method and Route**\
PUT /api/workout-instances

**Request Headers**\
Content-Type: application/json
Accept: application/json
Authorization: Bearer 3dcbc283-88d2-4fa9-90eb-18d681cf6459

**Post Body**
```javascript
{
  "id": 9901,
  "name": "Remove some sets, update some sets, add some new sets",
  "createdOn": "2017-03-23",
  "lastUpdated": "2017-03-23",
  "restBetweenInstances": 120,
  "orderNumber": 1,
  "notes": "Chest day",
  "workoutTemplateId": 1151,
  "workoutTemplateName": null,
  "exerciseInstances": [
    {
      "id": 9701,
      "notes": "Text Exercise Instance in Workout Post",
      "workoutInstanceId": 9651,
      "exerciseId": 1401,
      "exerciseName": null,
      "repUnitId": 1201,
      "repUnitName": null,
      "effortUnitId": 1202,
      "effortUnitName": null,
      "exerciseInstanceSets": [
        {
          "orderNumber": 1,
          "reqQuantity": 1200,
          "effortQuantity": 45,
          "restTime": 30,
          "notes": "new set, replaced old set 1",
          "exerciseInstanceId": 9701
        },
        {
          "id": 9752,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 500,
          "notes": "updated set 2",
          "exerciseInstanceId": 9701
        },
        {
          "id": 9753,
          "orderNumber": 3,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "unchanged set 3",
          "exerciseInstanceId": 9701
        },
        {
          "orderNumber": 4,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "add set 4",
          "exerciseInstanceId": 9701
        }
      ]
    }
  ]
}
```

**Response Body**
```javascript
{
  "id": 12455,
  "name": "Remove some sets, update some sets, add some new sets",
  "createdOn": "2017-03-23",
  "lastUpdated": "2017-03-23",
  "restBetweenInstances": 120,
  "orderNumber": 1,
  "notes": "Chest day",
  "workoutTemplateId": 1151,
  "workoutTemplateName": "Blah",
  "exerciseInstances": [
    {
      "id": 12507,
      "notes": "Text Exercise Instance in Workout Post",
      "workoutInstanceId": 12455,
      "exerciseId": 1401,
      "exerciseName": null,
      "repUnitId": 1201,
      "repUnitName": null,
      "effortUnitId": 1202,
      "effortUnitName": null,
      "exerciseInstanceSets": [
        {
          "id": 12571,
          "orderNumber": 1,
          "reqQuantity": 1200,
          "effortQuantity": 45,
          "restTime": 30,
          "notes": "new set, replaced old set 1",
          "exerciseInstanceId": 12507
        },
        {
          "id": 12572,
          "orderNumber": 2,
          "reqQuantity": 15,
          "effortQuantity": 40,
          "restTime": 500,
          "notes": "updated set 2",
          "exerciseInstanceId": 12507
        },
        {
          "id": 12573,
          "orderNumber": 3,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "unchanged set 3",
          "exerciseInstanceId": 12507
        },
        {
          "id": 12574,
          "orderNumber": 4,
          "reqQuantity": 15,
          "effortQuantity": 10,
          "restTime": 0,
          "notes": "add set 4",
          "exerciseInstanceId": 12507
        }
      ]
    }
  ]
}
```

**Special Considerations**
* Must have valid id's for:\
..* workoutTemplateId
..* exerciseId
..* repUnitId
..* effortUnitId
* Child objects refer to the list of ExerciseInstances, and ExerciseInstanceSets.
* Child objects can be deleted from the WorkoutTemplate simply by removing them from the list of children.
* Child objects can be updated by providing a valid id for a child that already exists
* New children can be added by adding new objects to the list without an id.
* The foreign key id's required in any new child objects will be automatically generated/added on the server side.