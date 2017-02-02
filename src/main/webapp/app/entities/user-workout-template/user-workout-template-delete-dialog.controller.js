(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWorkoutTemplateDeleteController',UserWorkoutTemplateDeleteController);

    UserWorkoutTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserWorkoutTemplate'];

    function UserWorkoutTemplateDeleteController($uibModalInstance, entity, UserWorkoutTemplate) {
        var vm = this;

        vm.userWorkoutTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserWorkoutTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
