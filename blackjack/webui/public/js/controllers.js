/*global define */

'use strict';

define(function () {

    /* Controllers */

    var controllers = {};

    controllers.HomeController = function ($scope, $location, gameService) {
        $scope.newGame = function () {
            gameService.newGame();
            $location.path("/game");
        }
    };
    controllers.HomeController.$inject = ["$scope", "$location", "gameService"];

    controllers.GameController = function ($scope, $location, gameService) {
        gameService.getGamePlayers().then(function(response){
            $scope.gamePlayers = response.data
        }, function(errorResponse){
            $scope.errorMessage = errorResponse.statusText
        });
        $scope.newRound = function () {
            gameService.newRound();
            $location.path("/round");
        }
    };
    controllers.GameController.$inject = ["$scope", "$location", "gameService"];

    controllers.RoundController = function ($scope, gameService) {
        $scope.messages = new Array();
        $scope.messages.push(gameService.message);
        gameService.getRoundPlayers().then(function(response){
            $scope.roundPlayers = response.data
        }, function(errorResponse){
            $scope.errorMessage = errorResponse.statusText
        })
    };
    controllers.RoundController.$inject = ["$scope", "gameService"];

    return controllers;

});