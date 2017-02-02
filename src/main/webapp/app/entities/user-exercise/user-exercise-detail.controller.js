(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseDetailController', UserExerciseDetailController);

    UserExerciseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserExercise', 'UserWorkoutInstance', 'Exercise', 'UserExerciseSet'];

    function UserExerciseDetailController($scope, $rootScope, $stateParams, previousState, entity, UserExercise, UserWorkoutInstance, Exercise, UserExerciseSet) {
        var vm = this;

        vm.userExercise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userExerciseUpdate', function(event, result) {
            vm.userExercise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
