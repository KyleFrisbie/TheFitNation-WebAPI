'use strict';

describe('Controller Tests', function() {

    describe('WorkoutInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutInstance, MockWorkoutTemplate, MockUserWorkoutInstance, MockExerciseInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockUserWorkoutInstance = jasmine.createSpy('MockUserWorkoutInstance');
            MockExerciseInstance = jasmine.createSpy('MockExerciseInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutInstance': MockWorkoutInstance,
                'WorkoutTemplate': MockWorkoutTemplate,
                'UserWorkoutInstance': MockUserWorkoutInstance,
                'ExerciseInstance': MockExerciseInstance
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
