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
            lost: function (game) {
                return $http({
                    method: "POST",
                    url: "game/lost",
                    data: game
                });
            },
            hit: function (round) {
                return $http({
                    method: "POST",
                    url: "round/hit",
                    data: round
                });
            },
            bet: function (round, amount) {
                return $http({
                    method: "POST",
                    url: "round/bet/" + amount,
                    data: round
                });
            },
            finish: function (round) {
                return $http({
                    method: "POST",
                    url: "round/finish",
                    data: round
                });
            }
        }
    });

});