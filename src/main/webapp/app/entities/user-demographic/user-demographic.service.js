(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserDemographic', UserDemographic);

    UserDemographic.$inject = ['$resource', 'DateUtils'];

    function UserDemographic ($resource, DateUtils) {
        var resourceUrl =  'api/user-demographics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created_on = DateUtils.convertDateTimeFromServer(data.created_on);
                        data.last_login = DateUtils.convertDateTimeFromServer(data.last_login);
                        data.dob = DateUtils.convertDateTimeFromServer(data.dob);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
