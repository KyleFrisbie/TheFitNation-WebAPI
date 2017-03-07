'use strict';

describe('Controller Tests', function() {

    describe('ExerciseInstanceSet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExerciseInstanceSet, MockExerciseInstance, MockUserExerciseInstanceSet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExerciseInstanceSet = jasmine.createSpy('MockExerciseInstanceSet');
            MockExerciseInstance = jasmine.createSpy('MockExerciseInstance');
            MockUserExerciseInstanceSet = jasmine.createSpy('MockUserExerciseInstanceSet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ExerciseInstanceSet': MockExerciseInstanceSet,
                'ExerciseInstance': MockExerciseInstance,
                'UserExerciseInstanceSet': MockUserExerciseInstanceSet
            };
            createController = function() {
                $injector.get('$controller')("ExerciseInstanceSetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:exerciseInstanceSetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
