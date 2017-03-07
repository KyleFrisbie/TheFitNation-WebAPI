(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWeightDialogController', UserWeightDialogController);

    UserWeightDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserWeight', 'UserDemographic'];

    function UserWeightDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserWeight, UserDemographic) {
        var vm = this;

        vm.userWeight = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userdemographics = UserDemographic.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userWeight.id !== null) {
                UserWeight.update(vm.userWeight, onSaveSuccess, onSaveError);
            } else {
                UserWeight.save(vm.userWeight, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:userWeightUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.weightDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
