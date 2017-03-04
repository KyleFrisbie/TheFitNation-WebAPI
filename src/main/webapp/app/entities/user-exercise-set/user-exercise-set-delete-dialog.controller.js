(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserExerciseSetDeleteController',UserExerciseSetDeleteController);

    UserExerciseSetDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserExerciseSet'];

    function UserExerciseSetDeleteController($uibModalInstance, entity, UserExerciseSet) {
        var vm = this;

        vm.userExerciseSet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserExerciseSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
