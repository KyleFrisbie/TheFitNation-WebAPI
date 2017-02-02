'use strict';

describe('Controller Tests', function() {

    describe('UserWorkoutInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserWorkoutInstance, MockUserWorkoutTemplate, MockWorkoutInstance, MockUserExercise;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserWorkoutInstance = jasmine.createSpy('MockUserWorkoutInstance');
            MockUserWorkoutTemplate = jasmine.createSpy('MockUserWorkoutTemplate');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockUserExercise = jasmine.createSpy('MockUserExercise');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserWorkoutInstance': MockUserWorkoutInstance,
                'UserWorkoutTemplate': MockUserWorkoutTemplate,
                'WorkoutInstance': MockWorkoutInstance,
                'UserExercise': MockUserExercise
            };
            createController = function() {
                $injector.get('$controller')("UserWorkoutInstanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userWorkoutInstanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
