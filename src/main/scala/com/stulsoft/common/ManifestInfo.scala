/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.common

import com.typesafe.scalalogging.StrictLogging

import java.net.URL
import java.util.jar.Manifest
import scala.annotation.tailrec

case class ManifestInfo(groupId: String, artifactId: String) extends StrictLogging:
  private val jar = s"""(.*)$groupId.$artifactId-(.*).jar(.*)""".r

  @tailrec
  private def defineUrl(resources: java.util.Enumeration[URL]): Option[URL] =
    if !resources.hasMoreElements then
      None
    else
      val url = resources.nextElement()
      val urlOption: Option[URL] = url.getPath match
        case jar(_, _, _) =>
          Option(url)
        case _ => None

      if urlOption.isDefined then
        urlOption
      else
        defineUrl(resources)

  private def resourceWithManifest(): Option[URL] =
    val resources = classOf[ManifestInfo]
      .getClassLoader
      .getResources("META-INF/MANIFEST.MF")

    defineUrl(resources)

  private def manifestAttrValue(manifest: Manifest, key: String): String =
    manifest.getMainAttributes.getValue(key) match
      case v: String => v
      case null => ""

  def showManifest(): Unit =
    try
      resourceWithManifest().foreach(url =>
        val is = url.openStream()
        val manifest = new Manifest(is)
        val title = manifestAttrValue(manifest, "Implementation-Title")
        val version = manifestAttrValue(manifest, "Implementation-Version")
        val date = manifestAttrValue(manifest, "Build-Date")
        val text = s"$title, $version, $date"
        println(text)
      )
    catch
      case exception: Exception => exception.printStackTrace()

  def version(): Option[String] =
    resourceWithManifest() match
      case Some(url) =>
        val is = url.openStream()
        val manifest = new Manifest(is)
        is.close()
        Option(manifestAttrValue(manifest, "Implementation-Version"))
      case None => None

  def buildDate(): Option[String] =
    resourceWithManifest() match
      case Some(url) =>
        val is = url.openStream()
        val manifest = new Manifest(is)
        is.close()
        Option(manifestAttrValue(manifest, "Build-Date"))
      case None => None

  def buildTitle(titleName: String): String =
    val theVersion = version() match
      case Some(version) => version
      case _ => ""

    val theBuildDate = buildDate() match
      case Some(buildDate) => buildDate
      case _ => ""

    titleName + " " + theVersion + " " + theBuildDate