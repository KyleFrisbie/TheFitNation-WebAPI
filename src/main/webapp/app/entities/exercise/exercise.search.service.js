(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('ExerciseSearch', ExerciseSearch);

    ExerciseSearch.$inject = ['$resource'];

    function ExerciseSearch($resource) {
        var resourceUrl =  'api/_search/exercises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
