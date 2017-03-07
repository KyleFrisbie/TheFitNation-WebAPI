(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseFamilyDialogController', ExerciseFamilyDialogController);

    ExerciseFamilyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExerciseFamily', 'Exercise'];

    function ExerciseFamilyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExerciseFamily, Exercise) {
        var vm = this;

        vm.exerciseFamily = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exercises = Exercise.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exerciseFamily.id !== null) {
                ExerciseFamily.update(vm.exerciseFamily, onSaveSuccess, onSaveError);
            } else {
                ExerciseFamily.save(vm.exerciseFamily, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:exerciseFamilyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
