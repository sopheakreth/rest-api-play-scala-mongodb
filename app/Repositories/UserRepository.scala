package Repositories

import javax.inject.{Singleton, Inject}

import filters.UserFilter
import models.User
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.{QueryOpts, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection
import utils.Pagination

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 10/12/2016.
  */
@Singleton
class UserRepository @Inject()(reactiveMongoApi: ReactiveMongoApi){
  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(_.collection[JSONCollection]("users"))

  //TODO: find all users in collection
  def getAllUsers(pagination: Pagination, sortField: String)(implicit ec: ExecutionContext): Future[List[JsObject]] = {

    val sort: String = sortField.split(" ").flatMap(_.headOption).mkString
    var getField = sortField.substring(1)
    var s : Int = 1
    if (sort =="-"){ s = -1 }
    else{ getField = sortField }

    val genericQueryBuilder = collection.map(_.find(Json.obj()).sort(Json.obj(getField -> s)).options(QueryOpts(pagination.Offset)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary));
    cursor.flatMap(_.collect[List](pagination.Limit))
  }

  //TODO: find one user by using id
  def getUserId(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  //TODO: update user by using id
  def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(id, update))
  }

  //TODO: delete user by using id
  def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(id))
  }

  //TODO: insert user data into database
  def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(user))
  }

  //TODO: count all row users in collection
  def count()(implicit ec: ExecutionContext): Future[Int] = {
    val count = collection.flatMap(_.count()); count
  }
}
