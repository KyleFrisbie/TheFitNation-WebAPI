Get OAuth2 Token for User
---

**Method and Route**\
POST /oauth/token\
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

**Form Data**
```
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="username"

user
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="password"

user
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="grant_type"

password
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="scope"

read write
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="client_secret"

my-secret-token-to-change-in-production
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="client_id"

TheFitNationapp
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="submit"

login
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

**Request Headers**\
Content-Type: application/json\
Accept: application/json\
Authorization: Bearer 6e8cc684-8afe-4958-b19c-4a038ebeb3c2


**Post Body**
```javascript
none
```

**Response Body**
```javascript
{
    "access_token": "23f0cf96-1701-4e9d-9160-9caf98fd6a5b",
    "token_type": "bearer",
    "refresh_token": "dd8a7144-e326-47c5-9b25-273f349b4aae",
    "expires_in": 1799,
    "scope": "read write"
}
```

---
Special Considerations:
* The access_token is required in all Read/Write requests using the "Authorization: Bearer <Token>" pattern in the request headers.