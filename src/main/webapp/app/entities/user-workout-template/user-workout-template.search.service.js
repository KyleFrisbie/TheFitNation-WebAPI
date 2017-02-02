(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('UserWorkoutTemplateSearch', UserWorkoutTemplateSearch);

    UserWorkoutTemplateSearch.$inject = ['$resource'];

    function UserWorkoutTemplateSearch($resource) {
        var resourceUrl =  'api/_search/user-workout-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
