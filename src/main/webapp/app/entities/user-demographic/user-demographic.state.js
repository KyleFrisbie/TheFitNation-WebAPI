(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-demographic', {
            parent: 'entity',
            url: '/user-demographic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userDemographic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-demographic/user-demographics.html',
                    controller: 'UserDemographicController',
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
                    $translatePartialLoader.addPart('userDemographic');
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('unitOfMeasure');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-demographic-detail', {
            parent: 'user-demographic',
            url: '/user-demographic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userDemographic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-demographic/user-demographic-detail.html',
                    controller: 'UserDemographicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userDemographic');
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('unitOfMeasure');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserDemographic', function($stateParams, UserDemographic) {
                    return UserDemographic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-demographic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-demographic-detail.edit', {
            parent: 'user-demographic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-dialog.html',
                    controller: 'UserDemographicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDemographic', function(UserDemographic) {
                            return UserDemographic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-demographic.new', {
            parent: 'user-demographic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-dialog.html',
                    controller: 'UserDemographicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdOn: null,
                                lastLogin: null,
                                gender: null,
                                dateOfBirth: null,
                                height: null,
                                unitOfMeasure: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-demographic', null, { reload: 'user-demographic' });
                }, function() {
                    $state.go('user-demographic');
                });
            }]
        })
        .state('user-demographic.edit', {
            parent: 'user-demographic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-dialog.html',
                    controller: 'UserDemographicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDemographic', function(UserDemographic) {
                            return UserDemographic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-demographic', null, { reload: 'user-demographic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-demographic.delete', {
            parent: 'user-demographic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-delete-dialog.html',
                    controller: 'UserDemographicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserDemographic', function(UserDemographic) {
                            return UserDemographic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-demographic', null, { reload: 'user-demographic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
