package Repositories

import Repositories.implements.TranslatorRepositoryImpl
import com.google.inject.ImplementedBy
import models.Translator
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import utils.Pagination

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 11/10/2016.
  */
@ImplementedBy(classOf[TranslatorRepositoryImpl])
trait TranslatorRepository {

  def getAllTranslators(pagination: Pagination, sortField: String)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def insertTranslator(translator: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def count()(implicit ec: ExecutionContext): Future[Int]
}
