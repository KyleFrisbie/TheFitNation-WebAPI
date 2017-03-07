(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('ExerciseInstance', ExerciseInstance);

    ExerciseInstance.$inject = ['$resource'];

    function ExerciseInstance ($resource) {
        var resourceUrl =  'api/exercise-instances/:id';

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
