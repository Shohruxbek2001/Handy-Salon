package controllers


import akka.actor.ActorSystem
import org.webjars.play.WebJarsUtil
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.libs.Files.logger
import play.api.mvc._

import java.net.URL
import javax.inject._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               loginPage: views.html.login,
                               registerPage: views.html.register,
                               notFoundPage: views.html.notFound,
                               dashboardPage: views.html.dashboard,
                               implicit val actorSystem: ActorSystem,
                               implicit val webJarsUtil: WebJarsUtil)
  extends BaseController {

  def login: Action[AnyContent] = Action {
    Ok(loginPage())
  }
  def register: Action[AnyContent] = Action {
    Ok(registerPage())
  }
  def notFound: Action[AnyContent] = Action {
    Ok(notFoundPage())
  }
  def dashboard: Action[AnyContent] = Action {
    Ok(dashboardPage())
  }
}
