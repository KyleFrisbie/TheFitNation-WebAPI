'use strict';

describe('Controller Tests', function() {

    describe('WorkoutLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutLog, MockUserDemographic, MockUserWorkoutTemplate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockUserWorkoutTemplate = jasmine.createSpy('MockUserWorkoutTemplate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutLog': MockWorkoutLog,
                'UserDemographic': MockUserDemographic,
                'UserWorkoutTemplate': MockUserWorkoutTemplate
            };
            createController = function() {
                $injector.get('$controller')("WorkoutLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:workoutLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
