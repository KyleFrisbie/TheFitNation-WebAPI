(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('UserWeightSearch', UserWeightSearch);

    UserWeightSearch.$inject = ['$resource'];

    function UserWeightSearch($resource) {
        var resourceUrl =  'api/_search/user-weights/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
