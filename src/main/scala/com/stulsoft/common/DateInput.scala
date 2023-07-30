/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.common

import java.time.format.DateTimeFormatterBuilder
import java.time.{Instant, ZoneOffset}

import scala.util.Try

/**
 * @author Yuriy Stul
 */
object DateInput:
  private lazy val dateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd HH:mm:ss")
    .toFormatter()
    .withZone(ZoneOffset.UTC)

  def dateTimeToTimestamp(input: String): Try[Long] =
    Try {
      val dateTimeInput = """([0-9]{4}-[0-9]{2}-[0-9]{2}) ([0-9]{2}:[0-9]{2}:[0-9]{2})""".r
      val dateInput = """([0-9]{4}-[0-9]{2}-[0-9]{2})""".r
      input match {
        case dateTimeInput(_, _) =>
          dateTimeFormatter.parse(input, x => Instant.from(x)).toEpochMilli
        case dateInput(_) =>
          dateTimeFormatter.parse(input + " 00:00:00", x => Instant.from(x)).toEpochMilli
        case _ =>
          throw new RuntimeException("Invalid date/time format")
      }
    }

