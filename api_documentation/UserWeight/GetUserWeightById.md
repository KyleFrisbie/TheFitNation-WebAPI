Get UserWeight by ID
---

* Gets a User Weight by ID (only if user owns the user weight they're trying to get)

**Method and Route**\
GET /api/user-weights/{id}

**Request Headers**\
Authorization: Bearer 6e8cc684-8afe-4958-b19c-4a038ebeb3c2


**Post Body**
```javascript
none
```

**Response Body**
```json
{
  "id": 6553,
  "weightDate": "2017-03-22",
  "weight": 102,
  "userDemographicId": 2451
}
```

---
Special Considerations:
* Attempting to get a UserWeight (by ID) that isn't owned by the User who owns the Token, or that doesn't exist, will result in a 500 server error.
