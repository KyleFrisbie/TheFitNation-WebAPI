(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .controller('SkillLevelDetailController', SkillLevelDetailController);

    SkillLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SkillLevel'];

    function SkillLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, SkillLevel) {
        var vm = this;

        vm.skillLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('theFitNationApp:skillLevelUpdate', function(event, result) {
            vm.skillLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
