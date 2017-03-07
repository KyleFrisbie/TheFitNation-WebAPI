(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('BodyPartDetailController', BodyPartDetailController);

    BodyPartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BodyPart', 'Muscle'];

    function BodyPartDetailController($scope, $rootScope, $stateParams, previousState, entity, BodyPart, Muscle) {
        var vm = this;

        vm.bodyPart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:bodyPartUpdate', function(event, result) {
            vm.bodyPart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
