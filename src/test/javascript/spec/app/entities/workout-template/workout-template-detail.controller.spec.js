'use strict';

describe('Controller Tests', function() {

    describe('WorkoutTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkoutTemplate, MockUserDemographic, MockWorkoutInstance, MockUserWorkoutTemplate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockUserWorkoutTemplate = jasmine.createSpy('MockUserWorkoutTemplate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkoutTemplate': MockWorkoutTemplate,
                'UserDemographic': MockUserDemographic,
                'WorkoutInstance': MockWorkoutInstance,
                'UserWorkoutTemplate': MockUserWorkoutTemplate
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
