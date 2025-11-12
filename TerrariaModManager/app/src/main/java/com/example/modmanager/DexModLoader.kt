package com.example.modmanager

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.reflect.Constructor

/**
 * Very small DexClassLoader-based loader.
 * Expects the mod artifact to provide a class 'com.example.mymod.ModMain'
 * implementing the Mod interface.
 */
object DexModLoader {
    fun loadMod(ctx: Context, dexFile: File): Mod {
        val optimizedDir = File(ctx.filesDir, "opt_dex").apply { if (!exists()) mkdirs() }
        val loader = DexClassLoader(dexFile.absolutePath, optimizedDir.absolutePath, null, ctx.classLoader)
        val className = "com.example.mymod.ModMain"
        val clazz = loader.loadClass(className)
        val instance = clazz.getDeclaredConstructor().newInstance()
        if (instance is Mod) {
            val mctx = object : ModContext {
                override fun log(msg: String) = android.util.Log.i("Mod", msg)
                override fun registerItem(id: String, json: String) { android.util.Log.i("ModReg","$id -> $json") }
            }
            instance.onEnable(mctx)
            return instance
        } else {
            throw IllegalArgumentException("Loaded class does not implement Mod interface")
        }
    }
}
