(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('ExerciseSetSearch', ExerciseSetSearch);

    ExerciseSetSearch.$inject = ['$resource'];

    function ExerciseSetSearch($resource) {
        var resourceUrl =  'api/_search/exercise-sets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
