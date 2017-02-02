(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseDialogController', UserExerciseDialogController);

    UserExerciseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserExercise', 'UserWorkoutInstance', 'Exercise', 'UserExerciseSet'];

    function UserExerciseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserExercise, UserWorkoutInstance, Exercise, UserExerciseSet) {
        var vm = this;

        vm.userExercise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userworkoutinstances = UserWorkoutInstance.query();
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
            if (vm.userExercise.id !== null) {
                UserExercise.update(vm.userExercise, onSaveSuccess, onSaveError);
            } else {
                UserExercise.save(vm.userExercise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userExerciseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
