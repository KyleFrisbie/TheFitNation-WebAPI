(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseFamilyDeleteController',ExerciseFamilyDeleteController);

    ExerciseFamilyDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExerciseFamily'];

    function ExerciseFamilyDeleteController($uibModalInstance, entity, ExerciseFamily) {
        var vm = this;

        vm.exerciseFamily = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExerciseFamily.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
