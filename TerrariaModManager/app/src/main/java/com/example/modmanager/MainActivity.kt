package com.example.modmanager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var tvInspect: TextView
    private lateinit var tvStatus: TextView

    private val pickApkLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val info = ApkInspector.inspectApk(this, it)
            tvInspect.text = info
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInspect = findViewById<Button>(R.id.btn_inspect)
        val btnLoadMod = findViewById<Button>(R.id.btn_load_mod)
        val btnScript = findViewById<Button>(R.id.btn_run_script_mod)
        tvInspect = findViewById(R.id.tv_inspect)
        tvStatus = findViewById(R.id.tv_status)

        btnInspect.setOnClickListener {
            // ask user to pick an APK file
            pickApkLauncher.launch("application/vnd.android.package-archive")
        }

        btnLoadMod.setOnClickListener {
            // load a prebuilt mod dex from assets/mods/samplemod.jar
            try {
                val modFile = Utils.copyAssetToFiles(this, "mods/samplemod.jar")
                val mod = DexModLoader.loadMod(this, modFile)
                tvStatus.text = "Loaded compiled mod instance: ${mod::class.java.name}"
            } catch (e: Exception) {
                e.printStackTrace()
                tvStatus.text = "Error loading compiled mod: ${e.message}"
            }
        }

        btnScript.setOnClickListener {
            try {
                val script = Utils.copyAssetToFiles(this, "mods/main.js")
                ScriptModLoader.runScriptMod(this, script) { log ->
                    runOnUiThread { tvStatus.append("\n$log") }
                }
                tvStatus.text = "Script mod executed (check logs)"
            } catch (e: Exception) {
                e.printStackTrace()
                tvStatus.text = "Error running script mod: ${e.message}"
            }
        }
    }
}
