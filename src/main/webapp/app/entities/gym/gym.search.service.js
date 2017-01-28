(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('GymSearch', GymSearch);

    GymSearch.$inject = ['$resource'];

    function GymSearch($resource) {
        var resourceUrl =  'api/_search/gyms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
