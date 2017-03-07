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
                        data.createdOn = DateUtils.convertLocalDateFromServer(data.createdOn);
                        data.lastLogin = DateUtils.convertLocalDateFromServer(data.lastLogin);
                        data.dateOfBirth = DateUtils.convertLocalDateFromServer(data.dateOfBirth);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdOn = DateUtils.convertLocalDateToServer(copy.createdOn);
                    copy.lastLogin = DateUtils.convertLocalDateToServer(copy.lastLogin);
                    copy.dateOfBirth = DateUtils.convertLocalDateToServer(copy.dateOfBirth);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdOn = DateUtils.convertLocalDateToServer(copy.createdOn);
                    copy.lastLogin = DateUtils.convertLocalDateToServer(copy.lastLogin);
                    copy.dateOfBirth = DateUtils.convertLocalDateToServer(copy.dateOfBirth);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
