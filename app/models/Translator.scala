package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json._

/**
  * Created by acer on 11/10/2016.
  */
case class Translator (
  firstname: String,
  lastname: String,
  email: String,
  languages: String,
  user_id: User
)

object Translator {
  implicit val translatorFormat = Json.format[Translator]
}