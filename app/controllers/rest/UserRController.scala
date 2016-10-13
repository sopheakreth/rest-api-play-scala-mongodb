package controllers.rest

import javax.inject.Inject

import models.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import services.Impl.UserServiceImpl


/**
  * Created by acer on 10/12/2016.
  */

/*case class GetAllUsers(paginationParams: Pagination) extends PaginatedDataRequest

case class AllUsersSummary(paginationParams: Pagination,
                           totalRecordsAvailable: Int,
                           totalPages: Int,
                           records: List[User]) extends PaginatedDataResponse[User]*/

class UserRController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def userService = new UserServiceImpl(reactiveMongoApi)

  def getAllUsers = Action.async {implicit request =>
    userService.getAllUsers().map(users => Ok(Json.prettyPrint(Json.obj("DATA" -> Json.toJson(users)))))
  }

  def getUser(id: String) = Action.async { implicit request =>
    userService.getUser(BSONDocument("_id" -> BSONObjectID(id))).map(user => Ok(Json.prettyPrint(Json.toJson(user))))
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
