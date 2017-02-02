(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-exercise', {
            parent: 'entity',
            url: '/user-exercise',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserExercises'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise/user-exercises.html',
                    controller: 'UserExerciseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-exercise-detail', {
            parent: 'entity',
            url: '/user-exercise/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserExercise'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise/user-exercise-detail.html',
                    controller: 'UserExerciseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserExercise', function($stateParams, UserExercise) {
                    return UserExercise.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-exercise',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-exercise-detail.edit', {
            parent: 'user-exercise-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise/user-exercise-dialog.html',
                    controller: 'UserExerciseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExercise', function(UserExercise) {
                            return UserExercise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise.new', {
            parent: 'user-exercise',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise/user-exercise-dialog.html',
                    controller: 'UserExerciseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-exercise', null, { reload: 'user-exercise' });
                }, function() {
                    $state.go('user-exercise');
                });
            }]
        })
        .state('user-exercise.edit', {
            parent: 'user-exercise',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise/user-exercise-dialog.html',
                    controller: 'UserExerciseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExercise', function(UserExercise) {
                            return UserExercise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise', null, { reload: 'user-exercise' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise.delete', {
            parent: 'user-exercise',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise/user-exercise-delete-dialog.html',
                    controller: 'UserExerciseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserExercise', function(UserExercise) {
                            return UserExercise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise', null, { reload: 'user-exercise' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
