/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.common

import java.sql.Timestamp

import scala.util.Try

/**
 * @author Yuriy Stul
 */
object DateInput{
  def dateTimeToTimestamp(input: String): Try[Long] = {
    Try {
      val dateTimeInput = """([0-9]{4}-[0-9]{2}-[0-9]{2}) ([0-9]{2}:[0-9]{2}:[0-9]{2})""".r
      val dateInput = """([0-9]{4}-[0-9]{2}-[0-9]{2})""".r
      input match {
        case dateTimeInput(_, _) =>
          Timestamp.valueOf(input).getTime
        case dateInput(_) =>
          Timestamp.valueOf(input + " 00:00:00").getTime
        case _ =>
          throw new RuntimeException("Invalid date/time format")
      }
    }
  }
}
