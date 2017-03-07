(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserExerciseInstance', UserExerciseInstance);

    UserExerciseInstance.$inject = ['$resource'];

    function UserExerciseInstance ($resource) {
        var resourceUrl =  'api/user-exercise-instances/:id';

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
