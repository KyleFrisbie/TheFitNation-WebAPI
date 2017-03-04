(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('WorkoutInstance', WorkoutInstance);

    WorkoutInstance.$inject = ['$resource', 'DateUtils'];

    function WorkoutInstance ($resource, DateUtils) {
        var resourceUrl =  'api/workout-instances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.last_updated = DateUtils.convertDateTimeFromServer(data.last_updated);
                        data.created_on = DateUtils.convertDateTimeFromServer(data.created_on);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
