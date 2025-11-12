package com.example.modmanager

/**
 * Simple Mod API interface — mod authors implement this
 * and the DexModLoader will instantiate it reflectively.
 */
interface Mod {
    /**
     * Called by loader to enable the mod.
     */
    fun onEnable(ctx: ModContext)

    /**
     * Called by loader on shutdown/unload.
     */
    fun onDisable()
}

interface ModContext {
    fun log(msg: String)
    fun registerItem(id: String, json: String)
    // expand as needed
}
