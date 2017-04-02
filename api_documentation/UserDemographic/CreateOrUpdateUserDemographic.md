Create or Update UserDemographic
---

* By (User) owner of Authorization token

---
**Method and Route**\
PUT /api/user-demographics/byLoggedInUser

**Request Headers**\
Content-Type: application/json\
Accept: application/json\
Authorization: Bearer 6e8cc684-8afe-4958-b19c-4a038ebeb3c2


**Post Body**
```javascript
{
	"id":2051, // will be overridden on server side
	"createdOn":"2012-03-11", // required, but will be overridden on server side
	"lastLogin":"2017-03-11", // required, but will be overridden on server side
	"gender":"Male",
	"dateOfBirth":"2017-03-11", // required
	"height":169,
	"unitOfMeasure":"Imperial", // required
	"userId":1954, // may be null (server will associate with user token)
	"userLogin":"kyle", // optional, will be inferred by userId field
	"gyms":[],
	"skillLevelId":1351, // required
	"skillLevelLevel":"Beginner" // optional, will be inferred by skillLevelId field
}
```

**Response Body**
```javascript
{
    "id": 2451,
    "createdOn": "2012-03-11",
    "lastLogin": "2017-03-11",
    "gender": "Male",
    "dateOfBirth": "2017-03-11",
    "height": 169,
    "unitOfMeasure": "Imperial",
    "userId": 4,
    "userLogin": "user",
    "gyms": [],
    "skillLevelId": 1351,
    "skillLevelLevel": "Beginner"
}
```

---
Special Considerations:
* Any field that is optional doesn't have to be passed, but the server will assign a null value to that field.