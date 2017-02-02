(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('UserWorkoutInstanceSearch', UserWorkoutInstanceSearch);

    UserWorkoutInstanceSearch.$inject = ['$resource'];

    function UserWorkoutInstanceSearch($resource) {
        var resourceUrl =  'api/_search/user-workout-instances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
