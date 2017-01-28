(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseSetDeleteController',ExerciseSetDeleteController);

    ExerciseSetDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExerciseSet'];

    function ExerciseSetDeleteController($uibModalInstance, entity, ExerciseSet) {
        var vm = this;

        vm.exerciseSet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExerciseSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
