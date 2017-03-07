(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exercise-instance-set', {
            parent: 'entity',
            url: '/exercise-instance-set?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.exerciseInstanceSet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-instance-set/exercise-instance-sets.html',
                    controller: 'ExerciseInstanceSetController',
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
                    $translatePartialLoader.addPart('exerciseInstanceSet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('exercise-instance-set-detail', {
            parent: 'exercise-instance-set',
            url: '/exercise-instance-set/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.exerciseInstanceSet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-instance-set/exercise-instance-set-detail.html',
                    controller: 'ExerciseInstanceSetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('exerciseInstanceSet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExerciseInstanceSet', function($stateParams, ExerciseInstanceSet) {
                    return ExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'exercise-instance-set',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('exercise-instance-set-detail.edit', {
            parent: 'exercise-instance-set-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance-set/exercise-instance-set-dialog.html',
                    controller: 'ExerciseInstanceSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseInstanceSet', function(ExerciseInstanceSet) {
                            return ExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-instance-set.new', {
            parent: 'exercise-instance-set',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance-set/exercise-instance-set-dialog.html',
                    controller: 'ExerciseInstanceSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderNumber: null,
                                reqQuantity: null,
                                effortQuantity: null,
                                restTime: null,
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exercise-instance-set', null, { reload: 'exercise-instance-set' });
                }, function() {
                    $state.go('exercise-instance-set');
                });
            }]
        })
        .state('exercise-instance-set.edit', {
            parent: 'exercise-instance-set',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance-set/exercise-instance-set-dialog.html',
                    controller: 'ExerciseInstanceSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseInstanceSet', function(ExerciseInstanceSet) {
                            return ExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-instance-set', null, { reload: 'exercise-instance-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-instance-set.delete', {
            parent: 'exercise-instance-set',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance-set/exercise-instance-set-delete-dialog.html',
                    controller: 'ExerciseInstanceSetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExerciseInstanceSet', function(ExerciseInstanceSet) {
                            return ExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-instance-set', null, { reload: 'exercise-instance-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
