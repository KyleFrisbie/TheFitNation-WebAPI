(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('UserWeightDetailController', UserWeightDetailController);

    UserWeightDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserWeight', 'UserDemographic'];

    function UserWeightDetailController($scope, $rootScope, $stateParams, previousState, entity, UserWeight, UserDemographic) {
        var vm = this;

        vm.userWeight = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:userWeightUpdate', function(event, result) {
            vm.userWeight = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
