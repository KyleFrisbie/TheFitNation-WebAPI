(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserWeight', UserWeight);

    UserWeight.$inject = ['$resource', 'DateUtils'];

    function UserWeight ($resource, DateUtils) {
        var resourceUrl =  'api/user-weights/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.weightDate = DateUtils.convertLocalDateFromServer(data.weightDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.weightDate = DateUtils.convertLocalDateToServer(copy.weightDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.weightDate = DateUtils.convertLocalDateToServer(copy.weightDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
