import json, requests


def seed(base_address, auth_token):
    payloads = [
        {'level': 'Beginner'},
        {'level': 'Intermediate'},
        {'level': 'Advanced'},
    ]
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
            print('Unable to create SkillLevel: {}. Exception: {}'.format(payload['level'], e))
    return responses
