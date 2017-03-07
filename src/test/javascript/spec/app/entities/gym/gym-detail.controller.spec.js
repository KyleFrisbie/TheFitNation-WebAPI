'use strict';

describe('Controller Tests', function() {

    describe('Gym Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGym, MockUserDemographic, MockLocation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGym = jasmine.createSpy('MockGym');
            MockUserDemographic = jasmine.createSpy('MockUserDemographic');
            MockLocation = jasmine.createSpy('MockLocation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Gym': MockGym,
                'UserDemographic': MockUserDemographic,
                'Location': MockLocation
            };
            createController = function() {
                $injector.get('$controller')("GymDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'theFitNationApp:gymUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
