var joggingApp = angular.module('joggingApp', ['ngRoute', 'ngMessages', 'ui.bootstrap']);

joggingApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'partials/login.html',
            controller: 'LoginController'
        })
        .when('/joggingList/', {
            templateUrl: 'partials/tabView.html',
            controller: 'JoggingController'
        })
        .when('/register/', {
            templateUrl: 'partials/register.html',
            controller: 'RegistrationController'
        })
        .otherwise({
            redirectTo: '/'
        })
}]);



