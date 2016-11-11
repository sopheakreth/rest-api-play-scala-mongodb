package Repositories
import Repositories.implements.UserRepositoryImpl
import com.google.inject.ImplementedBy
import models.User
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import utils.Pagination

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 11/10/2016.
  */
@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {
  /**
    * service call function from user repository
    * function return value CRUD
    */
  def getAllUsers(pagination: Pagination, sortField: String)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def getUserId(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult]

  def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult]

  //TODO: count collections
  def count()(implicit ec: ExecutionContext): Future[Int]
}
