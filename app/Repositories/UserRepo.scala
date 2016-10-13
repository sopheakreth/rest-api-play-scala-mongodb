package Repositories

import javax.inject.Inject

import models.User
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONObjectID, BSONDocument}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 10/12/2016.
  */
class UserRepo @Inject()(reactiveMongoApi: ReactiveMongoApi){
  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(_.collection[JSONCollection]("users"))

  def getAllUsers()(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.map(_.find(Json.obj()));
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary));
    cursor.flatMap(_.collect[List]())
  }

  def getUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(id, update))
  }

  def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(id))
  }

  def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(user))
  }
}
