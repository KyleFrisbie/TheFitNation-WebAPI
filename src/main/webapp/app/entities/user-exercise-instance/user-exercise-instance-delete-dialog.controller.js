(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseInstanceDeleteController',UserExerciseInstanceDeleteController);

    UserExerciseInstanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserExerciseInstance'];

    function UserExerciseInstanceDeleteController($uibModalInstance, entity, UserExerciseInstance) {
        var vm = this;

        vm.userExerciseInstance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserExerciseInstance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
