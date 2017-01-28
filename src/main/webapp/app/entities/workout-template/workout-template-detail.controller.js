(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutTemplateDetailController', WorkoutTemplateDetailController);

    WorkoutTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkoutTemplate', 'UserDemographic', 'WorkoutLog', 'WorkoutInstance'];

    function WorkoutTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkoutTemplate, UserDemographic, WorkoutLog, WorkoutInstance) {
        var vm = this;

        vm.workoutTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:workoutTemplateUpdate', function(event, result) {
            vm.workoutTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
