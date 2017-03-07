(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserDemographicDetailController', UserDemographicDetailController);

    UserDemographicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserDemographic', 'User', 'Gym', 'UserWeight', 'WorkoutTemplate', 'UserWorkoutTemplate', 'SkillLevel'];

    function UserDemographicDetailController($scope, $rootScope, $stateParams, previousState, entity, UserDemographic, User, Gym, UserWeight, WorkoutTemplate, UserWorkoutTemplate, SkillLevel) {
        var vm = this;

        vm.userDemographic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userDemographicUpdate', function(event, result) {
            vm.userDemographic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
