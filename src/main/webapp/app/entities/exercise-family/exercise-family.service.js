(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('ExerciseFamily', ExerciseFamily);

    ExerciseFamily.$inject = ['$resource'];

    function ExerciseFamily ($resource) {
        var resourceUrl =  'api/exercise-families/:id';

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
