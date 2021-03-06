package controllers

import de.htwg.core.GameCoreController
import de.htwg.core.entities.{HumanPlayer, Round, Game}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def newGame = Action {
    val game = GameCoreController.startNewGame
    Ok(Json.stringify(Json.toJson(game)))
  }

  def newRound = Action(parse.json) { request =>
    request.body.validate[Game].map {
      case (game: Game) =>
        val round = GameCoreController.startNewRound(game)
        Ok(Json.stringify(Json.toJson(round)))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def getRoundPlayers = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(round.getRoundPlayers)))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def getGamePlayers = Action(parse.json) { request =>
    request.body.validate[Game].map {
      case (game: Game) =>
        Ok(Json.stringify(Json.toJson(game.getPlayers)))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }


  def finishRound = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(round.finish())))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def hit = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(round.hit(round.humanRoundPlayer.player))))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def bet(amount: Int) = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(round.bet(round.humanRoundPlayer.player.asInstanceOf[HumanPlayer], amount))))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

}