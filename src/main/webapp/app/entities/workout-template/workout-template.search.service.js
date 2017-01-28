(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('WorkoutTemplateSearch', WorkoutTemplateSearch);

    WorkoutTemplateSearch.$inject = ['$resource'];

    function WorkoutTemplateSearch($resource) {
        var resourceUrl =  'api/_search/workout-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
