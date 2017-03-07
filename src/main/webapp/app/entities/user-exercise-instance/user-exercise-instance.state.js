(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-exercise-instance', {
            parent: 'entity',
            url: '/user-exercise-instance?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userExerciseInstance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise-instance/user-exercise-instances.html',
                    controller: 'UserExerciseInstanceController',
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
                    $translatePartialLoader.addPart('userExerciseInstance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-exercise-instance-detail', {
            parent: 'user-exercise-instance',
            url: '/user-exercise-instance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.userExerciseInstance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-exercise-instance/user-exercise-instance-detail.html',
                    controller: 'UserExerciseInstanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userExerciseInstance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserExerciseInstance', function($stateParams, UserExerciseInstance) {
                    return UserExerciseInstance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-exercise-instance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-exercise-instance-detail.edit', {
            parent: 'user-exercise-instance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance/user-exercise-instance-dialog.html',
                    controller: 'UserExerciseInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExerciseInstance', function(UserExerciseInstance) {
                            return UserExerciseInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise-instance.new', {
            parent: 'user-exercise-instance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance/user-exercise-instance-dialog.html',
                    controller: 'UserExerciseInstanceDialogController',
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
                    $state.go('user-exercise-instance', null, { reload: 'user-exercise-instance' });
                }, function() {
                    $state.go('user-exercise-instance');
                });
            }]
        })
        .state('user-exercise-instance.edit', {
            parent: 'user-exercise-instance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance/user-exercise-instance-dialog.html',
                    controller: 'UserExerciseInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExerciseInstance', function(UserExerciseInstance) {
                            return UserExerciseInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise-instance', null, { reload: 'user-exercise-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-exercise-instance.delete', {
            parent: 'user-exercise-instance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-exercise-instance/user-exercise-instance-delete-dialog.html',
                    controller: 'UserExerciseInstanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserExerciseInstance', function(UserExerciseInstance) {
                            return UserExerciseInstance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-exercise-instance', null, { reload: 'user-exercise-instance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
