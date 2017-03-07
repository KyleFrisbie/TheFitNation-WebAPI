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
            url: '/user-weight?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userWeight.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-weight/user-weights.html',
                    controller: 'UserWeightController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userWeight');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-weight-detail', {
            parent: 'user-weight',
            url: '/user-weight/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userWeight.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-weight/user-weight-detail.html',
                    controller: 'UserWeightDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userWeight');
                    return $translate.refresh();
                }],
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
                                weightDate: null,
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
