(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutTemplateDialogController', WorkoutTemplateDialogController);

    WorkoutTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkoutTemplate', 'WorkoutInstance', 'WorkoutLog'];

    function WorkoutTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkoutTemplate, WorkoutInstance, WorkoutLog) {
        var vm = this;

        vm.workoutTemplate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.workoutinstances = WorkoutInstance.query();
        vm.workoutlogs = WorkoutLog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workoutTemplate.id !== null) {
                WorkoutTemplate.update(vm.workoutTemplate, onSaveSuccess, onSaveError);
            } else {
                WorkoutTemplate.save(vm.workoutTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:workoutTemplateUpdate', result);
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
