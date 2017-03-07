'use strict';

describe('Controller Tests', function() {

    describe('UserDemographic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserDemographic, MockUser, MockGym, MockUserWeight, MockWorkoutTemplate, MockUserWorkoutTemplate, MockSkillLevel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockUser = jasmine.createSpy('MockUser');
            MockGym = jasmine.createSpy('MockGym');
            MockUserWeight = jasmine.createSpy('MockUserWeight');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockUserWorkoutTemplate = jasmine.createSpy('MockUserWorkoutTemplate');
            MockSkillLevel = jasmine.createSpy('MockSkillLevel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserDemographic': MockUserDemographic,
                'User': MockUser,
                'Gym': MockGym,
                'UserWeight': MockUserWeight,
                'WorkoutTemplate': MockWorkoutTemplate,
                'UserWorkoutTemplate': MockUserWorkoutTemplate,
                'SkillLevel': MockSkillLevel
            };
            createController = function() {
                $injector.get('$controller')("UserDemographicDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userDemographicUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
