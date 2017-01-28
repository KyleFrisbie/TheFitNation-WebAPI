(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('ExerciseSet', ExerciseSet);

    ExerciseSet.$inject = ['$resource'];

    function ExerciseSet ($resource) {
        var resourceUrl =  'api/exercise-sets/:id';

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
