Create or Update UserWeight
---

* By (User) owner of Authorization token

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
	"id":null // must be null (doesn't have to be present), will be generated by server
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
