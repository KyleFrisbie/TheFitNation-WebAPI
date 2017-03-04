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
                        data.weight_date = DateUtils.convertDateTimeFromServer(data.weight_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
