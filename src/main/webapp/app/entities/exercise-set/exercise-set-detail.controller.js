(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseSetDetailController', ExerciseSetDetailController);

    ExerciseSetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExerciseSet', 'Exercise'];

    function ExerciseSetDetailController($scope, $rootScope, $stateParams, previousState, entity, ExerciseSet, Exercise) {
        var vm = this;

        vm.exerciseSet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:exerciseSetUpdate', function(event, result) {
            vm.exerciseSet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
