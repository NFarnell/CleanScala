package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def help() = Action {
    Ok("Hello World")
  }

  def index() = Action {
    Ok(<h1>Hello World!</h1>).as("text/html")
  }
  def hello() = Action { implicit request: Request[AnyContent] =>
    Ok("Welcome!").withCookies(Cookie("colour", "Red")).withSession(request.session +  "connected" -> "user@gmail.com")
    Ok(request.session.get("connected").getOrElse("User is not logged in"))

  }

  Action { implicit request: Request[AnyContent] =>
    Ok(request.headers.get("Host").getOrElse("Value not found"))
  }
  def read() = Action { implicit request: Request[AnyContent] =>
    Ok(request.flash.get("success").getOrElse("Something went wrong while redirecting"))
  }



  def write() = Action {
    Redirect("/read").flashing("success" -> "You have been successfully redirected")

  }
  def body() = Action { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      Ok("Got:" + (json \ "name").as[String])
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }
  }
  def files() = Action {
    Ok(views.html.index(6))

  }



}