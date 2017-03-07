(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('workout-template', {
            parent: 'entity',
            url: '/workout-template?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.workoutTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-template/workout-templates.html',
                    controller: 'WorkoutTemplateController',
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
                    $translatePartialLoader.addPart('workoutTemplate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('workout-template-detail', {
            parent: 'workout-template',
            url: '/workout-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.workoutTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-template/workout-template-detail.html',
                    controller: 'WorkoutTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workoutTemplate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WorkoutTemplate', function($stateParams, WorkoutTemplate) {
                    return WorkoutTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'workout-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('workout-template-detail.edit', {
            parent: 'workout-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-template/workout-template-dialog.html',
                    controller: 'WorkoutTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkoutTemplate', function(WorkoutTemplate) {
                            return WorkoutTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workout-template.new', {
            parent: 'workout-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-template/workout-template-dialog.html',
                    controller: 'WorkoutTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                createdOn: null,
                                lastUpdated: null,
                                isPrivate: false,
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('workout-template', null, { reload: 'workout-template' });
                }, function() {
                    $state.go('workout-template');
                });
            }]
        })
        .state('workout-template.edit', {
            parent: 'workout-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-template/workout-template-dialog.html',
                    controller: 'WorkoutTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkoutTemplate', function(WorkoutTemplate) {
                            return WorkoutTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workout-template', null, { reload: 'workout-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workout-template.delete', {
            parent: 'workout-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-template/workout-template-delete-dialog.html',
                    controller: 'WorkoutTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkoutTemplate', function(WorkoutTemplate) {
                            return WorkoutTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workout-template', null, { reload: 'workout-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
