(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseInstanceDialogController', ExerciseInstanceDialogController);

    ExerciseInstanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExerciseInstance', 'WorkoutInstance', 'UserExerciseInstance', 'ExerciseInstanceSet', 'Exercise', 'Unit'];

    function ExerciseInstanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExerciseInstance, WorkoutInstance, UserExerciseInstance, ExerciseInstanceSet, Exercise, Unit) {
        var vm = this;

        vm.exerciseInstance = entity;
        vm.clear = clear;
        vm.save = save;
        vm.workoutinstances = WorkoutInstance.query();
        vm.userexerciseinstances = UserExerciseInstance.query();
        vm.exerciseinstancesets = ExerciseInstanceSet.query();
        vm.exercises = Exercise.query();
        vm.units = Unit.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exerciseInstance.id !== null) {
                ExerciseInstance.update(vm.exerciseInstance, onSaveSuccess, onSaveError);
            } else {
                ExerciseInstance.save(vm.exerciseInstance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:exerciseInstanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
