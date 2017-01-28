(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('WorkoutInstanceSearch', WorkoutInstanceSearch);

    WorkoutInstanceSearch.$inject = ['$resource'];

    function WorkoutInstanceSearch($resource) {
        var resourceUrl =  'api/_search/workout-instances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
