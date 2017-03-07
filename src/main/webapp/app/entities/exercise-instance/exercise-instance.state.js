(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exercise-instance', {
            parent: 'entity',
            url: '/exercise-instance?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.exerciseInstance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-instance/exercise-instances.html',
                    controller: 'ExerciseInstanceController',
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
                    $translatePartialLoader.addPart('exerciseInstance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('exercise-instance-detail', {
            parent: 'exercise-instance',
            url: '/exercise-instance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.exerciseInstance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-instance/exercise-instance-detail.html',
                    controller: 'ExerciseInstanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('exerciseInstance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExerciseInstance', function($stateParams, ExerciseInstance) {
                    return ExerciseInstance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'exercise-instance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('exercise-instance-detail.edit', {
            parent: 'exercise-instance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance/exercise-instance-dialog.html',
                    controller: 'ExerciseInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseInstance', function(ExerciseInstance) {
                            return ExerciseInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-instance.new', {
            parent: 'exercise-instance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance/exercise-instance-dialog.html',
                    controller: 'ExerciseInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exercise-instance', null, { reload: 'exercise-instance' });
                }, function() {
                    $state.go('exercise-instance');
                });
            }]
        })
        .state('exercise-instance.edit', {
            parent: 'exercise-instance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance/exercise-instance-dialog.html',
                    controller: 'ExerciseInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseInstance', function(ExerciseInstance) {
                            return ExerciseInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-instance', null, { reload: 'exercise-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-instance.delete', {
            parent: 'exercise-instance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-instance/exercise-instance-delete-dialog.html',
                    controller: 'ExerciseInstanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExerciseInstance', function(ExerciseInstance) {
                            return ExerciseInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-instance', null, { reload: 'exercise-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
