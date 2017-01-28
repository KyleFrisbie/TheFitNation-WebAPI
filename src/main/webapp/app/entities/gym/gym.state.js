(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gym', {
            parent: 'entity',
            url: '/gym',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gyms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gym/gyms.html',
                    controller: 'GymController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gym-detail', {
            parent: 'entity',
            url: '/gym/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gym'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gym/gym-detail.html',
                    controller: 'GymDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gym', function($stateParams, Gym) {
                    return Gym.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gym',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gym-detail.edit', {
            parent: 'gym-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gym/gym-dialog.html',
                    controller: 'GymDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gym', function(Gym) {
                            return Gym.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gym.new', {
            parent: 'gym',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gym/gym-dialog.html',
                    controller: 'GymDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                location: null,
                                last_visited: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gym', null, { reload: 'gym' });
                }, function() {
                    $state.go('gym');
                });
            }]
        })
        .state('gym.edit', {
            parent: 'gym',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gym/gym-dialog.html',
                    controller: 'GymDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gym', function(Gym) {
                            return Gym.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gym', null, { reload: 'gym' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gym.delete', {
            parent: 'gym',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gym/gym-delete-dialog.html',
                    controller: 'GymDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gym', function(Gym) {
                            return Gym.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gym', null, { reload: 'gym' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
