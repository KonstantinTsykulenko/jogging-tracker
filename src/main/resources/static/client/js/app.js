var joggingApp = angular.module('joggingApp', ['ngRoute', 'ngMessages', 'ui.bootstrap'])
    .controller('LoginController', function ($scope, $routeParams, $location, $http, $session) {
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
                    $session.create(data.token, $scope.credentials.email)
                }
                else {
                    $scope.credentials.$error.generalLogin = true;
                }
            });
            loginPromise.error(function (data, status, headers, config) {
                if (status == 403) {
                    $scope.credentials.$error.invalidCredentials = true;
                }
                else {
                    $scope.credentials.$error.generalLogin = true;
                }
            });
        }

        $scope.register = function () {
            $location.path('/register');
        }
    })

    .controller('RegistrationController', function ($scope, $routeParams, $location, $http) {
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
    })

    .controller('JoggingController', function ($scope, $routeParams, $location, $http, $session, $filter) {
        if (!$session.token) {
            $location.path('/');
            return;
        }

        $scope.record = {};
        $scope.record.date = new Date();
        $scope.userName = $session.userName;
        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1,
            showWeeks: true
        }

        $scope.open = function ($event, id) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope[id] = true;
        };

        $scope.jogRecords = [];

        $scope.refresh = function () {
            $scope.jogRecords.$error = [];
            var jogDataPromise = $http.get("/jogRecord", {
                "headers": {"Auth-Token": $session.token}
            });

            jogDataPromise.success(function (data, status, headers, config) {
                $scope.jogRecords = data;
            });
            jogDataPromise.error(function (data, status, headers, config) {
                $scope.jogRecords.$error.generalError = true;
            });
        }

        $scope.refresh();

        $scope.addRecord = function () {
            $scope.record.$error = {};
            var addRecordResponse = $http.post("/jogRecord", $scope.record, {
                "headers": {"Auth-Token": $session.token}
            });

            addRecordResponse.success(function (data, status, headers, config) {
                $scope.refresh();
            });
            addRecordResponse.error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.record.$error.validationError = true;
                    $scope.record.$error.validationErrors = [];
                    data.errors.forEach(function (error) {
                        $scope.record.$error.validationErrors.push(error.field + ' ' + error.error);
                    });
                }
                else {
                    $scope.record.$error.genericError = true;
                }
            });
        }

        $scope.removeRecord = function (id) {
            $scope.$removalError = {};
            var addRecordResponse = $http.delete("/jogRecord" + id, {
                "headers": {"Auth-Token": $session.token}
            });

            addRecordResponse.success(function (data, status, headers, config) {
                $scope.refresh();
            });
            addRecordResponse.error(function (data, status, headers, config) {
                $scope.$removalError.removalError = true;
            });
        }

        $scope.filterByDates = function (record) {

            var startDate = $filter('date')($scope.startDate, 'yyyy/MM/dd');
            var endDate = $filter('date')($scope.endDate, 'yyyy/MM/dd');

            if (record.date < startDate) {
                return false;
            }

            if (record.date > endDate) {
                return false;
            }

            return true;
        };

        $scope.logout = function () {
            $session.destroy();
            $location.path('/');
        }

        $scope.showReport = function () {
            $location.path('/report');
        }
    })

    .controller('ReportController', function ($scope, $routeParams, $location, $http, $session) {
        if (!$session.token) {
            $location.path('/');
            return;
        }

        $scope.reportData = []

        $scope.refresh = function () {
            $scope.reportData.$error = [];
            var jogDataPromise = $http.get("/jogRecord/report", {
                "headers": {"Auth-Token": $session.token}
            });

            jogDataPromise.success(function (data, status, headers, config) {
                $scope.reportData = data;
            });
            jogDataPromise.error(function (data, status, headers, config) {
                $scope.reportData.$error.generalError = true;
            });
        }

        $scope.refresh();
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
            .when('/register/', {
                templateUrl: 'partials/register.html',
                controller: 'RegistrationController'
            })
            .when('/report/', {
                templateUrl: 'partials/report.html',
                controller: 'ReportController'
            })
            .otherwise({
                redirectTo: '/'
            })
    })

    .config(function (datepickerConfig) {
        datepickerConfig.showWeeks = true;
        datepickerConfig.startingDay = 1
    })

    .service('$session', function () {
        this.create = function (token, userName) {
            this.token = token;
            this.userName = userName;
        }
        this.destroy = function () {
            this.token = null;
            this.userName = null;
        }
        return this;
    })