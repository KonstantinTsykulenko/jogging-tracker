joggingApp.controller('RegistrationController', ['$scope', '$routeParams', '$location', '$http',
    function ($scope, $routeParams, $location, $http) {

    $scope.credentials = {};

    $scope.register = function () {
        if ($scope.registrationForm.$invalid) {
            return;
        }

        $scope.credentials.$error = {};
        var registerPromise = $http.post("/user", $scope.credentials);

        registerPromise.success(function (data, status, headers, config) {
            if (data.email) {
                $location.search('registration', 'true').path('/');
            }
            else {
                $scope.credentials.$error.registrationFailure = true;
            }
        });
        registerPromise.error(function (data, status, headers, config) {
            if (status == 409) {
                $scope.credentials.$error.userExists = true;
            }
            else if (status == 400 && data.errors) {
                data.errors.forEach(function (error) {
                    if (error.field == 'email') {
                        $scope.credentials.$error.invalidEmail = true;
                        $scope.credentials.$error.invalidEmailMessage = error.field + ' ' + error.error;
                    }
                    else if (error.field = 'password') {
                        $scope.credentials.$error.invalidPassword = true;
                        $scope.credentials.$error.invalidPasswordMessage = error.field + ' ' + error.error;
                    }
                })
            }
            else {
                $scope.credentials.$error.registrationFailure = true;
            }
        });
    }
}]);

