Get All UserWeights
---

* Gets all user weights for the current user (by token)

**Method and Route**\
GET /api/user-weights/byLoggedInUser

**Request Headers**\
Authorization: Bearer 6e8cc684-8afe-4958-b19c-4a038ebeb3c2


**Post Body**
```javascript
none
```

**Response Body**
```javascript
[
  {
    "id": 6553,
    "weightDate": "2017-03-22",
    "weight": 102,
    "userDemographicId": 2451
  },
  {
    "id": 6552,
    "weightDate": "2017-02-25",
    "weight": 12,
    "userDemographicId": 2451
  }
]
```