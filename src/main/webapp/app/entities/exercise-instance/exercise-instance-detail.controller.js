(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseInstanceDetailController', ExerciseInstanceDetailController);

    ExerciseInstanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExerciseInstance', 'WorkoutInstance', 'UserExerciseInstance', 'ExerciseInstanceSet', 'Exercise', 'Unit'];

    function ExerciseInstanceDetailController($scope, $rootScope, $stateParams, previousState, entity, ExerciseInstance, WorkoutInstance, UserExerciseInstance, ExerciseInstanceSet, Exercise, Unit) {
        var vm = this;

        vm.exerciseInstance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:exerciseInstanceUpdate', function(event, result) {
            vm.exerciseInstance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
