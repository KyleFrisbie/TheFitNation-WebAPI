(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserDemographicDialogController', UserDemographicDialogController);

    UserDemographicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserDemographic', 'User', 'Gym', 'UserWeight', 'WorkoutTemplate', 'UserWorkoutTemplate', 'SkillLevel'];

    function UserDemographicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserDemographic, User, Gym, UserWeight, WorkoutTemplate, UserWorkoutTemplate, SkillLevel) {
        var vm = this;

        vm.userDemographic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.gyms = Gym.query();
        vm.userweights = UserWeight.query();
        vm.workouttemplates = WorkoutTemplate.query();
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
            if (vm.userDemographic.id !== null) {
                UserDemographic.update(vm.userDemographic, onSaveSuccess, onSaveError);
            } else {
                UserDemographic.save(vm.userDemographic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userDemographicUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.lastLogin = false;
        vm.datePickerOpenStatus.dateOfBirth = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
