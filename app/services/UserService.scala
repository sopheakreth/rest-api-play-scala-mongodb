package services

import javax.inject.Inject

import Repositories.UserRepository
import models.User
import play.api.libs.json.JsObject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import utils.Pagination

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 10/12/2016.
  */
class UserService @Inject()(val reactiveMongoApi: ReactiveMongoApi){
  def userRepository = new UserRepository(reactiveMongoApi)
  /**
    * service call function from user repository
    * function return value CRUD
    */
  def getAllUsers(pagination: Pagination, sortField: String)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    userRepository.getAllUsers(pagination, sortField)
  }

  def getUserId(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    userRepository.getUserId(id)
  }
  def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    userRepository.updateUser(id, update)
  }

  def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    userRepository.deleteUser(id)
  }

  def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    userRepository.insertUser(user)
  }

  //TODO: count collections
  def count()(implicit ec: ExecutionContext): Future[Int] = {
    userRepository.count()
  }
}
