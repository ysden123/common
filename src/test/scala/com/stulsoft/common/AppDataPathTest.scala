/*
 * Copyright (c) 2023. StulSoft
 */
package com.stulsoft.common

import org.scalatest.funsuite.AnyFunSuite

class AppDataPathTest  extends AnyFunSuite {
  test("appDataPath") {
    val appDataPath = AppDataPath.appDataPath("commonYSTest")
    println(s"appDataPath: $appDataPath")
  }
}
