(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutLogDetailController', WorkoutLogDetailController);

    WorkoutLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkoutLog', 'UserDemographic', 'UserWorkoutTemplate'];

    function WorkoutLogDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkoutLog, UserDemographic, UserWorkoutTemplate) {
        var vm = this;

        vm.workoutLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:workoutLogUpdate', function(event, result) {
            vm.workoutLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
