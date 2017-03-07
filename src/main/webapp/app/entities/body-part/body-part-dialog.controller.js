(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('BodyPartDialogController', BodyPartDialogController);

    BodyPartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BodyPart', 'Muscle'];

    function BodyPartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BodyPart, Muscle) {
        var vm = this;

        vm.bodyPart = entity;
        vm.clear = clear;
        vm.save = save;
        vm.muscles = Muscle.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bodyPart.id !== null) {
                BodyPart.update(vm.bodyPart, onSaveSuccess, onSaveError);
            } else {
                BodyPart.save(vm.bodyPart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:bodyPartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
