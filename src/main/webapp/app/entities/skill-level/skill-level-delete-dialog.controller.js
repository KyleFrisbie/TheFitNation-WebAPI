(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('SkillLevelDeleteController',SkillLevelDeleteController);

    SkillLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'SkillLevel'];

    function SkillLevelDeleteController($uibModalInstance, entity, SkillLevel) {
        var vm = this;

        vm.skillLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SkillLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
