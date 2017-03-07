'use strict';

describe('Controller Tests', function() {

    describe('UserExerciseInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserExerciseInstance, MockUserWorkoutInstance, MockExerciseInstance, MockUserExerciseInstanceSet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserExerciseInstance = jasmine.createSpy('MockUserExerciseInstance');
            MockUserWorkoutInstance = jasmine.createSpy('MockUserWorkoutInstance');
            MockExerciseInstance = jasmine.createSpy('MockExerciseInstance');
            MockUserExerciseInstanceSet = jasmine.createSpy('MockUserExerciseInstanceSet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserExerciseInstance': MockUserExerciseInstance,
                'UserWorkoutInstance': MockUserWorkoutInstance,
                'ExerciseInstance': MockExerciseInstance,
                'UserExerciseInstanceSet': MockUserExerciseInstanceSet
            };
            createController = function() {
                $injector.get('$controller')("UserExerciseInstanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userExerciseInstanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
