(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('workout-log', {
            parent: 'entity',
            url: '/workout-log',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkoutLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-log/workout-logs.html',
                    controller: 'WorkoutLogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('workout-log-detail', {
            parent: 'entity',
            url: '/workout-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkoutLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-log/workout-log-detail.html',
                    controller: 'WorkoutLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WorkoutLog', function($stateParams, WorkoutLog) {
                    return WorkoutLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'workout-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('workout-log-detail.edit', {
            parent: 'workout-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-log/workout-log-dialog.html',
                    controller: 'WorkoutLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkoutLog', function(WorkoutLog) {
                            return WorkoutLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workout-log.new', {
            parent: 'workout-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-log/workout-log-dialog.html',
                    controller: 'WorkoutLogDialogController',
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
                    $state.go('workout-log', null, { reload: 'workout-log' });
                }, function() {
                    $state.go('workout-log');
                });
            }]
        })
        .state('workout-log.edit', {
            parent: 'workout-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-log/workout-log-dialog.html',
                    controller: 'WorkoutLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkoutLog', function(WorkoutLog) {
                            return WorkoutLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workout-log', null, { reload: 'workout-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workout-log.delete', {
            parent: 'workout-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-log/workout-log-delete-dialog.html',
                    controller: 'WorkoutLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkoutLog', function(WorkoutLog) {
                            return WorkoutLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workout-log', null, { reload: 'workout-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
