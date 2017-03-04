(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWorkoutTemplateDialogController', UserWorkoutTemplateDialogController);

    UserWorkoutTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserWorkoutTemplate', 'WorkoutLog', 'WorkoutTemplate', 'UserWorkoutInstance'];

    function UserWorkoutTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserWorkoutTemplate, WorkoutLog, WorkoutTemplate, UserWorkoutInstance) {
        var vm = this;

        vm.userWorkoutTemplate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.workoutlogs = WorkoutLog.query();
        vm.workouttemplates = WorkoutTemplate.query();
        vm.userworkoutinstances = UserWorkoutInstance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userWorkoutTemplate.id !== null) {
                UserWorkoutTemplate.update(vm.userWorkoutTemplate, onSaveSuccess, onSaveError);
            } else {
                UserWorkoutTemplate.save(vm.userWorkoutTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userWorkoutTemplateUpdate', result);
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
