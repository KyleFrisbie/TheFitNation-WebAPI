(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserWorkoutInstance', UserWorkoutInstance);

    UserWorkoutInstance.$inject = ['$resource', 'DateUtils'];

    function UserWorkoutInstance ($resource, DateUtils) {
        var resourceUrl =  'api/user-workout-instances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created_on = DateUtils.convertDateTimeFromServer(data.created_on);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
