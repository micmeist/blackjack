/*global define */

'use strict';

define(function () {

    /* Controllers */

    var controllers = {};

    controllers.HomeController = function ($scope, $location, gameService) {
        $scope.newGame = function () {
            gameService.newGame().then(function (response) {
                gameService.game = response.data;
                $location.path("/game");
            }, function(response){
                $scope.errorMessage = response.status + ": " +  response.statusText
            });
        }
    };
    controllers.HomeController.$inject = ["$scope", "$location", "gameService"];

    controllers.GameController = function ($scope, $location, gameService) {
        gameService.getGamePlayers(gameService.game).then(function(response){
            $scope.gamePlayers = response.data
        }, function(errorResponse){
            $scope.errorMessage = errorResponse.status + ": " +  errorResponse.statusText
        });

        $scope.newRound = function () {
            gameService.newRound(gameService.game).then(function (response) {
                gameService.round = response.data;
                $location.path("/round");
            }, function (response) {
                $scope.errorMessage = errorResponse.status + ": " +  errorResponse.statusText
            });

        }
    };
    controllers.GameController.$inject = ["$scope", "$location", "gameService"];

    controllers.RoundController = function ($scope, gameService) {
        gameService.getRoundPlayers(gameService.round).then(function(response){
            $scope.roundPlayers = response.data
        }, function(errorResponse){
            $scope.errorMessage = errorResponse.status + ": " +  errorResponse.statusText
        })
    };
    controllers.RoundController.$inject = ["$scope", "gameService"];

    return controllers;

});