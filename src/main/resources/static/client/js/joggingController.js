joggingApp.controller('JoggingController',['$scope', '$routeParams', '$location', '$http', '$session', '$filter',
    function ($scope, $routeParams, $location, $http, $session, $filter) {

    if (!$session.token) {
        $location.path('/');
        return;
    }

    $scope.record = {};
    $scope.dateFilter = {};
    $scope.record.date = new Date();
    $scope.userName = $session.userName;

    $scope.open = function ($event, id) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope[id] = false;
        $scope.$apply();
        $scope[id] = true;
    };

    $scope.isOpen = function (id) {
        return $scope[id];
    };

    $scope.jogRecords = [];

    $scope.refreshRecords = function () {
        $scope.jogRecords.$error = [];
        var jogDataPromise = $http.get("/jogRecord", {
            "headers": {"Auth-Token": $session.token}
        });

        jogDataPromise.success(function (data, status, headers, config) {
            $scope.jogRecords = data;
        });
        jogDataPromise.error(function (data, status, headers, config) {
            if (status == 401) {
                $session.destroy();
                $location.path("/");
            }
            else {
                $scope.jogRecords.$error.generalError = true;
            }
        });
    }

    $scope.refreshRecords();

    $scope.addRecord = function () {
        $scope.record.$error = {};
        var addRecordResponse = $http.post("/jogRecord", $scope.record, {
            "headers": {"Auth-Token": $session.token}
        });

        addRecordResponse.success(function (data, status, headers, config) {
            $scope.refreshRecords();
        });
        addRecordResponse.error(function (data, status, headers, config) {
            if (status == 400) {
                $scope.record.$error.validationError = true;
                $scope.record.$error.validationErrors = [];
                data.errors.forEach(function (error) {
                    $scope.record.$error.validationErrors.push(error.field + ' ' + error.error);
                });
            }
            else if (status == 401) {
                $session.destroy();
                $location.path("/");
            }
            else {
                $scope.record.$error.genericError = true;
            }
        });
    }

    $scope.removeRecord = function (id) {
        $scope.$removalError = {};
        var addRecordResponse = $http.delete("/jogRecord/" + id, {
            "headers": {"Auth-Token": $session.token}
        });

        addRecordResponse.success(function (data, status, headers, config) {
            $scope.refreshRecords();
        });
        addRecordResponse.error(function (data, status, headers, config) {
            if (status == 401) {
                $session.destroy();
                $location.path("/");
            }
            else {
                $scope.$removalError.removalError = true;
            }
        });
    }

    $scope.reportData = []

    $scope.refreshReport = function () {
        $scope.reportData.$error = [];
        var jogDataPromise = $http.get("/jogRecord/report", {
            "headers": {"Auth-Token": $session.token}
        });

        jogDataPromise.success(function (data, status, headers, config) {
            $scope.reportData = data;
        });
        jogDataPromise.error(function (data, status, headers, config) {
            if (status == 401) {
                $session.destroy();
                $location.path("/");
            }
            else {
                $scope.reportData.$error.generalError = true;
            }
        });
    }

    $scope.refreshReport();

    $scope.filterByDates = function (record) {

        var startDate = $filter('date')($scope.dateFilter.startDate, 'yyyy/MM/dd');
        var endDate = $filter('date')($scope.dateFilter.endDate, 'yyyy/MM/dd');

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
}]);

