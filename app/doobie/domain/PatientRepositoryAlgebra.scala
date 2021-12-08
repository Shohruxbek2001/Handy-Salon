package doobie.domain

import protocols.AdminProtocol.{GetProducts, GetSales, Product, Sale}

trait PatientRepositoryAlgebra[F[_]] {

  def create(product: Product): F[Int]
  def getProduct: F[List[GetProducts]]
  def createSale(sale: Sale): F[Int]
  def getSale: F[List[GetSales]]

}

trait ChatRepositoryAlgebra[F[_]] extends PatientRepositoryAlgebra[F]
