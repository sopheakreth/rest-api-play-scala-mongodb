package utils

import play.api.libs.json._

/**
  * Created by acer on 10/13/2016.
  */
class Pagination(page: Int, limit: Int, totalCount: Int) {
  val Page: Int = page
  val Limit: Int = limit
  val TotalCount: Int = totalCount
  val TotalPages: Int = Math.ceil(totalCount / limit.asInstanceOf[Double]).asInstanceOf[Int]
  val Offset: Int = (Page - 1) * Limit
}

object Pagination {
  implicit object PaginationFormat extends OWrites[Pagination] {
    // TODO: convert from Pagination object to JSON
    def writes(p: Pagination): JsObject = Json.obj(
      "PAGE" -> p.Page,
      "LIMIT" -> p.Limit,
      "TOTAL_COUNT" -> p.TotalCount,
      "TOTAL_PAGES" -> p.TotalPages,
      "OFFSET" -> p.Offset
    )
  }
}


