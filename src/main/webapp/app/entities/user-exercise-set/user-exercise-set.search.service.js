(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('UserExerciseSetSearch', UserExerciseSetSearch);

    UserExerciseSetSearch.$inject = ['$resource'];

    function UserExerciseSetSearch($resource) {
        var resourceUrl =  'api/_search/user-exercise-sets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
