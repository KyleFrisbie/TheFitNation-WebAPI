(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('ExerciseDialogController', ExerciseDialogController);

    ExerciseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Exercise', 'SkillLevel', 'ExerciseInstance', 'Muscle', 'ExerciseFamily'];

    function ExerciseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Exercise, SkillLevel, ExerciseInstance, Muscle, ExerciseFamily) {
        var vm = this;

        vm.exercise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.skilllevels = SkillLevel.query();
        vm.exerciseinstances = ExerciseInstance.query();
        vm.muscles = Muscle.query();
        vm.exercisefamilies = ExerciseFamily.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exercise.id !== null) {
                Exercise.update(vm.exercise, onSaveSuccess, onSaveError);
            } else {
                Exercise.save(vm.exercise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:exerciseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
