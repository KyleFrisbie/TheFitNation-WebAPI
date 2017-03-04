(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('GymDetailController', GymDetailController);

    GymDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gym', 'UserDemographic'];

    function GymDetailController($scope, $rootScope, $stateParams, previousState, entity, Gym, UserDemographic) {
        var vm = this;

        vm.gym = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:gymUpdate', function(event, result) {
            vm.gym = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
