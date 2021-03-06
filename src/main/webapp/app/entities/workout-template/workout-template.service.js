(function() {
    'use strict';
    angular
        .module('theFitNationApp')
        .factory('WorkoutTemplate', WorkoutTemplate);

    WorkoutTemplate.$inject = ['$resource', 'DateUtils'];

    function WorkoutTemplate ($resource, DateUtils) {
        var resourceUrl =  'api/workout-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdOn = DateUtils.convertLocalDateFromServer(data.createdOn);
                        data.lastUpdated = DateUtils.convertLocalDateFromServer(data.lastUpdated);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdOn = DateUtils.convertLocalDateToServer(copy.createdOn);
                    copy.lastUpdated = DateUtils.convertLocalDateToServer(copy.lastUpdated);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdOn = DateUtils.convertLocalDateToServer(copy.createdOn);
                    copy.lastUpdated = DateUtils.convertLocalDateToServer(copy.lastUpdated);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
