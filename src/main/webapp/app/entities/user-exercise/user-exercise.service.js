(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('UserExercise', UserExercise);

    UserExercise.$inject = ['$resource'];

    function UserExercise ($resource) {
        var resourceUrl =  'api/user-exercises/:id';

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
