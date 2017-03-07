(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseFamilyDetailController', ExerciseFamilyDetailController);

    ExerciseFamilyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExerciseFamily', 'Exercise'];

    function ExerciseFamilyDetailController($scope, $rootScope, $stateParams, previousState, entity, ExerciseFamily, Exercise) {
        var vm = this;

        vm.exerciseFamily = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:exerciseFamilyUpdate', function(event, result) {
            vm.exerciseFamily = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
