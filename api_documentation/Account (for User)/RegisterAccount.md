Create (and register) a new User
---

_Note: In the account context, the term User and Account are interchangeable._

**Method and Route**\
POST /api/register

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
  none
```
**Note**: All fields are required

Special Considerations:
---
* This route creates a new user _without_ all initialization of user fields, additional POST to "Update Account" is required to initialize other User fields like _firstName_ and _lastName_.
* When a new account is "registered" the account doesn't become activated until the user uses the activation link sent via email (thus a valid email is required).
* All users should have a _UserDemographic_ associated with them -- therefore, a new UserDemographic is also initialized when a new User (account) is registered (with defalut values which must be updated).