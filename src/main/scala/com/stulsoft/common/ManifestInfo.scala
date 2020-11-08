/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.common

import java.net.URL
import java.util.jar.Manifest

import scala.annotation.tailrec

/**
 * @author Yuriy Stul
 */
sealed class ManifestInfo(groupId:String, artifactId:String) {
  private val jar = s"""(.*)$groupId.$artifactId-(.*).jar(.*)""".r

  @tailrec
  private def defineUrl(resources: java.util.Enumeration[URL]): Option[URL] = {
    if (!resources.hasMoreElements)
      None
    else {
      val url = resources.nextElement()
      val urlOption: Option[URL] = url.getPath match {
        case jar(_, _, _) =>
          Option(url)
        case _ => None
      }
      if (urlOption.isDefined)
        urlOption
      else
        defineUrl(resources)
    }
  }

  private def resourceWithManifest(): Option[URL] = {
    val resources = classOf[ManifestInfo]
      .getClassLoader
      .getResources("META-INF/MANIFEST.MF")

    defineUrl(resources)
  }

  private def manifestAttrValue(manifest: Manifest, key: String): String = {
    manifest.getMainAttributes.getValue(key) match {
      case v: String => v
      case _ => ""
    }
  }

  def showManifest(): Unit = {
    try {
      resourceWithManifest().foreach(url => {
        val is = url.openStream()
        val manifest = new Manifest(is)
        val title = manifestAttrValue(manifest, "Implementation-Title")
        val version = manifestAttrValue(manifest, "Implementation-Version")
        val date = manifestAttrValue(manifest, "Build-Date")
        println(s"$title, $version, $date")
      })
    } catch {
      case ex: Exception => ex.printStackTrace()
    }
  }

}
object ManifestInfo{
  def apply(groupId: String, artifactId: String): ManifestInfo = new ManifestInfo(groupId, artifactId)
}