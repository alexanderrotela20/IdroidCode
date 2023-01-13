package com.ardev.idroid.ext

import java.util.zip.ZipFile
import java.io.File
import java.net.URI

fun String.toFile() = File(this)

fun String.toZipFile() = ZipFile(this)

fun String.toURI() = URI(this)