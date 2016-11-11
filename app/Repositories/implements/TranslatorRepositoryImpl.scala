package Repositories.implements

import Repositories.TranslatorRepository
import com.google.inject.Inject
import models.Translator
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{QueryOpts, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection
import utils.Pagination
import play.modules.reactivemongo.json._
import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 11/10/2016.
  */
class TranslatorRepositoryImpl @Inject()(reactiveMongoApi: ReactiveMongoApi) extends TranslatorRepository{
  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(_.collection[JSONCollection]("translators"))

  override def getAllTranslators(pagination: Pagination, sortField: String)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    var sort : Int = 1; var getField: String = sortField
    if(sortField.startsWith("-")){
      sort = -1; getField = sortField.drop(1)
    }
    val genericQueryBuilder = collection.map(_.find(Json.obj()).sort(Json.obj(getField -> sort)).options(QueryOpts(pagination.Offset)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Limit))
  }

  override def insertTranslator(translator: Translator)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(translator))
  }

  override def count()(implicit ec: ExecutionContext): Future[Int] = {
    val count = collection.flatMap(_.count()); count
  }
}
