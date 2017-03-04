'use strict';

describe('Controller Tests', function() {

    describe('Exercise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExercise, MockWorkoutInstance, MockMuscle, MockExerciseSet, MockUserExercise;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExercise = jasmine.createSpy('MockExercise');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockMuscle = jasmine.createSpy('MockMuscle');
            MockExerciseSet = jasmine.createSpy('MockExerciseSet');
            MockUserExercise = jasmine.createSpy('MockUserExercise');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Exercise': MockExercise,
                'WorkoutInstance': MockWorkoutInstance,
                'Muscle': MockMuscle,
                'ExerciseSet': MockExerciseSet,
                'UserExercise': MockUserExercise
            };
            createController = function() {
                $injector.get('$controller')("ExerciseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:exerciseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
