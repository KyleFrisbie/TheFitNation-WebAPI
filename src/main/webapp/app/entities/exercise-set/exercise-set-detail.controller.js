(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseSetDetailController', ExerciseSetDetailController);

    ExerciseSetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExerciseSet', 'Exercise', 'UserExerciseSet'];

    function ExerciseSetDetailController($scope, $rootScope, $stateParams, previousState, entity, ExerciseSet, Exercise, UserExerciseSet) {
        var vm = this;

        vm.exerciseSet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:exerciseSetUpdate', function(event, result) {
            vm.exerciseSet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
