/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.common

import java.io.File

object AppDataPath:
  def appDataPath(appName: String): String =
    val path = s"""${System.getenv("APPDATA")}\\$appName"""
    val file = new File(path)
    if !file.exists() then file.mkdir()
    path
