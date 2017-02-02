(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-exercise-set', {
            parent: 'entity',
            url: '/user-exercise-set',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserExerciseSets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise-set/user-exercise-sets.html',
                    controller: 'UserExerciseSetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-exercise-set-detail', {
            parent: 'entity',
            url: '/user-exercise-set/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserExerciseSet'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise-set/user-exercise-set-detail.html',
                    controller: 'UserExerciseSetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserExerciseSet', function($stateParams, UserExerciseSet) {
                    return UserExerciseSet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-exercise-set',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-exercise-set-detail.edit', {
            parent: 'user-exercise-set-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-set/user-exercise-set-dialog.html',
                    controller: 'UserExerciseSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExerciseSet', function(UserExerciseSet) {
                            return UserExerciseSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise-set.new', {
            parent: 'user-exercise-set',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-set/user-exercise-set-dialog.html',
                    controller: 'UserExerciseSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                order_number: null,
                                reps: null,
                                weight: null,
                                rest: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-exercise-set', null, { reload: 'user-exercise-set' });
                }, function() {
                    $state.go('user-exercise-set');
                });
            }]
        })
        .state('user-exercise-set.edit', {
            parent: 'user-exercise-set',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-set/user-exercise-set-dialog.html',
                    controller: 'UserExerciseSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExerciseSet', function(UserExerciseSet) {
                            return UserExerciseSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise-set', null, { reload: 'user-exercise-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise-set.delete', {
            parent: 'user-exercise-set',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-set/user-exercise-set-delete-dialog.html',
                    controller: 'UserExerciseSetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserExerciseSet', function(UserExerciseSet) {
                            return UserExerciseSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise-set', null, { reload: 'user-exercise-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
