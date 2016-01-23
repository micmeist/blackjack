package controllers

import de.htwg.core.GameCoreController
import de.htwg.core.entities.{Game, Round}
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

  def lost = Action(parse.json) { request =>
    request.body.validate[Game].map {
      case (game: Game) =>
        Ok(Json.stringify(Json.toJson(GameCoreController.lost(game))))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def finishRound = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(GameCoreController.finish(round))))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def hit = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(GameCoreController.hit(round))))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

  def bet(amount: Int) = Action(parse.json) { request =>
    request.body.validate[Round].map {
      case (round: Round) =>
        Ok(Json.stringify(Json.toJson(GameCoreController.bet(round, amount))))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }
  }

}