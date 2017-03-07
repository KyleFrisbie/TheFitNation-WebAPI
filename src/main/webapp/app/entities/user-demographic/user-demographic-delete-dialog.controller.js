(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserDemographicDeleteController',UserDemographicDeleteController);

    UserDemographicDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserDemographic'];

    function UserDemographicDeleteController($uibModalInstance, entity, UserDemographic) {
        var vm = this;

        vm.userDemographic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserDemographic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
