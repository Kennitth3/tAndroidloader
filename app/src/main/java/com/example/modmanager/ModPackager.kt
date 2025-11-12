package com.example.modmanager

import android.content.Context
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ModPackager {
    /**
     * Build a simple .amod zip from a directory (manifest + scripts + assets).
     */
    fun packageFromFolder(folder: File, outFile: File) {
        ZipOutputStream(outFile.outputStream()).use { zos ->
            folder.walkTopDown().filter { it.isFile }.forEach { file ->
                val rel = file.relativeTo(folder).path.replace(File.separatorChar, '/')
                val entry = ZipEntry(rel)
                zos.putNextEntry(entry)
                file.inputStream().use { it.copyTo(zos) }
                zos.closeEntry()
            }
        }
    }
}
