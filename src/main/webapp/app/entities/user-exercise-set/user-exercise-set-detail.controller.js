(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseSetDetailController', UserExerciseSetDetailController);

    UserExerciseSetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserExerciseSet', 'UserExercise', 'ExerciseSet'];

    function UserExerciseSetDetailController($scope, $rootScope, $stateParams, previousState, entity, UserExerciseSet, UserExercise, ExerciseSet) {
        var vm = this;

        vm.userExerciseSet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userExerciseSetUpdate', function(event, result) {
            vm.userExerciseSet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
