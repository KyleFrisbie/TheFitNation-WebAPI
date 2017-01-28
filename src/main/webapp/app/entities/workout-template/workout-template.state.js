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
            url: '/workout-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkoutTemplates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-template/workout-templates.html',
                    controller: 'WorkoutTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('workout-template-detail', {
            parent: 'entity',
            url: '/workout-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkoutTemplate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-template/workout-template-detail.html',
                    controller: 'WorkoutTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
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
                                created_on: null,
                                last_updated: null,
                                is_private: false,
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
