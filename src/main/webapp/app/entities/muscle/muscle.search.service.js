(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('MuscleSearch', MuscleSearch);

    MuscleSearch.$inject = ['$resource'];

    function MuscleSearch($resource) {
        var resourceUrl =  'api/_search/muscles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
