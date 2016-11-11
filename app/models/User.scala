package models

import java.lang.annotation.Documented

import play.api.libs.json._
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json._
/**
  * Created by acer on 10/12/2016.
  */

case class User(
   username: String,
   password: String,
   role: Byte,
   status: Byte,
   created_date: String,
   comment: String
)

/*
abstract class User {
  var _id: Option[BSONObjectID]
  var username: String
  var password: String
  var role: Byte
  var status: Byte
  var created_date: String
  var comment: String
}
*/


object User {
  implicit val userFormat = Json.format[User]
}