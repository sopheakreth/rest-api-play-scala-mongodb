package utils

/**
  * Created by acer on 10/13/2016.
  */
case class Pagination (page: Int, limit: Int)


trait PaginatedDataRequest {
  val pagination: Pagination

  def fistRecordNumber = (pagination.page - 1) * pagination.limit
}

trait PaginatedDataResponse[T] {
  val pagination: Pagination
  val totalCount: Int
  val totalPages: Int
  val records: List[T]
}

object Pagination {
  def totalPages(totalCount: Int, limit: Int): Int =
     Math.ceil(totalCount / limit.asInstanceOf[Double]).asInstanceOf[Int]
}


