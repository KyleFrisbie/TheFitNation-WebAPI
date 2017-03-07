(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-workout-template', {
            parent: 'entity',
            url: '/user-workout-template?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userWorkoutTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-workout-template/user-workout-templates.html',
                    controller: 'UserWorkoutTemplateController',
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
                    $translatePartialLoader.addPart('userWorkoutTemplate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-workout-template-detail', {
            parent: 'user-workout-template',
            url: '/user-workout-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userWorkoutTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-workout-template/user-workout-template-detail.html',
                    controller: 'UserWorkoutTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userWorkoutTemplate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserWorkoutTemplate', function($stateParams, UserWorkoutTemplate) {
                    return UserWorkoutTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-workout-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-workout-template-detail.edit', {
            parent: 'user-workout-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-template/user-workout-template-dialog.html',
                    controller: 'UserWorkoutTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserWorkoutTemplate', function(UserWorkoutTemplate) {
                            return UserWorkoutTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-workout-template.new', {
            parent: 'user-workout-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-template/user-workout-template-dialog.html',
                    controller: 'UserWorkoutTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdOn: null,
                                lastUpdated: null,
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-workout-template', null, { reload: 'user-workout-template' });
                }, function() {
                    $state.go('user-workout-template');
                });
            }]
        })
        .state('user-workout-template.edit', {
            parent: 'user-workout-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-template/user-workout-template-dialog.html',
                    controller: 'UserWorkoutTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserWorkoutTemplate', function(UserWorkoutTemplate) {
                            return UserWorkoutTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-workout-template', null, { reload: 'user-workout-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-workout-template.delete', {
            parent: 'user-workout-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-template/user-workout-template-delete-dialog.html',
                    controller: 'UserWorkoutTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserWorkoutTemplate', function(UserWorkoutTemplate) {
                            return UserWorkoutTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-workout-template', null, { reload: 'user-workout-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
