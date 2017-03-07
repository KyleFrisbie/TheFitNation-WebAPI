(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseInstanceSetDialogController', UserExerciseInstanceSetDialogController);

    UserExerciseInstanceSetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserExerciseInstanceSet', 'UserExerciseInstance', 'ExerciseInstanceSet'];

    function UserExerciseInstanceSetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserExerciseInstanceSet, UserExerciseInstance, ExerciseInstanceSet) {
        var vm = this;

        vm.userExerciseInstanceSet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userexerciseinstances = UserExerciseInstance.query();
        vm.exerciseinstancesets = ExerciseInstanceSet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userExerciseInstanceSet.id !== null) {
                UserExerciseInstanceSet.update(vm.userExerciseInstanceSet, onSaveSuccess, onSaveError);
            } else {
                UserExerciseInstanceSet.save(vm.userExerciseInstanceSet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userExerciseInstanceSetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
