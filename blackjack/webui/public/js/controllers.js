/*global define */

'use strict';

define(function () {

    /* Controllers */

    var controllers = {};

    controllers.HomeController = function ($scope, $location, gameService) {
        $scope.newGame = function () {
            gameService.newGame()
            $location.path("/game");
        }
    }
    controllers.HomeController.$inject = ["$scope", "$location", "gameService"];

    controllers.GameController = function ($scope, $location, gameService) {
        $scope.service = gameService;
        $scope.newRound = function () {
            gameService.newRound()
            $location.path("/round");
        }
    }
    controllers.GameController.$inject = ["$scope", "$location", "gameService"];

    controllers.RoundController = function ($scope, gameService) {
        $scope.service = gameService
    }
    controllers.RoundController.$inject = ["$scope", "gameService"];

    return controllers;

});