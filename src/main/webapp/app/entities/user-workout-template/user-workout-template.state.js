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
            url: '/user-workout-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserWorkoutTemplates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-workout-template/user-workout-templates.html',
                    controller: 'UserWorkoutTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-workout-template-detail', {
            parent: 'entity',
            url: '/user-workout-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserWorkoutTemplate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-workout-template/user-workout-template-detail.html',
                    controller: 'UserWorkoutTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
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
                                created_on: null,
                                last_updated: null,
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
