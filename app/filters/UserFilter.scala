package filters

import models.User
import play.api.libs.json._

/**
  * Created by acer on 10/13/2016.
  */
case class UserFilter(username: String)
object UserFilter {

  implicit object UserFormat extends OFormat[UserFilter] {

    // TODO: convert from JSON string to a User object (de-serializing from JSON)
    def reads(json: JsValue): JsResult[UserFilter] = {
      val username = (json \ "username").as[String]
      JsSuccess(UserFilter(username))
    }

    // TODO: convert from User object to JSON (serializing to JSON)
    def writes(u: UserFilter): JsObject = Json.obj(
      "username" -> u.username
    )
  }

}
