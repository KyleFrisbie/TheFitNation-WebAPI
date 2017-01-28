(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutLogDetailController', WorkoutLogDetailController);

    WorkoutLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkoutLog', 'WorkoutTemplate', 'WorkoutInstance'];

    function WorkoutLogDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkoutLog, WorkoutTemplate, WorkoutInstance) {
        var vm = this;

        vm.workoutLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:workoutLogUpdate', function(event, result) {
            vm.workoutLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
