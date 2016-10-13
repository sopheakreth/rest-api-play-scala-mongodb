package services

import models.User
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

import scala.concurrent.{Future, ExecutionContext}

/**
  * Created by acer on 10/12/2016.
  */
trait UserService {
  def getAllUsers()(implicit ec: ExecutionContext): Future[List[JsObject]]

  def getUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult]

  def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult]
}
