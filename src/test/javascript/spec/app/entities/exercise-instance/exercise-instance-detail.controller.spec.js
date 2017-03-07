'use strict';

describe('Controller Tests', function() {

    describe('ExerciseInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExerciseInstance, MockWorkoutInstance, MockUserExerciseInstance, MockExerciseInstanceSet, MockExercise, MockUnit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExerciseInstance = jasmine.createSpy('MockExerciseInstance');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockUserExerciseInstance = jasmine.createSpy('MockUserExerciseInstance');
            MockExerciseInstanceSet = jasmine.createSpy('MockExerciseInstanceSet');
            MockExercise = jasmine.createSpy('MockExercise');
            MockUnit = jasmine.createSpy('MockUnit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ExerciseInstance': MockExerciseInstance,
                'WorkoutInstance': MockWorkoutInstance,
                'UserExerciseInstance': MockUserExerciseInstance,
                'ExerciseInstanceSet': MockExerciseInstanceSet,
                'Exercise': MockExercise,
                'Unit': MockUnit
            };
            createController = function() {
                $injector.get('$controller')("ExerciseInstanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:exerciseInstanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
