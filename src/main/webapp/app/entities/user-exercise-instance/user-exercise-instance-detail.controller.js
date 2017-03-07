(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseInstanceDetailController', UserExerciseInstanceDetailController);

    UserExerciseInstanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserExerciseInstance', 'UserWorkoutInstance', 'ExerciseInstance', 'UserExerciseInstanceSet'];

    function UserExerciseInstanceDetailController($scope, $rootScope, $stateParams, previousState, entity, UserExerciseInstance, UserWorkoutInstance, ExerciseInstance, UserExerciseInstanceSet) {
        var vm = this;

        vm.userExerciseInstance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userExerciseInstanceUpdate', function(event, result) {
            vm.userExerciseInstance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
