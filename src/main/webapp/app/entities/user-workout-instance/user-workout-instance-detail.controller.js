(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWorkoutInstanceDetailController', UserWorkoutInstanceDetailController);

    UserWorkoutInstanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserWorkoutInstance', 'UserWorkoutTemplate', 'WorkoutInstance', 'UserExerciseInstance'];

    function UserWorkoutInstanceDetailController($scope, $rootScope, $stateParams, previousState, entity, UserWorkoutInstance, UserWorkoutTemplate, WorkoutInstance, UserExerciseInstance) {
        var vm = this;

        vm.userWorkoutInstance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userWorkoutInstanceUpdate', function(event, result) {
            vm.userWorkoutInstance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
