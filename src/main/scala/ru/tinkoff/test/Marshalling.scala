package ru.tinkoff.test

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller, ToResponseMarshaller}
import akka.http.scaladsl.model.MediaTypes.`application/json`
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Printer}

object Marshalling {

  private[this] implicit val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  implicit def marshallResponseCirce[T: Encoder]: ToResponseMarshaller[T] = Marshaller.fromToEntityMarshaller[T]()

  implicit def marshallEntityCirce[T: Encoder]: ToEntityMarshaller[T] =
    Marshaller.stringMarshaller(`application/json`).compose((t: T) => t.asJson.printWith(printer))

}
