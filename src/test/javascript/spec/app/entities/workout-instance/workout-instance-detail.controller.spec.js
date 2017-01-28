'use strict';

describe('Controller Tests', function() {

    describe('WorkoutInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutInstance, MockUserDemographic, MockWorkoutTemplate, MockMuscle, MockExercise, MockWorkoutLog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockMuscle = jasmine.createSpy('MockMuscle');
            MockExercise = jasmine.createSpy('MockExercise');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutInstance': MockWorkoutInstance,
                'UserDemographic': MockUserDemographic,
                'WorkoutTemplate': MockWorkoutTemplate,
                'Muscle': MockMuscle,
                'Exercise': MockExercise,
                'WorkoutLog': MockWorkoutLog
            };
            createController = function() {
                $injector.get('$controller')("WorkoutInstanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:workoutInstanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
