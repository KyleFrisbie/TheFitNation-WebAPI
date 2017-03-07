(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutTemplateDialogController', WorkoutTemplateDialogController);

    WorkoutTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkoutTemplate', 'UserDemographic', 'WorkoutInstance', 'UserWorkoutTemplate', 'SkillLevel'];

    function WorkoutTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkoutTemplate, UserDemographic, WorkoutInstance, UserWorkoutTemplate, SkillLevel) {
        var vm = this;

        vm.workoutTemplate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userdemographics = UserDemographic.query();
        vm.workoutinstances = WorkoutInstance.query();
        vm.userworkouttemplates = UserWorkoutTemplate.query();
        vm.skilllevels = SkillLevel.query();

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

        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.lastUpdated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
