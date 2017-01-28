(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('MuscleDeleteController',MuscleDeleteController);

    MuscleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Muscle'];

    function MuscleDeleteController($uibModalInstance, entity, Muscle) {
        var vm = this;

        vm.muscle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Muscle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
