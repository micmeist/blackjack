/*global require, requirejs */

'use strict';

requirejs.config({
  paths: {
    'angular': ['../lib/angularjs/angular'],
    'angular-route': ['../lib/angularjs/angular-route']
  },
  shim: {
    'angular': {
      exports : 'angular'
    },
    'angular-route': {
      deps: ['angular'],
      exports : 'angular'
    }
  }
});

require(['angular', './controllers', './directives', './filters', './services', 'angular-route'],
  function(angular, controllers) {

    // Declare app level module which depends on filters, and services

    angular.module('blackjack', ['blackjack.filters', 'blackjack.services', 'blackjack.directives', 'ngRoute']).
      config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: controllers.HomeController});
        $routeProvider.when('/game', {templateUrl: 'partials/game.html', controller: controllers.GameController});
        $routeProvider.when('/round', {templateUrl: 'partials/round.html', controller: controllers.RoundController});
        $routeProvider.otherwise({redirectTo: '/home'});
      }]);

    angular.bootstrap(document, ['blackjack']);

});
