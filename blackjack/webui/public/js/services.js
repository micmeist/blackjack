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
        var message = null;
        return {
            newGame: function () {
                game = null;
                $http.get("game/new").then(function (response) {
                    game = response.data;
                });
            },
            getGame: function () {
                return game;
            },
            newRound: function () {
                $http({
                    method: "POST",
                    url: "round/new",
                    data: game
                }).then(function (response) {
                    round = response.data;
                }, function (response) {
                    //TODO: Error handling
                    message = response.statusText
                });
            },
            getRound: function () {
                return round;
            },
            getMessage : function (){
                return message;
            }
        }
    });

});