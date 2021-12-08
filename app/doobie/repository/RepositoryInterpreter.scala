package doobie.repository

import cats.effect.Bracket
import com.typesafe.scalalogging.LazyLogging
import doobie._
import doobie.domain.PatientRepositoryAlgebra
import doobie.implicits._
import protocols.AdminProtocol._
import doobie.implicits.javasql._
import doobie.implicits.javatime._

import java.sql.Timestamp
import java.time.LocalDateTime

object MessageSQL extends CommonSQL with LazyLogging {

  implicit val han: LogHandler = LogHandler.jdkLogHandler


  private def javaLdTime2JavaSqlTimestamp(ldTime: LocalDateTime): Timestamp = {
    Timestamp.valueOf(ldTime)
  }


  private def updateQueryWithUniqueId(fr: Fragment): doobie.ConnectionIO[Int] = {
    fr.update.withUniqueGeneratedKeys[Int]("id")
  }

  def create(product: Product): doobie.ConnectionIO[Int] = {
    val values = {
      fr""" values (
        ${javaLdTime2JavaSqlTimestamp(product.created_at)},${product.Firm_name}, ${product.Product_type},
         ${product.Product_name}, ${product.dose}, ${product.quantity}, ${product.bought_prise_per_of_1},
          ${product.sale_prise_per_of_1}, ${product.total}
      )"""
    }

    val fieldsName =
      fr"""
        insert into "Buy" (created_at, firm_name, product_type, product_name, dose, quantity, bought_prise_per_of_1,
         sale_prise_per_of_1, total)
        """

    updateQueryWithUniqueId(fieldsName ++ values)
  }

  def createSale(sale: Sale): doobie.ConnectionIO[Int] = {
    val values = {
      fr""" values (
        ${javaLdTime2JavaSqlTimestamp(sale.created_at)},${sale.fullName}, ${sale.autoNumber},
         ${sale.phoneNumber}, ${sale.motorNumber}, ${sale.typeP}, ${sale.firmName},
          ${sale.productName}, ${sale.priceD}, ${sale.quantity}, ${sale.total}
      )"""
    }

    val fieldsName =
      fr"""
        insert into "Store" (created_at, full_name, auto_number, phone_number, motor_number, type_p, firm_name,
         product_name, price_d, quantity, total)
        """

    updateQueryWithUniqueId(fieldsName ++ values)
  }

  def getProduct: ConnectionIO[List[GetProducts]] = {
    val querySql =
      fr"""SELECT id, created_at, firm_name, product_type, product_name, dose, quantity, bought_prise_per_of_1,
          sale_prise_per_of_1, total FROM "Buy" ORDER BY id """
    querySql.query[GetProducts].to[List]
  }





//
//
//  def create(sale: Sale): doobie.ConnectionIO[Int] = {
//    val values = {
//      fr""" values (
//        ${javaLdTime2JavaSqlTimestamp(sale.created_at)},${sale.fullName}, ${sale.autoNumber},
//         ${sale.phoneNumber}, ${sale.typeP}, ${sale.firmName}, ${sale.productName},
//          ${sale.priceD}, ${sale.quantity}, ${sale.total}
//      )"""
//    }
//
//    val fieldsName =
//      fr"""
//        insert into "Store" (created_at, fullName, autoNumber, phoneNumber, motorNumber, typeP, firmName, productName,
//         priceD, quantity, total)
//        """
//
//    updateQueryWithUniqueId(fieldsName ++ values)
//  }

  def getSale: ConnectionIO[List[GetSales]] = {
    val querySql =
      fr"""SELECT id, created_at, fullName, autoNumber, phoneNumber, motorNumber, typeP, firmName, productName,
         priceD, quantity, total FROM "Store" ORDER BY id """
    querySql.query[GetSales].to[List]
  }
}

class RepositoryInterpreter[F[_] : Bracket[*[_], Throwable]](override val xa: Transactor[F])
  extends CommonRepositoryInterpreter[F](xa) with PatientRepositoryAlgebra[F] {

  override val commonSql: CommonSQL = MessageSQL

}

object RepositoryInterpreter {
  def apply[F[_] : Bracket[*[_], Throwable]](xa: Transactor[F]): RepositoryInterpreter[F] =
    new RepositoryInterpreter(xa)
}