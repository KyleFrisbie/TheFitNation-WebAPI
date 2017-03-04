(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseSetDialogController', UserExerciseSetDialogController);

    UserExerciseSetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserExerciseSet', 'UserExercise', 'ExerciseSet'];

    function UserExerciseSetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserExerciseSet, UserExercise, ExerciseSet) {
        var vm = this;

        vm.userExerciseSet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userexercises = UserExercise.query();
        vm.exercisesets = ExerciseSet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userExerciseSet.id !== null) {
                UserExerciseSet.update(vm.userExerciseSet, onSaveSuccess, onSaveError);
            } else {
                UserExerciseSet.save(vm.userExerciseSet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userExerciseSetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
