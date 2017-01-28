(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .factory('UserDemographicSearch', UserDemographicSearch);

    UserDemographicSearch.$inject = ['$resource'];

    function UserDemographicSearch($resource) {
        var resourceUrl =  'api/_search/user-demographics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
