(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserWorkoutTemplate', UserWorkoutTemplate);

    UserWorkoutTemplate.$inject = ['$resource', 'DateUtils'];

    function UserWorkoutTemplate ($resource, DateUtils) {
        var resourceUrl =  'api/user-workout-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created_on = DateUtils.convertDateTimeFromServer(data.created_on);
                        data.last_updated = DateUtils.convertDateTimeFromServer(data.last_updated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
