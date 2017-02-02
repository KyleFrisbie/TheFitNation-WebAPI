(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutLogDialogController', WorkoutLogDialogController);

    WorkoutLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkoutLog', 'UserDemographic', 'UserWorkoutTemplate'];

    function WorkoutLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkoutLog, UserDemographic, UserWorkoutTemplate) {
        var vm = this;

        vm.workoutLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userdemographics = UserDemographic.query();
        vm.userworkouttemplates = UserWorkoutTemplate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workoutLog.id !== null) {
                WorkoutLog.update(vm.workoutLog, onSaveSuccess, onSaveError);
            } else {
                WorkoutLog.save(vm.workoutLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:workoutLogUpdate', result);
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
