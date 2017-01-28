(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('MuscleDetailController', MuscleDetailController);

    MuscleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Muscle', 'WorkoutInstance', 'Exercise'];

    function MuscleDetailController($scope, $rootScope, $stateParams, previousState, entity, Muscle, WorkoutInstance, Exercise) {
        var vm = this;

        vm.muscle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:muscleUpdate', function(event, result) {
            vm.muscle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
