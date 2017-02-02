(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-workout-instance', {
            parent: 'entity',
            url: '/user-workout-instance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserWorkoutInstances'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-workout-instance/user-workout-instances.html',
                    controller: 'UserWorkoutInstanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-workout-instance-detail', {
            parent: 'entity',
            url: '/user-workout-instance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserWorkoutInstance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-workout-instance/user-workout-instance-detail.html',
                    controller: 'UserWorkoutInstanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserWorkoutInstance', function($stateParams, UserWorkoutInstance) {
                    return UserWorkoutInstance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-workout-instance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-workout-instance-detail.edit', {
            parent: 'user-workout-instance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-instance/user-workout-instance-dialog.html',
                    controller: 'UserWorkoutInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserWorkoutInstance', function(UserWorkoutInstance) {
                            return UserWorkoutInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-workout-instance.new', {
            parent: 'user-workout-instance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-instance/user-workout-instance-dialog.html',
                    controller: 'UserWorkoutInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                created_on: null,
                                was_completed: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-workout-instance', null, { reload: 'user-workout-instance' });
                }, function() {
                    $state.go('user-workout-instance');
                });
            }]
        })
        .state('user-workout-instance.edit', {
            parent: 'user-workout-instance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-instance/user-workout-instance-dialog.html',
                    controller: 'UserWorkoutInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserWorkoutInstance', function(UserWorkoutInstance) {
                            return UserWorkoutInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-workout-instance', null, { reload: 'user-workout-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-workout-instance.delete', {
            parent: 'user-workout-instance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-workout-instance/user-workout-instance-delete-dialog.html',
                    controller: 'UserWorkoutInstanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserWorkoutInstance', function(UserWorkoutInstance) {
                            return UserWorkoutInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-workout-instance', null, { reload: 'user-workout-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
