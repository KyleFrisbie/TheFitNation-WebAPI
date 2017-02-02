(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('workout-instance', {
            parent: 'entity',
            url: '/workout-instance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkoutInstances'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-instance/workout-instances.html',
                    controller: 'WorkoutInstanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('workout-instance-detail', {
            parent: 'entity',
            url: '/workout-instance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkoutInstance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workout-instance/workout-instance-detail.html',
                    controller: 'WorkoutInstanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WorkoutInstance', function($stateParams, WorkoutInstance) {
                    return WorkoutInstance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'workout-instance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('workout-instance-detail.edit', {
            parent: 'workout-instance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-instance/workout-instance-dialog.html',
                    controller: 'WorkoutInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkoutInstance', function(WorkoutInstance) {
                            return WorkoutInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workout-instance.new', {
            parent: 'workout-instance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-instance/workout-instance-dialog.html',
                    controller: 'WorkoutInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                created_on: null,
                                last_updated: null,
                                rest_between_instances: null,
                                order_number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('workout-instance', null, { reload: 'workout-instance' });
                }, function() {
                    $state.go('workout-instance');
                });
            }]
        })
        .state('workout-instance.edit', {
            parent: 'workout-instance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-instance/workout-instance-dialog.html',
                    controller: 'WorkoutInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkoutInstance', function(WorkoutInstance) {
                            return WorkoutInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workout-instance', null, { reload: 'workout-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workout-instance.delete', {
            parent: 'workout-instance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workout-instance/workout-instance-delete-dialog.html',
                    controller: 'WorkoutInstanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkoutInstance', function(WorkoutInstance) {
                            return WorkoutInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workout-instance', null, { reload: 'workout-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
