'use strict';

describe('Controller Tests', function() {

    describe('UserWorkoutTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserWorkoutTemplate, MockUserDemographic, MockWorkoutTemplate, MockUserWorkoutInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserWorkoutTemplate = jasmine.createSpy('MockUserWorkoutTemplate');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockUserWorkoutInstance = jasmine.createSpy('MockUserWorkoutInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserWorkoutTemplate': MockUserWorkoutTemplate,
                'UserDemographic': MockUserDemographic,
                'WorkoutTemplate': MockWorkoutTemplate,
                'UserWorkoutInstance': MockUserWorkoutInstance
            };
            createController = function() {
                $injector.get('$controller')("UserWorkoutTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userWorkoutTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
