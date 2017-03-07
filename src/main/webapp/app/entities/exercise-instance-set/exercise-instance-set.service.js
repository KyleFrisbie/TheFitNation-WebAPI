(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('ExerciseInstanceSet', ExerciseInstanceSet);

    ExerciseInstanceSet.$inject = ['$resource'];

    function ExerciseInstanceSet ($resource) {
        var resourceUrl =  'api/exercise-instance-sets/:id';

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
