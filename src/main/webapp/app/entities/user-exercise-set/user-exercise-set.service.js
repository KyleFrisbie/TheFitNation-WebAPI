(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserExerciseSet', UserExerciseSet);

    UserExerciseSet.$inject = ['$resource'];

    function UserExerciseSet ($resource) {
        var resourceUrl =  'api/user-exercise-sets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
