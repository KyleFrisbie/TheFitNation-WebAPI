(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutInstanceDialogController', WorkoutInstanceDialogController);

    WorkoutInstanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkoutInstance', 'WorkoutTemplate', 'Muscle', 'Exercise', 'WorkoutLog'];

    function WorkoutInstanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkoutInstance, WorkoutTemplate, Muscle, Exercise, WorkoutLog) {
        var vm = this;

        vm.workoutInstance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.workouttemplates = WorkoutTemplate.query();
        vm.muscles = Muscle.query();
        vm.exercises = Exercise.query();
        vm.workoutlogs = WorkoutLog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workoutInstance.id !== null) {
                WorkoutInstance.update(vm.workoutInstance, onSaveSuccess, onSaveError);
            } else {
                WorkoutInstance.save(vm.workoutInstance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:workoutInstanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created_on = false;
        vm.datePickerOpenStatus.last_updated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
