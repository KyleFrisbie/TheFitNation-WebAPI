Get All UserWorkoutInstances
---
* Returns all UserWorkoutInstances with all of their child objects.

**Method and Route**\
GET /api/user-workout-instances

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
        "id": 7701,
        "createdOn": "2017-04-01",
        "lastUpdated": "2017-04-01",
        "wasCompleted": false,
        "notes": "2017-04-01",
        "userWorkoutTemplateId": 7601,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": [
            {
                "id": 7751,
                "notes": "blah",
                "userWorkoutInstanceId": 7701,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 8501,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 10,
                        "restTime": 30,
                        "notes": "Mike bets this fails",
                        "userExerciseInstanceId": 7751,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 13001,
                        "orderNumber": 1,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 7751,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 13002,
                        "orderNumber": 1,
                        "repQuantity": 19,
                        "effortQuantity": 19,
                        "restTime": 1290,
                        "notes": "Updated stuff",
                        "userExerciseInstanceId": 7751,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 13101,
                        "orderNumber": 1,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 7751,
                        "exerciseInstanceSetId": null
                    }
                ]
            },
            {
                "id": 8551,
                "notes": "post new exercise instance",
                "userWorkoutInstanceId": 7701,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": []
            },
            {
                "id": 8951,
                "notes": "post new exercise instance",
                "userWorkoutInstanceId": 7701,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 9001,
                        "orderNumber": 1,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it!!",
                        "userExerciseInstanceId": 8951,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 9002,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it!!",
                        "userExerciseInstanceId": 8951,
                        "exerciseInstanceSetId": null
                    }
                ]
            },
            {
                "id": 10151,
                "notes": "foo",
                "userWorkoutInstanceId": 7701,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 10201,
                        "orderNumber": 1,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it!!",
                        "userExerciseInstanceId": 10151,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 10202,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it!!",
                        "userExerciseInstanceId": 10151,
                        "exerciseInstanceSetId": null
                    }
                ]
            },
            {
                "id": 13751,
                "notes": "foo updated",
                "userWorkoutInstanceId": 7701,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 13801,
                        "orderNumber": 1,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it, and update it!!",
                        "userExerciseInstanceId": 13751,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 13802,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it, again!!",
                        "userExerciseInstanceId": 13751,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 13803,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 2,
                        "restTime": 1,
                        "notes": "Do it, again!!",
                        "userExerciseInstanceId": 13751,
                        "exerciseInstanceSetId": null
                    }
                ]
            }
        ]
    },
    {
        "id": 8051,
        "createdOn": "2017-03-30",
        "lastUpdated": "2017-03-30",
        "wasCompleted": false,
        "notes": "Test test stuff",
        "userWorkoutTemplateId": 8001,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": [
            {
                "id": 8101,
                "notes": "Test exercise instance",
                "userWorkoutInstanceId": 8051,
                "exerciseInstanceId": null,
                "userExerciseInstanceSets": [
                    {
                        "id": 8151,
                        "orderNumber": 1,
                        "repQuantity": 13,
                        "effortQuantity": 13,
                        "restTime": 223,
                        "notes": "exercise set",
                        "userExerciseInstanceId": 8101,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 8251,
                        "orderNumber": 2,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 8101,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 8351,
                        "orderNumber": 3,
                        "repQuantity": 1,
                        "effortQuantity": 1,
                        "restTime": 1,
                        "notes": "1",
                        "userExerciseInstanceId": 8101,
                        "exerciseInstanceSetId": null
                    }
                ]
            }
        ]
    },
    {
        "id": 13951,
        "createdOn": "2017-04-01",
        "lastUpdated": "2017-04-01",
        "wasCompleted": false,
        "notes": "2017-04-01",
        "userWorkoutTemplateId": 7601,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": [
            {
                "id": 14001,
                "notes": "blah",
                "userWorkoutInstanceId": 13951,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": []
            }
        ]
    },
    {
        "id": 14101,
        "createdOn": "2017-04-01",
        "lastUpdated": "2017-04-01",
        "wasCompleted": false,
        "notes": "2017-04-01",
        "userWorkoutTemplateId": 7601,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": []
    },
    {
        "id": 14752,
        "createdOn": "2017-04-01",
        "lastUpdated": "2017-04-01",
        "wasCompleted": false,
        "notes": "2017-04-01",
        "userWorkoutTemplateId": 7601,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": [
            {
                "id": 14802,
                "notes": "blah",
                "userWorkoutInstanceId": 14752,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 14854,
                        "orderNumber": 1,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 14802,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 14855,
                        "orderNumber": 3,
                        "repQuantity": 19,
                        "effortQuantity": 19,
                        "restTime": 1290,
                        "notes": "Updated stuff",
                        "userExerciseInstanceId": 14802,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 14856,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 10,
                        "restTime": 30,
                        "notes": "Mike bets this fails",
                        "userExerciseInstanceId": 14802,
                        "exerciseInstanceSetId": null
                    }
                ]
            },
            {
                "id": 14803,
                "notes": "blah",
                "userWorkoutInstanceId": 14752,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 14857,
                        "orderNumber": 1,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 14803,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 14858,
                        "orderNumber": 3,
                        "repQuantity": 19,
                        "effortQuantity": 19,
                        "restTime": 1290,
                        "notes": "Updated stuff",
                        "userExerciseInstanceId": 14803,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 14859,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 10,
                        "restTime": 30,
                        "notes": "Mike bets this fails",
                        "userExerciseInstanceId": 14803,
                        "exerciseInstanceSetId": null
                    }
                ]
            }
        ]
    },
    {
        "id": 14951,
        "createdOn": "2017-04-01",
        "lastUpdated": "2017-04-01",
        "wasCompleted": false,
        "notes": "2017-04-01",
        "userWorkoutTemplateId": 7601,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": [
            {
                "id": 15001,
                "notes": "blah",
                "userWorkoutInstanceId": 14951,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 15051,
                        "orderNumber": 1,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 15001,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 15052,
                        "orderNumber": 3,
                        "repQuantity": 19,
                        "effortQuantity": 19,
                        "restTime": 1290,
                        "notes": "Updated stuff",
                        "userExerciseInstanceId": 15001,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 15053,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 10,
                        "restTime": 30,
                        "notes": "Mike bets this fails",
                        "userExerciseInstanceId": 15001,
                        "exerciseInstanceSetId": null
                    }
                ]
            }
        ]
    },
    {
        "id": 14952,
        "createdOn": "2017-04-01",
        "lastUpdated": "2017-04-01",
        "wasCompleted": false,
        "notes": "2017-04-01",
        "userWorkoutTemplateId": 7601,
        "workoutInstanceId": null,
        "workoutInstanceName": null,
        "userExerciseInstances": [
            {
                "id": 15002,
                "notes": "blah",
                "userWorkoutInstanceId": 14952,
                "exerciseInstanceId": 6701,
                "userExerciseInstanceSets": [
                    {
                        "id": 15056,
                        "orderNumber": 2,
                        "repQuantity": 10,
                        "effortQuantity": 10,
                        "restTime": 30,
                        "notes": "Mike bets this fails",
                        "userExerciseInstanceId": 15002,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 15054,
                        "orderNumber": 1,
                        "repQuantity": 190,
                        "effortQuantity": 190,
                        "restTime": 1290,
                        "notes": "Mike is cool!",
                        "userExerciseInstanceId": 15002,
                        "exerciseInstanceSetId": null
                    },
                    {
                        "id": 15055,
                        "orderNumber": 3,
                        "repQuantity": 19,
                        "effortQuantity": 19,
                        "restTime": 1290,
                        "notes": "Updated stuff",
                        "userExerciseInstanceId": 15002,
                        "exerciseInstanceSetId": null
                    }
                ]
            }
        ]
    }
]
```
