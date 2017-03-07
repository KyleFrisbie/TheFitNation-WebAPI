(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserExerciseInstanceSet', UserExerciseInstanceSet);

    UserExerciseInstanceSet.$inject = ['$resource'];

    function UserExerciseInstanceSet ($resource) {
        var resourceUrl =  'api/user-exercise-instance-sets/:id';

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
