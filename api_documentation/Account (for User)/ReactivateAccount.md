Reactivate Account
---
* Sends reactivation email for account associated with login.

**Method and Route**\
POST /api/account/reactivate

**Request Headers**\
Content-Type: application/json

**Post Body**
```javascript
{
  "email": "kyle.l.frisbie@gmail.com",
  "langKey": "en",
  "login": "kyle",
  "password": "kyle"
}
```

**Response Body**
```javascript
Reactivation email sent.
```