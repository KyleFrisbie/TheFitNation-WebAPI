import json, requests

import SeedBodyPart


def seed(base_address, auth_token):
    muscles = [
        {
            'name': 'Squat'
        }
    ]

    return send_payloads(base_address, auth_token, payloads=payloads)


def send_payloads(base_address, auth_token, payloads):
    endpoint = base_address + '/skill-levels'
    auth = 'Bearer ' + auth_token
    headers = {
        'Authorization': auth,
        'Content-Type': 'application/json'
    }
    responses = []
    for payload in payloads:
        try:
            res = requests.post(endpoint, data=json.dumps(payload), headers=headers)
            responses.append(json.loads(res.text))
        except Exception as e:
            print('Unable to create Muscle: {}. Exception: {}'.format(payload['name'], e))