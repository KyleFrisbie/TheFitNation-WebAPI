(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('GymDialogController', GymDialogController);

    GymDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gym', 'UserDemographic'];

    function GymDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gym, UserDemographic) {
        var vm = this;

        vm.gym = entity;
        vm.clear = clear;
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
            if (vm.gym.id !== null) {
                Gym.update(vm.gym, onSaveSuccess, onSaveError);
            } else {
                Gym.save(vm.gym, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:gymUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
