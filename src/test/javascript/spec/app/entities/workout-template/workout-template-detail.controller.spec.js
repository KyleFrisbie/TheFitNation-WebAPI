'use strict';

describe('Controller Tests', function() {

    describe('WorkoutTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutTemplate, MockUserDemographic, MockWorkoutLog, MockWorkoutInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutTemplate': MockWorkoutTemplate,
                'UserDemographic': MockUserDemographic,
                'WorkoutLog': MockWorkoutLog,
                'WorkoutInstance': MockWorkoutInstance
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
