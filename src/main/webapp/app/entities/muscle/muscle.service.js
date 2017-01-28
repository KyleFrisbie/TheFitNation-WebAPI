(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('Muscle', Muscle);

    Muscle.$inject = ['$resource'];

    function Muscle ($resource) {
        var resourceUrl =  'api/muscles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
