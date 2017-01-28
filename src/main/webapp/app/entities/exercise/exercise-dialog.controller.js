(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseDialogController', ExerciseDialogController);

    ExerciseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Exercise', 'Muscle', 'ExerciseSet', 'WorkoutInstance'];

    function ExerciseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Exercise, Muscle, ExerciseSet, WorkoutInstance) {
        var vm = this;

        vm.exercise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.muscles = Muscle.query();
        vm.exercisesets = ExerciseSet.query();
        vm.workoutinstances = WorkoutInstance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exercise.id !== null) {
                Exercise.update(vm.exercise, onSaveSuccess, onSaveError);
            } else {
                Exercise.save(vm.exercise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:exerciseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
