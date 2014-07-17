var joggingApp = angular.module('joggingApp', ['ngRoute'])
    .controller('LoginController', function ($scope, $routeParams, $location, $http, $session) {
        $scope.credentials = {}
        $scope.login = function (roomId) {
            var loginPromise = $http.post("http://localhost:9090/login", $scope.credentials)

            loginPromise.success(function(data, status, headers, config) {
                $location.path('/joggingList')
                $session.create(data.token)
                alert(data.token);
            });
            loginPromise.error(function(data, status, headers, config) {
                alert("Login failed!");
            });
        }
    })

    .controller('JoggingController', function ($scope, $routeParams, $location, $http, $session) {
        $scope.jogRecords = []
        var jogDataPromise = $http.get("http://localhost:9090/jogRecord", {
            "headers": {"Auth-Token": $session.token}
        })

        jogDataPromise.success(function(data, status, headers, config) {
            $scope.jogRecords = data
        });
        jogDataPromise.error(function(data, status, headers, config) {
        });
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

    .service('$session', function () {
        this.create = function (token) {
            this.token = token;
        };
        this.destroy = function () {
            this.token = null;
        };
        return this;
    })