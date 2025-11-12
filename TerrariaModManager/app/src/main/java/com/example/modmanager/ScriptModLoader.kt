package com.example.modmanager

import android.content.Context
import com.squareup.duktape.Duktape
import java.io.File

object ScriptModLoader {
    /**
     * Runs a JS 'main.js' file that expects certain functions: onEnable(ctx)
     * This is tiny and synchronous for demo purposes.
     */
    fun runScriptMod(ctx: Context, scriptFile: File, logger: (String) -> Unit) {
        val scriptText = scriptFile.readText()
        val duktape = Duktape.create()
        try {
            // Provide a minimal "ctx" object with a 'log' function and 'registerItem'
            duktape.evaluate("var __android_ctx = { log: function(s) { _log(s); }, registerItem: function(id,json){ _registerItem(id,json);} };")
            // Bind the two functions
            duktape.set("_log") { s: String -> logger("script: $s") }
            duktape.set("_registerItem") { id: String, json: String -> logger("script registerItem $id -> $json") }

            // Evaluate the script
            duktape.evaluate(scriptText)

            // Attempt to call onEnable if present
            duktape.evaluate("if (typeof onEnable === 'function') { try { onEnable(__android_ctx); } catch(e) { _log('onEnable error: '+e); } }")
        } finally {
            duktape.close()
        }
    }
}
