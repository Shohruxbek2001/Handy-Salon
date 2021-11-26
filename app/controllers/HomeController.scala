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
                               indexPage: views.html.index,
                               implicit val actorSystem: ActorSystem,
                               implicit val webJarsUtil: WebJarsUtil)
  extends BaseController {

  def index: Action[AnyContent] = Action {
    Ok(indexPage())
  }
}
