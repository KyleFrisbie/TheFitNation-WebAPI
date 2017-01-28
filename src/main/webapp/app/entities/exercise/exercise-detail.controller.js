(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseDetailController', ExerciseDetailController);

    ExerciseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exercise', 'WorkoutInstance', 'Muscle', 'ExerciseSet'];

    function ExerciseDetailController($scope, $rootScope, $stateParams, previousState, entity, Exercise, WorkoutInstance, Muscle, ExerciseSet) {
        var vm = this;

        vm.exercise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:exerciseUpdate', function(event, result) {
            vm.exercise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
