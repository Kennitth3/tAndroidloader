package com.example.modmanager

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.util.zip.ZipFile

object ApkInspector {
    /**
     * Inspect an APK (Uri) for Unity/Mono/IL2CPP markers.
     * Returns a short report string.
     */
    fun inspectApk(ctx: Context, apkUri: Uri): String {
        val resolver = ctx.contentResolver
        val local = File(ctx.cacheDir, "inspect.apk")
        resolver.openInputStream(apkUri).use { input ->
            local.outputStream().use { out -> input?.copyTo(out) }
        }

        val report = StringBuilder()
        try {
            ZipFile(local).use { zip ->
                val entries = zip.entries().asSequence().map { it.name }.toList()
                // Look for managed assemblies (Unity mono)
                val hasManaged = entries.any { it.startsWith("assets/bin/Data/Managed/") || it.endsWith(".dll") }
                val hasIl2cpp = entries.any { it.contains("libil2cpp.so") || it.contains("il2cpp") }
                val hasUnity = entries.any { it.startsWith("assets/bin/Data") || entries.any { it.contains("Unity") } }

                report.append("APK path: ${local.absolutePath}\n")
                report.append("Contains managed assemblies (.dll in assets): $hasManaged\n")
                report.append("Contains IL2CPP/native: $hasIl2cpp\n")
                report.append("Likely Unity game: $hasUnity\n")
                report.append("Top-level files (sample):\n")
                entries.take(30).forEach { report.append("  - $it\n") }
            }
        } catch (e: Exception) {
            Log.e("ApkInspector", "inspect failed", e)
            report.append("Error inspecting APK: ${e.message}")
        }
        return report.toString()
    }
}
