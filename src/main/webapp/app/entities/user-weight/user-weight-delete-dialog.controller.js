(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWeightDeleteController',UserWeightDeleteController);

    UserWeightDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserWeight'];

    function UserWeightDeleteController($uibModalInstance, entity, UserWeight) {
        var vm = this;

        vm.userWeight = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserWeight.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
