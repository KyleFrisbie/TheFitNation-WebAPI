'use strict';

describe('Controller Tests', function() {

    describe('WorkoutLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutLog, MockWorkoutInstance, MockWorkoutTemplate, MockUserDemographic;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutLog': MockWorkoutLog,
                'WorkoutInstance': MockWorkoutInstance,
                'WorkoutTemplate': MockWorkoutTemplate,
                'UserDemographic': MockUserDemographic
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
