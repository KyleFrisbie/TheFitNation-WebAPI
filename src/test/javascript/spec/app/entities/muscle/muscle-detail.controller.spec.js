'use strict';

describe('Controller Tests', function() {

    describe('Muscle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMuscle, MockExercise, MockBodyPart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMuscle = jasmine.createSpy('MockMuscle');
            MockExercise = jasmine.createSpy('MockExercise');
            MockBodyPart = jasmine.createSpy('MockBodyPart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Muscle': MockMuscle,
                'Exercise': MockExercise,
                'BodyPart': MockBodyPart
            };
            createController = function() {
                $injector.get('$controller')("MuscleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:muscleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
