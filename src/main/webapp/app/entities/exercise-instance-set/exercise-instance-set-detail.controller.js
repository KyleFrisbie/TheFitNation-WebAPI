(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseInstanceSetDetailController', ExerciseInstanceSetDetailController);

    ExerciseInstanceSetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExerciseInstanceSet', 'ExerciseInstance', 'UserExerciseInstanceSet'];

    function ExerciseInstanceSetDetailController($scope, $rootScope, $stateParams, previousState, entity, ExerciseInstanceSet, ExerciseInstance, UserExerciseInstanceSet) {
        var vm = this;

        vm.exerciseInstanceSet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:exerciseInstanceSetUpdate', function(event, result) {
            vm.exerciseInstanceSet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
