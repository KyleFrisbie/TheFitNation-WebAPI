(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutInstanceDeleteController',WorkoutInstanceDeleteController);

    WorkoutInstanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkoutInstance'];

    function WorkoutInstanceDeleteController($uibModalInstance, entity, WorkoutInstance) {
        var vm = this;

        vm.workoutInstance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkoutInstance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
