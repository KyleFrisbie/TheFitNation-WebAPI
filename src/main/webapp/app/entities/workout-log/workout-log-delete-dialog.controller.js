(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutLogDeleteController',WorkoutLogDeleteController);

    WorkoutLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkoutLog'];

    function WorkoutLogDeleteController($uibModalInstance, entity, WorkoutLog) {
        var vm = this;

        vm.workoutLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkoutLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
