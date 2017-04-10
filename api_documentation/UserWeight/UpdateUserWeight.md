Update UserWeight
---

* By (User) owner of Authorization token
* Can also be used to create a new UserWeight

---
**Method and Route**\
POST /api/user-weights

**Request Headers**\
Content-Type: application/json\
Accept: application/json\
Authorization: Bearer 6e8cc684-8afe-4958-b19c-4a038ebeb3c2


**Post Body**
```json
{
	"weightDate":"2017-03-25",
	"weight":102,
	"id": 3101 // existing UserWeight ID, if null (or an Id that doesn't exist) it will create a new UserWeight
}
```

**Response Body**
```json
{
  "id": 7601,
  "weightDate": "2017-03-25",
  "weight": 102,
  "userDemographicId": 2451
}
```

---
Special Considerations:
* Attempting to update a UserWeight (by ID) that isn't owned by the User who owns the Token, will result in a 500 server error
