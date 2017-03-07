(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseInstanceSetDeleteController',UserExerciseInstanceSetDeleteController);

    UserExerciseInstanceSetDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserExerciseInstanceSet'];

    function UserExerciseInstanceSetDeleteController($uibModalInstance, entity, UserExerciseInstanceSet) {
        var vm = this;

        vm.userExerciseInstanceSet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserExerciseInstanceSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
