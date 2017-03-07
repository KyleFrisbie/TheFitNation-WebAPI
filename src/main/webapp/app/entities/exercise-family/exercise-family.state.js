(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exercise-family', {
            parent: 'entity',
            url: '/exercise-family?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.exerciseFamily.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-family/exercise-families.html',
                    controller: 'ExerciseFamilyController',
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
                    $translatePartialLoader.addPart('exerciseFamily');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('exercise-family-detail', {
            parent: 'exercise-family',
            url: '/exercise-family/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.exerciseFamily.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-family/exercise-family-detail.html',
                    controller: 'ExerciseFamilyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('exerciseFamily');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExerciseFamily', function($stateParams, ExerciseFamily) {
                    return ExerciseFamily.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'exercise-family',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('exercise-family-detail.edit', {
            parent: 'exercise-family-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-family/exercise-family-dialog.html',
                    controller: 'ExerciseFamilyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseFamily', function(ExerciseFamily) {
                            return ExerciseFamily.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-family.new', {
            parent: 'exercise-family',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-family/exercise-family-dialog.html',
                    controller: 'ExerciseFamilyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exercise-family', null, { reload: 'exercise-family' });
                }, function() {
                    $state.go('exercise-family');
                });
            }]
        })
        .state('exercise-family.edit', {
            parent: 'exercise-family',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-family/exercise-family-dialog.html',
                    controller: 'ExerciseFamilyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseFamily', function(ExerciseFamily) {
                            return ExerciseFamily.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-family', null, { reload: 'exercise-family' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-family.delete', {
            parent: 'exercise-family',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-family/exercise-family-delete-dialog.html',
                    controller: 'ExerciseFamilyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExerciseFamily', function(ExerciseFamily) {
                            return ExerciseFamily.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-family', null, { reload: 'exercise-family' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
