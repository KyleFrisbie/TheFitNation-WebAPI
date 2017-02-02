(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWorkoutInstanceDialogController', UserWorkoutInstanceDialogController);

    UserWorkoutInstanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserWorkoutInstance', 'UserWorkoutTemplate', 'WorkoutInstance', 'UserExercise'];

    function UserWorkoutInstanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserWorkoutInstance, UserWorkoutTemplate, WorkoutInstance, UserExercise) {
        var vm = this;

        vm.userWorkoutInstance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userworkouttemplates = UserWorkoutTemplate.query();
        vm.workoutinstances = WorkoutInstance.query();
        vm.userexercises = UserExercise.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userWorkoutInstance.id !== null) {
                UserWorkoutInstance.update(vm.userWorkoutInstance, onSaveSuccess, onSaveError);
            } else {
                UserWorkoutInstance.save(vm.userWorkoutInstance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userWorkoutInstanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created_on = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
