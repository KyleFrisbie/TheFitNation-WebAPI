(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseInstanceDeleteController',ExerciseInstanceDeleteController);

    ExerciseInstanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExerciseInstance'];

    function ExerciseInstanceDeleteController($uibModalInstance, entity, ExerciseInstance) {
        var vm = this;

        vm.exerciseInstance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExerciseInstance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
