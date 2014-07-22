joggingApp.controller('LoginController', function ($scope, $routeParams, $location, $http, $session) {
    $scope.credentials = {};

    if ($session.token) {
        $location.path('/joggingList');
        return;
    }

    if ($routeParams.registration) {
        $scope.registered = true;
    }

    $scope.login = function () {
        $scope.credentials.$error = {};
        var loginPromise = $http.post("/login", $scope.credentials);

        loginPromise.success(function (data, status, headers, config) {
            if (data.token) {
                $location.path('/joggingList');
                $session.create(data.token, $scope.credentials.email);
            }
            else {
                $scope.credentials.$error.generalLogin = true;
            }
        });
        loginPromise.error(function (data, status, headers, config) {
            if (status == 403) {
                $scope.credentials.$error.invalidCredentials = true;
            }
            else if (status == 401) {
                $session.destroy();
                $location.path("/");
            }
            else {
                $scope.credentials.$error.generalLogin = true;
            }
        });
    }

    $scope.register = function () {
        $location.path('/register');
    }
});