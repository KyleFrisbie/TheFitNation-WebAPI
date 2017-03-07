(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('AddressDetailController', AddressDetailController);

    AddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Address', 'Location'];

    function AddressDetailController($scope, $rootScope, $stateParams, previousState, entity, Address, Location) {
        var vm = this;

        vm.address = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:addressUpdate', function(event, result) {
            vm.address = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
