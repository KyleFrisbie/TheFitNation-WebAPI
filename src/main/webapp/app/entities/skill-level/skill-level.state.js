(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('skill-level', {
            parent: 'entity',
            url: '/skill-level?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.skillLevel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/skill-level/skill-levels.html',
                    controller: 'SkillLevelController',
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
                    $translatePartialLoader.addPart('skillLevel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('skill-level-detail', {
            parent: 'skill-level',
            url: '/skill-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'theFitNationApp.skillLevel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/skill-level/skill-level-detail.html',
                    controller: 'SkillLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('skillLevel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SkillLevel', function($stateParams, SkillLevel) {
                    return SkillLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'skill-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('skill-level-detail.edit', {
            parent: 'skill-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill-level/skill-level-dialog.html',
                    controller: 'SkillLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SkillLevel', function(SkillLevel) {
                            return SkillLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skill-level.new', {
            parent: 'skill-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill-level/skill-level-dialog.html',
                    controller: 'SkillLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                level: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('skill-level', null, { reload: 'skill-level' });
                }, function() {
                    $state.go('skill-level');
                });
            }]
        })
        .state('skill-level.edit', {
            parent: 'skill-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill-level/skill-level-dialog.html',
                    controller: 'SkillLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SkillLevel', function(SkillLevel) {
                            return SkillLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('skill-level', null, { reload: 'skill-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skill-level.delete', {
            parent: 'skill-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill-level/skill-level-delete-dialog.html',
                    controller: 'SkillLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SkillLevel', function(SkillLevel) {
                            return SkillLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('skill-level', null, { reload: 'skill-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
