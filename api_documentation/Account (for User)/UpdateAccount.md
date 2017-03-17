Update Account (User)
---

**Method and Route**
POST /api/account

**Request Headers**
Content-Type: application/json
Accept: application/json
Authorization: Bearer 51988575-031c-4be6-b90f-5b0157eccd45


**Post Body**
```javascript
{
  "id": 0, // optional, ignored and auto populated by server
  "activated": true, // no matter what you pass in this field, a user will only become activated through the email link
  "authorities": [], // optional, only admin can update roles
  "createdBy": "string", // optional, ignored and auto populated by server
  "createdDate": "2017-03-17T20:22:47.313Z", // optional, ignored and auto populated by server
  "email": "email@email.com", // required, may be updated as long as new email is unique
  "firstName": "string", // optional, will be null if not passed
  "imageUrl": "string", // optional, will be null if not passed
  "lastModifiedBy": "string", // optional, ignored and auto populated by server
  "lastModifiedDate": "2017-03-17T20:22:47.313Z", // optional, ignored and auto populated by server
  "lastName": "string", // optional, will be null if not passed
  "login": "user", // required, login must match User who owns API key. Can't change login even if a different login is sent to server
  "langKey": "en" // required, en is only language currently supported
}
```

**Response Body**
```
none
```

Special Considerations
---
* Updates a user based on the owner of the token passed to the server
* Login must be passed in post body to ensure a unique pairing between logins/emails