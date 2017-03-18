#!/user/bin/python

import json
import requests

endpoint = "http://localhost:8080/api/register"

for index in range(0, 10):
    testUser = "testUser{0}".format(index)
    req = requests.post(endpoint, json=
                        {
                            "email": "{0}@{1}.com".format(testUser, "test"),
                            "langKey": "en",
                            "login": testUser,
                            "password": testUser
                        })
