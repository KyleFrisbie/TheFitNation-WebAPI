'use strict';

describe('Controller Tests', function() {

    describe('WorkoutTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutTemplate, MockWorkoutInstance, MockWorkoutLog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutTemplate': MockWorkoutTemplate,
                'WorkoutInstance': MockWorkoutInstance,
                'WorkoutLog': MockWorkoutLog
            };
            createController = function() {
                $injector.get('$controller')("WorkoutTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:workoutTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
