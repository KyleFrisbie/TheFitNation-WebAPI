(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('UserExerciseSearch', UserExerciseSearch);

    UserExerciseSearch.$inject = ['$resource'];

    function UserExerciseSearch($resource) {
        var resourceUrl =  'api/_search/user-exercises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
