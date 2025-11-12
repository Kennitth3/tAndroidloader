package com.example.modmanager

import android.content.Context
import java.io.File

object Utils {
    /**
     * Copy an asset into filesDir and return the resulting File
     */
    fun copyAssetToFiles(ctx: Context, assetPath: String): File {
        val out = File(ctx.filesDir, assetPath.substringAfterLast('/'))
        ctx.assets.open(assetPath).use { inp ->
            out.outputStream().use { outp -> inp.copyTo(outp) }
        }
        return out
    }
}
