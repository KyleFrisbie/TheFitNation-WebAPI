import json
import requests

import SeedBodyPart
import SeedSkillLevel

base_address = "http://localhost:8080"


def main_seed():
    try:
        req = requests.get(base_address)
        access_token = get_admin_token(base_address)
        if access_token:
            seed_objects = SeedBodyPart.seed(base_address + '/api', access_token)
            print(seed_objects)
    except Exception as e:
        print("Failed to seed DB: " + e)


def get_admin_token(base_address):
    endpoint = base_address + '/oauth/token'
    headers = {'content-type': 'application/x-www-form-urlencoded'}
    body = 'username=admin&password=admin&grant_type=password&scope=read+write' \
           '&client_secret=my-secret-token-to-change-in-production&client_id=TheFitNationapp' \
           '&submit=login'
    try:
        json_response = json.loads(requests.post(endpoint, data=body, headers=headers).text)
        return json_response['access_token']
    except Exception:
        print('Failed to get token')
        return


main_seed()
