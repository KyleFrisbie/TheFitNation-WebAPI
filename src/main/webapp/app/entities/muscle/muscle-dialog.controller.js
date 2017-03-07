(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('MuscleDialogController', MuscleDialogController);

    MuscleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Muscle', 'Exercise', 'BodyPart'];

    function MuscleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Muscle, Exercise, BodyPart) {
        var vm = this;

        vm.muscle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exercises = Exercise.query();
        vm.bodyparts = BodyPart.query({filter: 'muscle-is-null'});
        $q.all([vm.muscle.$promise, vm.bodyparts.$promise]).then(function() {
            if (!vm.muscle.bodyPartId) {
                return $q.reject();
            }
            return BodyPart.get({id : vm.muscle.bodyPartId}).$promise;
        }).then(function(bodyPart) {
            vm.bodyparts.push(bodyPart);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.muscle.id !== null) {
                Muscle.update(vm.muscle, onSaveSuccess, onSaveError);
            } else {
                Muscle.save(vm.muscle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:muscleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
