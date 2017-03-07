(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('WorkoutTemplateDeleteController',WorkoutTemplateDeleteController);

    WorkoutTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkoutTemplate'];

    function WorkoutTemplateDeleteController($uibModalInstance, entity, WorkoutTemplate) {
        var vm = this;

        vm.workoutTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkoutTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
