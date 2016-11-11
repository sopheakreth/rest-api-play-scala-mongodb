package controllers.rest

import javax.inject.Inject

import Repositories.{TranslatorRepository, UserRepository}
import models.{Translator, User}
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers, Controller}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import utils.Pagination

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by acer on 11/10/2016.
  */
class TranslatorController @Inject()(val translatorService: TranslatorRepository, val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def getAllTranslators(page:Int, limit:Int, sort: String) = Action.async {implicit request =>

    val totalCount = Await.result(translatorService.count(), Duration.Inf)
    val pagination = new Pagination(page, limit, totalCount)
    translatorService.getAllTranslators(pagination, sort).map(translators => Ok(Json.obj("DATA" -> Json.toJson(translators), "PAGINATION" -> Json.toJson(pagination))))
  }

  def insertTranslator = Action.async(BodyParsers.parse.json) { implicit request =>
    val translatorObj = (request.body).as[Translator]
    translatorService.insertTranslator(translatorObj).map(result => Created)
  }
}
