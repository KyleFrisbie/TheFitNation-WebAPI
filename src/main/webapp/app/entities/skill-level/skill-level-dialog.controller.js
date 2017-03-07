(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('SkillLevelDialogController', SkillLevelDialogController);

    SkillLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SkillLevel'];

    function SkillLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SkillLevel) {
        var vm = this;

        vm.skillLevel = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.skillLevel.id !== null) {
                SkillLevel.update(vm.skillLevel, onSaveSuccess, onSaveError);
            } else {
                SkillLevel.save(vm.skillLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:skillLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
