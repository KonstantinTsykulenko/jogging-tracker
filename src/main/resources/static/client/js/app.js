var joggingApp = angular.module('joggingApp', ['ngRoute'])
    .controller('LoginController', function ($scope, $routeParams, $location, $http) {
        $scope.credentials = {}
        $scope.login = function (roomId) {
            $http.post("http://localhost:9090/login", $scope.credentials)
            $location.path('/joggingList')
            //$scope.$apply()
        }
    })

    .controller('JoggingController', function ($scope, $routeParams, $location) {
    })

    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'partials/login.html',
                controller: 'LoginController'
            })
            .when('/joggingList/', {
                templateUrl: 'partials/recordList.html',
                controller: 'JoggingController'
            })
            .otherwise({
                redirectTo: '/'
            })
    })