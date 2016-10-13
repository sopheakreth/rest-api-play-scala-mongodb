package models

import play.api.libs.json._

/**
  * Created by acer on 10/12/2016.
  */

case class User(username: String, password: String, role: Int, status: Int, created_date: String, comment: String)

object User {
  implicit object UserFormat extends OFormat[User] {

    // TODO: convert from JSON string to a User object (de-serializing from JSON)
    def reads(json: JsValue): JsResult[User] = {
      val username = (json \ "username").as[String]
      val password = (json \ "password").as[String]
      val role = (json \ "role").as[Int]
      val status = (json \ "status").as[Int]
      val created_date = (json \ "created_date").as[String]
      val comment = (json \ "comment").as[String]
      JsSuccess(User(username, password, role, status, created_date, comment))
    }

    // TODO: convert from User object to JSON (serializing to JSON)
    def writes(u: User): JsObject = Json.obj(
      "username" -> u.username,
      "password" -> u.password,
      "role" -> u.role,
      "status" -> u.status,
      "created_date" -> u.created_date,
      "comment" -> u.comment
    )
  }






  /*implicit object UserReads extends Reads[User] {
    // convert from JSON string to a User object (de-serializing from JSON)
    def reads(json: JsValue): JsResult[User] = {
      val Username = (json \ "username").as[String]
      val Password = (json \ "password").as[String]
      JsSuccess(User(Username, Password))
    }
  }

  implicit object UserWrites extends OWrites[User]{
    /*def writes(u: User):JsObject = Json.obj(
      "username" -> JsString(u.Username),
      "password" -> JsString(u.Password)
    )*/
    // convert from User object to JSON (serializing to JSON)
    def writes(u: User): JsObject = {
      // JsObject requires Seq[(String, play.api.libs.json.JsValue)]
      val UserList = Seq(
        "username" -> JsString(u.Username),
        "password" -> JsString(u.Password))
      JsObject(UserList)
    }
  }*/
}
