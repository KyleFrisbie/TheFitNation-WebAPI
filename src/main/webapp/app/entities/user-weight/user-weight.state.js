(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-weight', {
            parent: 'entity',
            url: '/user-weight',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserWeights'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-weight/user-weights.html',
                    controller: 'UserWeightController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-weight-detail', {
            parent: 'entity',
            url: '/user-weight/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserWeight'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-weight/user-weight-detail.html',
                    controller: 'UserWeightDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserWeight', function($stateParams, UserWeight) {
                    return UserWeight.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-weight',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-weight-detail.edit', {
            parent: 'user-weight-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-weight/user-weight-dialog.html',
                    controller: 'UserWeightDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserWeight', function(UserWeight) {
                            return UserWeight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-weight.new', {
            parent: 'user-weight',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-weight/user-weight-dialog.html',
                    controller: 'UserWeightDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                weight_date: null,
                                weight: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-weight', null, { reload: 'user-weight' });
                }, function() {
                    $state.go('user-weight');
                });
            }]
        })
        .state('user-weight.edit', {
            parent: 'user-weight',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-weight/user-weight-dialog.html',
                    controller: 'UserWeightDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserWeight', function(UserWeight) {
                            return UserWeight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-weight', null, { reload: 'user-weight' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-weight.delete', {
            parent: 'user-weight',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-weight/user-weight-delete-dialog.html',
                    controller: 'UserWeightDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserWeight', function(UserWeight) {
                            return UserWeight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-weight', null, { reload: 'user-weight' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
