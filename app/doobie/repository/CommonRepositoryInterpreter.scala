package doobie.repository

import cats.effect.Bracket
import doobie._
import doobie.domain.PatientRepositoryAlgebra
import protocols.AdminProtocol.{GetProducts, GetSales, Product, Sale}
import doobie.implicits._

trait CommonSQL {

  def create(product: Product): ConnectionIO[Int]
  def getProduct: ConnectionIO[List[GetProducts]]
  def createSale(sale: Sale): ConnectionIO[Int]
  def getSale: ConnectionIO[List[GetSales]]

}

abstract class CommonRepositoryInterpreter[F[_]: Bracket[*[_], Throwable]](val xa: Transactor[F])
  extends PatientRepositoryAlgebra[F] {

  val commonSql: CommonSQL

  override def create(product: Product): F[Int] = {
    commonSql.create(product).transact(xa)
  }

  override def getProduct: F[List[GetProducts]] = {
    commonSql.getProduct.transact(xa)
  }


  override def createSale(sale: Sale): F[Int] = {
    commonSql.createSale(sale).transact(xa)
  }

  override def getSale: F[List[GetSales]] = {
    commonSql.getSale.transact(xa)
  }
}