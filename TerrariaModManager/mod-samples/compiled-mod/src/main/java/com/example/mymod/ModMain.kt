package com.example.mymod

import com.example.modmanager.Mod
import com.example.modmanager.ModContext

/**
 * NOTE: In practice, mod authors need the Mod interface compiled into a small jar to compile against.
 * For this sample, assume the same Mod/ModContext lives in a published mod-api.jar.
 */
class ModMain : Mod {
    private var ctxRef: ModContext? = null

    override fun onEnable(ctx: ModContext) {
        ctxRef = ctx
        ctx.log("SampleMod enabled (compiled mod)")
        ctx.registerItem("samplemod:flower", "{\"name\":\"Flower Sword\",\"damage\":42}")
    }

    override fun onDisable() {
        ctxRef?.log("SampleMod disabled")
    }
}
