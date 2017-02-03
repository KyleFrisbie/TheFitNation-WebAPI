(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutInstanceDetailController', WorkoutInstanceDetailController);

    WorkoutInstanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkoutInstance', 'WorkoutTemplate', 'UserWorkoutInstance', 'Exercise'];

    function WorkoutInstanceDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkoutInstance, WorkoutTemplate, UserWorkoutInstance, Exercise) {
        var vm = this;

        vm.workoutInstance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:workoutInstanceUpdate', function(event, result) {
            vm.workoutInstance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
