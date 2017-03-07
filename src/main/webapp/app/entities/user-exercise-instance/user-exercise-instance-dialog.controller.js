(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseInstanceDialogController', UserExerciseInstanceDialogController);

    UserExerciseInstanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserExerciseInstance', 'UserWorkoutInstance', 'ExerciseInstance', 'UserExerciseInstanceSet'];

    function UserExerciseInstanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserExerciseInstance, UserWorkoutInstance, ExerciseInstance, UserExerciseInstanceSet) {
        var vm = this;

        vm.userExerciseInstance = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userworkoutinstances = UserWorkoutInstance.query();
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
            if (vm.userExerciseInstance.id !== null) {
                UserExerciseInstance.update(vm.userExerciseInstance, onSaveSuccess, onSaveError);
            } else {
                UserExerciseInstance.save(vm.userExerciseInstance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userExerciseInstanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
