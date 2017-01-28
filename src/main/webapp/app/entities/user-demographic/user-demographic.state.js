(function() {
    'use strict';

    angular
        .module('theFitNationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-demographic', {
            parent: 'entity',
            url: '/user-demographic',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserDemographics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-demographic/user-demographics.html',
                    controller: 'UserDemographicController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-demographic-detail', {
            parent: 'entity',
            url: '/user-demographic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserDemographic'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-demographic/user-demographic-detail.html',
                    controller: 'UserDemographicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserDemographic', function($stateParams, UserDemographic) {
                    return UserDemographic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-demographic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-demographic-detail.edit', {
            parent: 'user-demographic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-dialog.html',
                    controller: 'UserDemographicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDemographic', function(UserDemographic) {
                            return UserDemographic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-demographic.new', {
            parent: 'user-demographic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-dialog.html',
                    controller: 'UserDemographicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                first_name: null,
                                last_name: null,
                                gender: null,
                                dob: null,
                                height: null,
                                skill_level: null,
                                unit_of_measure: null,
                                last_login: null,
                                join_date: null,
                                is_active: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-demographic', null, { reload: 'user-demographic' });
                }, function() {
                    $state.go('user-demographic');
                });
            }]
        })
        .state('user-demographic.edit', {
            parent: 'user-demographic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-dialog.html',
                    controller: 'UserDemographicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDemographic', function(UserDemographic) {
                            return UserDemographic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-demographic', null, { reload: 'user-demographic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-demographic.delete', {
            parent: 'user-demographic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-demographic/user-demographic-delete-dialog.html',
                    controller: 'UserDemographicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserDemographic', function(UserDemographic) {
                            return UserDemographic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-demographic', null, { reload: 'user-demographic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
