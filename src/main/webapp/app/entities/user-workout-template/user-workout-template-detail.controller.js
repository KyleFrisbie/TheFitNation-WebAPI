(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWorkoutTemplateDetailController', UserWorkoutTemplateDetailController);

    UserWorkoutTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserWorkoutTemplate', 'WorkoutLog', 'WorkoutTemplate', 'UserWorkoutInstance'];

    function UserWorkoutTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, UserWorkoutTemplate, WorkoutLog, WorkoutTemplate, UserWorkoutInstance) {
        var vm = this;

        vm.userWorkoutTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userWorkoutTemplateUpdate', function(event, result) {
            vm.userWorkoutTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
