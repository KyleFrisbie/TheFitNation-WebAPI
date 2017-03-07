(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('GymDialogController', GymDialogController);

    GymDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Gym', 'UserDemographic', 'Location'];

    function GymDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Gym, UserDemographic, Location) {
        var vm = this;

        vm.gym = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userdemographics = UserDemographic.query();
        vm.locations = Location.query({filter: 'gym-is-null'});
        $q.all([vm.gym.$promise, vm.locations.$promise]).then(function() {
            if (!vm.gym.locationId) {
                return $q.reject();
            }
            return Location.get({id : vm.gym.locationId}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gym.id !== null) {
                Gym.update(vm.gym, onSaveSuccess, onSaveError);
            } else {
                Gym.save(vm.gym, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('theFitNationApp:gymUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
