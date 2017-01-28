'use strict';

describe('Controller Tests', function() {

    describe('UserDemographic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserDemographic, MockUserWeight, MockGym, MockWorkoutInstance, MockWorkoutTemplate, MockWorkoutLog, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockUserWeight = jasmine.createSpy('MockUserWeight');
            MockGym = jasmine.createSpy('MockGym');
            MockWorkoutInstance = jasmine.createSpy('MockWorkoutInstance');
            MockWorkoutTemplate = jasmine.createSpy('MockWorkoutTemplate');
            MockWorkoutLog = jasmine.createSpy('MockWorkoutLog');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserDemographic': MockUserDemographic,
                'UserWeight': MockUserWeight,
                'Gym': MockGym,
                'WorkoutInstance': MockWorkoutInstance,
                'WorkoutTemplate': MockWorkoutTemplate,
                'WorkoutLog': MockWorkoutLog,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("UserDemographicDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:userDemographicUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
