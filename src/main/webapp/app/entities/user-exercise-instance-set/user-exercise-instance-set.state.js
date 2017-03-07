(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-exercise-instance-set', {
            parent: 'entity',
            url: '/user-exercise-instance-set?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userExerciseInstanceSet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise-instance-set/user-exercise-instance-sets.html',
                    controller: 'UserExerciseInstanceSetController',
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
                    $translatePartialLoader.addPart('userExerciseInstanceSet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-exercise-instance-set-detail', {
            parent: 'user-exercise-instance-set',
            url: '/user-exercise-instance-set/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userExerciseInstanceSet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise-instance-set/user-exercise-instance-set-detail.html',
                    controller: 'UserExerciseInstanceSetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userExerciseInstanceSet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserExerciseInstanceSet', function($stateParams, UserExerciseInstanceSet) {
                    return UserExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-exercise-instance-set',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-exercise-instance-set-detail.edit', {
            parent: 'user-exercise-instance-set-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance-set/user-exercise-instance-set-dialog.html',
                    controller: 'UserExerciseInstanceSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExerciseInstanceSet', function(UserExerciseInstanceSet) {
                            return UserExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise-instance-set.new', {
            parent: 'user-exercise-instance-set',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance-set/user-exercise-instance-set-dialog.html',
                    controller: 'UserExerciseInstanceSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderNumber: null,
                                repQuantity: null,
                                effortQuantity: null,
                                restTime: null,
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-exercise-instance-set', null, { reload: 'user-exercise-instance-set' });
                }, function() {
                    $state.go('user-exercise-instance-set');
                });
            }]
        })
        .state('user-exercise-instance-set.edit', {
            parent: 'user-exercise-instance-set',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance-set/user-exercise-instance-set-dialog.html',
                    controller: 'UserExerciseInstanceSetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExerciseInstanceSet', function(UserExerciseInstanceSet) {
                            return UserExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise-instance-set', null, { reload: 'user-exercise-instance-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise-instance-set.delete', {
            parent: 'user-exercise-instance-set',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance-set/user-exercise-instance-set-delete-dialog.html',
                    controller: 'UserExerciseInstanceSetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserExerciseInstanceSet', function(UserExerciseInstanceSet) {
                            return UserExerciseInstanceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise-instance-set', null, { reload: 'user-exercise-instance-set' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
