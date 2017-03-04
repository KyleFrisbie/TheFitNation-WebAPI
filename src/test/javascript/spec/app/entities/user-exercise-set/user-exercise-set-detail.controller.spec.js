'use strict';

describe('Controller Tests', function() {

    describe('UserExerciseSet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserExerciseSet, MockUserExercise, MockExerciseSet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserExerciseSet = jasmine.createSpy('MockUserExerciseSet');
            MockUserExercise = jasmine.createSpy('MockUserExercise');
            MockExerciseSet = jasmine.createSpy('MockExerciseSet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserExerciseSet': MockUserExerciseSet,
                'UserExercise': MockUserExercise,
                'ExerciseSet': MockExerciseSet
            };
            createController = function() {
                $injector.get('$controller')("UserExerciseSetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userExerciseSetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
