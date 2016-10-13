package services.Impl

import javax.inject.Inject
import Repositories.UserRepo
import models.User
import play.api.libs.json.JsObject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import services.UserService

import scala.concurrent.{Future, ExecutionContext}

/**
  * Created by acer on 10/12/2016.
  */
class UserServiceImpl @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends UserService{
  def userRepo = new UserRepo(reactiveMongoApi)

  def getAllUsers()(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    return userRepo.getAllUsers()
  }

  def getUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    return userRepo.getUser(id)
  }
  def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    return userRepo.updateUser(id, update)
  }

  def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    return userRepo.deleteUser(id)
  }

  def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    return userRepo.insertUser(user)
  }
}
