(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserDemographicDetailController', UserDemographicDetailController);

    UserDemographicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserDemographic', 'Gym', 'UserWeight', 'WorkoutLog', 'WorkoutTemplate'];

    function UserDemographicDetailController($scope, $rootScope, $stateParams, previousState, entity, UserDemographic, Gym, UserWeight, WorkoutLog, WorkoutTemplate) {
        var vm = this;

        vm.userDemographic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userDemographicUpdate', function(event, result) {
            vm.userDemographic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
