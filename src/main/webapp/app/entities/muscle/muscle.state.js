(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('muscle', {
            parent: 'entity',
            url: '/muscle',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Muscles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/muscle/muscles.html',
                    controller: 'MuscleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('muscle-detail', {
            parent: 'entity',
            url: '/muscle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Muscle'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/muscle/muscle-detail.html',
                    controller: 'MuscleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Muscle', function($stateParams, Muscle) {
                    return Muscle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'muscle',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('muscle-detail.edit', {
            parent: 'muscle-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/muscle/muscle-dialog.html',
                    controller: 'MuscleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Muscle', function(Muscle) {
                            return Muscle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('muscle.new', {
            parent: 'muscle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/muscle/muscle-dialog.html',
                    controller: 'MuscleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('muscle', null, { reload: 'muscle' });
                }, function() {
                    $state.go('muscle');
                });
            }]
        })
        .state('muscle.edit', {
            parent: 'muscle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/muscle/muscle-dialog.html',
                    controller: 'MuscleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Muscle', function(Muscle) {
                            return Muscle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('muscle', null, { reload: 'muscle' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('muscle.delete', {
            parent: 'muscle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/muscle/muscle-delete-dialog.html',
                    controller: 'MuscleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Muscle', function(Muscle) {
                            return Muscle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('muscle', null, { reload: 'muscle' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
