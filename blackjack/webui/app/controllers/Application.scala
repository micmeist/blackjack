package controllers

import play.api.mvc._

object Application extends Controller {

  val keyGame = "game"

  def index = Action {
    Ok(views.html.index())
  }

}