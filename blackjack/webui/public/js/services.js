/*global define */

'use strict';

define(['angular'], function (angular) {

    /* Services */

// Demonstrate how to register services
// In this case it is a simple value service.
    angular.module('blackjack.services', []).
    value('version', '0.1').
    factory("gameService", function ($http) {
        var game;
        var round;
        return {
            newGame: function () {
                game = null;
                return $http.get("game/new");
            },
            newRound: function (game) {
                return $http({
                    method: "POST",
                    url: "round/new",
                    data: game
                });
            },
            getGamePlayers: function (game) {
                return $http({
                    method: "POST",
                    url: "game/players",
                    data: game
                });
            },
            getRoundPlayers: function (round) {
                return $http({
                    method: "POST",
                    url: "round/players",
                    data: round
                });
            },
            getRoundWinners : function (round){
                return $http({
                    method: "POST",
                    url: "round/winners",
                    data: round
                });
            }
        }
    });

});