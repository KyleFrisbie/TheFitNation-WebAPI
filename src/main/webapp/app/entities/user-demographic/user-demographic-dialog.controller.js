(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserDemographicDialogController', UserDemographicDialogController);

    UserDemographicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserDemographic', 'UserWeight', 'Gym', 'WorkoutInstance', 'WorkoutTemplate', 'WorkoutLog', 'User'];

    function UserDemographicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserDemographic, UserWeight, Gym, WorkoutInstance, WorkoutTemplate, WorkoutLog, User) {
        var vm = this;

        vm.userDemographic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userweights = UserWeight.query();
        vm.gyms = Gym.query();
        vm.workoutinstances = WorkoutInstance.query();
        vm.workouttemplates = WorkoutTemplate.query();
        vm.workoutlogs = WorkoutLog.query({filter: 'userdemographic-is-null'});
        $q.all([vm.userDemographic.$promise, vm.workoutlogs.$promise]).then(function() {
            if (!vm.userDemographic.workoutLog || !vm.userDemographic.workoutLog.id) {
                return $q.reject();
            }
            return WorkoutLog.get({id : vm.userDemographic.workoutLog.id}).$promise;
        }).then(function(workoutLog) {
            vm.workoutlogs.push(workoutLog);
        });
        vm.users = User.query();

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

        vm.datePickerOpenStatus.dob = false;
        vm.datePickerOpenStatus.last_login = false;
        vm.datePickerOpenStatus.join_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
