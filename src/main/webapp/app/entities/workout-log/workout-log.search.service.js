(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('WorkoutLogSearch', WorkoutLogSearch);

    WorkoutLogSearch.$inject = ['$resource'];

    function WorkoutLogSearch($resource) {
        var resourceUrl =  'api/_search/workout-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
