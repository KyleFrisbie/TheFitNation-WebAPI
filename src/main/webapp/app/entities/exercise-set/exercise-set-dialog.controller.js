(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseSetDialogController', ExerciseSetDialogController);

    ExerciseSetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExerciseSet', 'Exercise', 'UserExerciseSet'];

    function ExerciseSetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExerciseSet, Exercise, UserExerciseSet) {
        var vm = this;

        vm.exerciseSet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exercises = Exercise.query();
        vm.userexercisesets = UserExerciseSet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exerciseSet.id !== null) {
                ExerciseSet.update(vm.exerciseSet, onSaveSuccess, onSaveError);
            } else {
                ExerciseSet.save(vm.exerciseSet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:exerciseSetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
