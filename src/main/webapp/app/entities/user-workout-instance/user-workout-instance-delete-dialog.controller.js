(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWorkoutInstanceDeleteController',UserWorkoutInstanceDeleteController);

    UserWorkoutInstanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserWorkoutInstance'];

    function UserWorkoutInstanceDeleteController($uibModalInstance, entity, UserWorkoutInstance) {
        var vm = this;

        vm.userWorkoutInstance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserWorkoutInstance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
