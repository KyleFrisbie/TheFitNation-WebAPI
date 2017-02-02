'use strict';

describe('Controller Tests', function() {

    describe('UserDemographic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserDemographic, MockGym, MockUserWeight, MockWorkoutLog, MockWorkoutTemplate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockGym = jasmine.createSpy('MockGym');
            MockUserWeight = jasmine.createSpy('MockUserWeight');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserDemographic': MockUserDemographic,
                'Gym': MockGym,
                'UserWeight': MockUserWeight,
                'WorkoutLog': MockWorkoutLog,
                'WorkoutTemplate': MockWorkoutTemplate
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
