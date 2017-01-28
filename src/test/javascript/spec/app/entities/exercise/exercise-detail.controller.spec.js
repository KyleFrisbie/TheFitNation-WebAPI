'use strict';

describe('Controller Tests', function() {

    describe('Exercise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExercise, MockMuscle, MockExerciseSet, MockWorkoutInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExercise = jasmine.createSpy('MockExercise');
            MockMuscle = jasmine.createSpy('MockMuscle');
            MockExerciseSet = jasmine.createSpy('MockExerciseSet');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Exercise': MockExercise,
                'Muscle': MockMuscle,
                'ExerciseSet': MockExerciseSet,
                'WorkoutInstance': MockWorkoutInstance
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
