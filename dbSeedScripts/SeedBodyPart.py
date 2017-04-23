import json, requests


def seed(base_address, auth_token):
    payloads = [
        {'name': 'Legs'},
        {'name': 'Lower Back'},
        {'name': 'Back'},
        {'name': 'Upper Back'},
        {'name': 'Chest'},
        {'name': 'Sholders'},
        {'name': 'Sholders'},
        {'name': 'Arms'},
    ]
    endpoint = base_address + '/body-parts'
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
            print('Unable to create BodyPart: {}. Exception: {}'.format(payload['name'], e))
    return responses


def seed_bodypart(base_address, auth_token, bodypart):
    payload = {'name': bodypart}
    endpoint = base_address + '/body-parts'
    auth = 'Bearer ' + auth_token
    headers = {
        'Authorization': auth,
        'Content-Type': 'application/json'
    }
    try:
        res = requests.post(endpoint, data=json.dumps(payload), headers=headers)
    except Exception as e:
        print('Unable to create BodyPart: {}. Exception: {}'.format(payload['name'], e))
    return res
