'use strict';

describe('Controller Tests', function() {

    describe('WorkoutInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutInstance, MockWorkoutTemplate, MockExercise, MockMuscle, MockUserWorkoutInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockExercise = jasmine.createSpy('MockExercise');
            MockMuscle = jasmine.createSpy('MockMuscle');
            MockUserWorkoutInstance = jasmine.createSpy('MockUserWorkoutInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutInstance': MockWorkoutInstance,
                'WorkoutTemplate': MockWorkoutTemplate,
                'Exercise': MockExercise,
                'Muscle': MockMuscle,
                'UserWorkoutInstance': MockUserWorkoutInstance
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
