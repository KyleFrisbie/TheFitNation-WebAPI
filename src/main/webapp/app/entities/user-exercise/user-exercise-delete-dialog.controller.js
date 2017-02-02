(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseDeleteController',UserExerciseDeleteController);

    UserExerciseDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserExercise'];

    function UserExerciseDeleteController($uibModalInstance, entity, UserExercise) {
        var vm = this;

        vm.userExercise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserExercise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
