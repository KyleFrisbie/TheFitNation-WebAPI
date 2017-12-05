import json, requests


def seed_exercises(base_address, auth_token):
    payloads = [
        {'level': 'Beginner'},
        {'level': 'Intermediate'},
        {'level': 'Advanced'},
    ]
    endpoint = base_address + '/skill-levels'
    bearer = 'Bearer ' + auth_token
    auth = 'Bearer ' + auth_token
    headers = {
        'Authorization': bearer,
        'Content-Type': 'application/json'
    }
    responses = []
    for payload in payloads:
        try:
            print(json.dumps(payload))
            res = requests.post(endpoint, data=json.dumps(payload), headers=headers)
            responses.append(json.loads(res.text))
            print(res.text)
        except Exception as e:
            print('Unable to create SkillLevel: {}. Exception: {}'.format(payload['level'], e))
    return responses
