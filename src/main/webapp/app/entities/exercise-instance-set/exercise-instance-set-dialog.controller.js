(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseInstanceSetDialogController', ExerciseInstanceSetDialogController);

    ExerciseInstanceSetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExerciseInstanceSet', 'ExerciseInstance', 'UserExerciseInstanceSet'];

    function ExerciseInstanceSetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExerciseInstanceSet, ExerciseInstance, UserExerciseInstanceSet) {
        var vm = this;

        vm.exerciseInstanceSet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exerciseinstances = ExerciseInstance.query();
        vm.userexerciseinstancesets = UserExerciseInstanceSet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exerciseInstanceSet.id !== null) {
                ExerciseInstanceSet.update(vm.exerciseInstanceSet, onSaveSuccess, onSaveError);
            } else {
                ExerciseInstanceSet.save(vm.exerciseInstanceSet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:exerciseInstanceSetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
