(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exercise-set', {
            parent: 'entity',
            url: '/exercise-set',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExerciseSets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-set/exercise-sets.html',
                    controller: 'ExerciseSetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('exercise-set-detail', {
            parent: 'entity',
            url: '/exercise-set/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExerciseSet'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-set/exercise-set-detail.html',
                    controller: 'ExerciseSetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExerciseSet', function($stateParams, ExerciseSet) {
                    return ExerciseSet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'exercise-set',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('exercise-set-detail.edit', {
            parent: 'exercise-set-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-set/exercise-set-dialog.html',
                    controller: 'ExerciseSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseSet', function(ExerciseSet) {
                            return ExerciseSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-set.new', {
            parent: 'exercise-set',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-set/exercise-set-dialog.html',
                    controller: 'ExerciseSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                exercise_set_number: null,
                                reps: null,
                                weight: null,
                                rest: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exercise-set', null, { reload: 'exercise-set' });
                }, function() {
                    $state.go('exercise-set');
                });
            }]
        })
        .state('exercise-set.edit', {
            parent: 'exercise-set',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-set/exercise-set-dialog.html',
                    controller: 'ExerciseSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseSet', function(ExerciseSet) {
                            return ExerciseSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-set', null, { reload: 'exercise-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-set.delete', {
            parent: 'exercise-set',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-set/exercise-set-delete-dialog.html',
                    controller: 'ExerciseSetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExerciseSet', function(ExerciseSet) {
                            return ExerciseSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-set', null, { reload: 'exercise-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
