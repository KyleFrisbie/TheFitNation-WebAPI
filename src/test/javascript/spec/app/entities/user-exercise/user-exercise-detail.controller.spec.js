'use strict';

describe('Controller Tests', function() {

    describe('UserExercise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserExercise, MockUserWorkoutInstance, MockExercise, MockUserExerciseSet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserExercise = jasmine.createSpy('MockUserExercise');
            MockUserWorkoutInstance = jasmine.createSpy('MockUserWorkoutInstance');
            MockExercise = jasmine.createSpy('MockExercise');
            MockUserExerciseSet = jasmine.createSpy('MockUserExerciseSet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserExercise': MockUserExercise,
                'UserWorkoutInstance': MockUserWorkoutInstance,
                'Exercise': MockExercise,
                'UserExerciseSet': MockUserExerciseSet
            };
            createController = function() {
                $injector.get('$controller')("UserExerciseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userExerciseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
