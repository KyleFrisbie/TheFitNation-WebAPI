(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseInstanceSetDeleteController',ExerciseInstanceSetDeleteController);

    ExerciseInstanceSetDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExerciseInstanceSet'];

    function ExerciseInstanceSetDeleteController($uibModalInstance, entity, ExerciseInstanceSet) {
        var vm = this;

        vm.exerciseInstanceSet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExerciseInstanceSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
