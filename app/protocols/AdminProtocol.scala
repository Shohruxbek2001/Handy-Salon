package protocols

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json._

object AdminProtocol {

  implicit def ldtRead(fieldName: String, dateFormat: String = "yyyy-MM-dd HH:mm:ss"): Reads[LocalDateTime] =
    (__ \ fieldName).read[String].map(s => LocalDateTime.parse(s, DateTimeFormatter.ofPattern(dateFormat)))

  case class User(login: String, password: String, firstLastName: String)
  case class Product(created_at: LocalDateTime,
                        Firm_name: String,
                        Product_type: String,
                        Product_name: String,
                        dose: String,
                        quantity: Int,
                        bought_prise_per_of_1: String,
                        sale_prise_per_of_1: String,
                        total: String)
  case class AddProduct(product: Product)
  case class GetProducts(id: Int,
                         created_at: LocalDateTime,
                         Firm_name: String,
                         Product_type: String,
                         Product_name: String,
                         dose: String,
                         quantity: Int,
                         bought_prise_per_of_1: String,
                         sale_prise_per_of_1: String,
                         total: String)
  implicit val formatProductsFormat: OFormat[GetProducts] = Json.format[GetProducts]

  case class Sale(created_at: LocalDateTime,
                     fullName: String,
                     autoNumber: String,
                     phoneNumber: String,
                     motorNumber: String,
                     typeP: Option[String],
                     firmName: Option[String],
                     productName: Option[String],
                     priceD: String,
                     quantity: Int,
                     total: String)
  case class AddSale(sale: Sale)
  case class GetSales(id: Int,
                      created_at: LocalDateTime,
                      fullName: String,
                      autoNumber: String,
                      phoneNumber: String,
                      motorNumber: String,
                      typeP: Option[String],
                      firmName: Option[String],
                      productName: Option[String],
                      priceD: String,
                      quantity: Int,
                      total: String)
  implicit val formatSalesFormat: OFormat[GetSales] = Json.format[GetSales]
}
