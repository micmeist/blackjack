/*global define */

'use strict';

define(function () {

    /* Controllers */

    var controllers = {};

    controllers.HomeController = function ($scope, $location) {
        $scope.newGame = function(){
            $location.path("/game");
        }
    }
    controllers.HomeController.$inject = ["$scope", "$location"];

    controllers.GameController = function ($scope, $http) {
        $http.get("newgame").then(function (response) {
            $scope.game = response.data;
        });
    }
    controllers.GameController.$inject = ["$scope", "$http"];

    controllers.RoundController = function () {
    }
    controllers.RoundController.$inject = [];

    return controllers;

});