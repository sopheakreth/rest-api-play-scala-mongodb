package controllers.rest

import javax.inject.Inject

import Repositories.UserRepository
import models.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import utils.Pagination

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by acer on 10/12/2016.
  */

class UserController @Inject()(val userService: UserRepository, val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def getAllUsers(page:Int, limit:Int, sort: String) = Action.async {implicit request =>

    val totalCount = Await.result(userService.count(), Duration.Inf)
    val pagination = new Pagination(page, limit, totalCount)
    userService.getAllUsers(pagination, sort).map(users => Ok(Json.obj("DATA" -> Json.toJson(users), "PAGINATION" -> Json.toJson(pagination))))
  }

  def getUserId(id: String) = Action.async { implicit request =>
    userService.getUserId(BSONDocument("_id" -> BSONObjectID(id))).map(user => Ok(Json.prettyPrint(Json.toJson(user))))
  }

  def insertUser = Action.async(BodyParsers.parse.json) { implicit request =>
    val userObj = (request.body).as[User]
    userService.insertUser(userObj).map(result => Created)
  }

  def updateUser(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val userObj = (request.body).as[User]
    userService.updateUser(BSONDocument("_id" -> BSONObjectID(id)), userObj).map(result => Accepted)
  }

  def deleteUser(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    userService.deleteUser(BSONDocument("_id" -> BSONObjectID(id))).map(result => Accepted)
  }
}
