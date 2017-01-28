(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('Gym', Gym);

    Gym.$inject = ['$resource', 'DateUtils'];

    function Gym ($resource, DateUtils) {
        var resourceUrl =  'api/gyms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.last_visited = DateUtils.convertDateTimeFromServer(data.last_visited);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
