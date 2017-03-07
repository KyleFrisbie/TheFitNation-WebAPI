(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('SkillLevel', SkillLevel);

    SkillLevel.$inject = ['$resource'];

    function SkillLevel ($resource) {
        var resourceUrl =  'api/skill-levels/:id';

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
