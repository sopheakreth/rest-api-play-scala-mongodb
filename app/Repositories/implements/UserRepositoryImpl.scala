package Repositories.implements

import javax.inject.{Inject, Singleton}

import Repositories.UserRepository
import models.User
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONString, Macros}
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{QueryOpts, ReadPreference}
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection
import utils.Pagination

import scala.concurrent.{ExecutionContext, Future}


/**
  * Created by acer on 10/12/2016.
  */
@Singleton
class UserRepositoryImpl @Inject()(reactiveMongoApi: ReactiveMongoApi) extends UserRepository {
  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(_.collection[JSONCollection]("users"))

  //TODO: find all users in collection
  override def getAllUsers(pagination: Pagination,col: BSONCollection)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    import col.BatchCommands.AggregationFramework._

    col.aggregate(Group(BSONDocument("user" -> "$user"))(
      "pop" -> SumField("username")),
      List(
        Skip(pagination.Offset), // <-- skip some states if offset   > 0
        Limit(pagination.Limit), // <-- limit the state groups
        Sort(Ascending("username")),
        Group(BSONString("$_id.user"))(
          "biggestCity" -> LastField("_id.city"),
          "biggestPop" -> LastField("pop"),
          "smallestCity" -> FirstField("_id.city"),
          "smallestPop" -> FirstField("pop")),
        Project(BSONDocument("_id" -> 0, "state" -> "$_id",
          "biggestCity" -> BSONDocument("name" -> "$biggestCity",
            "population" -> "$biggestPop"),
          "smallestCity" -> BSONDocument("name" -> "$smallestCity",
            "population" -> "$smallestPop"))))).
      map(_.result[User])


//    pagination: Pagination, sortField: String
/*    var sort : Int = 1; var getField: String = sortField
    if(sortField.startsWith("-")){
      sort = -1; getField = sortField.drop(1)
    }
    val genericQueryBuilder = collection.map(_.find(Json.obj()).sort(Json.obj(getField -> sort)).options(QueryOpts(pagination.Offset)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Limit))*/

  }

  //TODO: find one user by using id
  override def getUserId(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  //TODO: update user by using id
  override def updateUser(id: BSONDocument, update: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(id, update))
  }

  //TODO: delete user by using id
  override def deleteUser(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(id))
  }

  //TODO: insert user data into database
  override def insertUser(user: User)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(user))
  }

  //TODO: count all row users in collection
  override def count()(implicit ec: ExecutionContext): Future[Int] = {
    val count = collection.flatMap(_.count()); count
  }
}
