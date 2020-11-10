/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.common

import com.stulsoft.common.DateInput.dateTimeToTimestamp
import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success}

/**
 * @author Yuriy Stul
 */
class DateInputTest extends AnyFunSuite {

  test("testDateTimeToTimestamp - full date time input") {
    val input = "2020-11-12 11:22:33"
    dateTimeToTimestamp(input) match {
      case Success(tm) => assertResult(1605172953000L)(tm)
      case Failure(exception) => fail(exception)
    }
  }

  test("testDateTimeToTimestamp - only date input") {
    val input = "2020-11-12"
    dateTimeToTimestamp(input) match {
      case Success(tm) => assertResult(1605132000000L)(tm)
      case Failure(exception) => fail(exception)
    }
  }

  test("testDateTimeToTimestamp - wrong input") {
    val input = "2020-11-12 76:"
    println(s"$input => ${dateTimeToTimestamp(input)}")
    dateTimeToTimestamp(input) match {
      case Success(_) => fail("Should be Failure")
      case Failure(_) => succeed
    }
  }

}
