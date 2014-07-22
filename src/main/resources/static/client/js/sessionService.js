joggingApp.service('$session', function () {
    this.create = function (token, userName) {
        this.token = token;
        this.userName = userName;
    }
    this.destroy = function () {
        this.token = null;
        this.userName = null;
    }
    return this;
});