(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseInstanceSetDetailController', UserExerciseInstanceSetDetailController);

    UserExerciseInstanceSetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserExerciseInstanceSet', 'UserExerciseInstance', 'ExerciseInstanceSet'];

    function UserExerciseInstanceSetDetailController($scope, $rootScope, $stateParams, previousState, entity, UserExerciseInstanceSet, UserExerciseInstance, ExerciseInstanceSet) {
        var vm = this;

        vm.userExerciseInstanceSet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userExerciseInstanceSetUpdate', function(event, result) {
            vm.userExerciseInstanceSet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
