Delete UserWeight
---

**Method and Route**\
DELETE /api/user-weights/{id}

**Request Headers**\
Authorization: Bearer 6e8cc684-8afe-4958-b19c-4a038ebeb3c2


**Post Body**
```javascript
none
```

**Response Body**
```javascript
none
```

---
Special Considerations:
* Attempting to delete a UserWeight (by ID) that isn't owned by the User who owns the Token, will result in a 500 Server error.
