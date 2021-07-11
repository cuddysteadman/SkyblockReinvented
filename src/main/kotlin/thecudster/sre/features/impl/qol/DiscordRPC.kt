/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 *  Copyright (C) 2021 theCudster
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package thecudster.sre.features.impl.qol

import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import com.jagrosh.discordipc.entities.User
import net.minecraft.client.Minecraft
import org.json.JSONObject
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils.sendMsg
import thecudster.sre.util.sbutil.CurrentLoc
import thecudster.sre.util.sbutil.ItemUtil.getDisplayName
import thecudster.sre.util.sbutil.stripControlCodes
import java.time.OffsetDateTime
import java.util.*

/**
 * Modified from SkyblockAddons under MIT License
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 * @author BiscuitDevelopment
 */
class DiscordRPC : IPCListener {
    private var client: IPCClient? = null
    private var startTimestamp: OffsetDateTime? = null
    private var updateTimer: Timer? = null
    private var connected = false
    fun start() {
        try {
            if (isActive) {
                return
            }
            startTimestamp = OffsetDateTime.now()
            client = IPCClient(APPLICATION_ID)
            client!!.setListener(this)
            try {
                client!!.connect()
            } catch (e: Exception) {
                println("Error while connecting to Discord RPC: " + e.message)
            }
        } catch (ex: Throwable) {
            println("Unexpected error with Discord RP.")
            ex.printStackTrace()
        }
    }

    fun stop() {
        if (isActive) {
            client!!.close()
            connected = false
        }
    }

    fun updatePresence() {
        val location = CurrentLoc.currentLoc ?: return
        when (SkyblockReinvented.config.discordMode) {
            0 -> {
            }
            1 -> state = Minecraft.getMinecraft().thePlayer.name
            2 -> {
                if (Minecraft.getMinecraft().thePlayer.heldItem != null) {
                    if (getDisplayName(Minecraft.getMinecraft().thePlayer.heldItem) != null) {
                        state = getDisplayName(Minecraft.getMinecraft().thePlayer.heldItem).stripControlCodes()
                    }
                } else {
                    state = "Not Holding an Item!"
                }
            }
            3 -> state = "Playing Skyblock"
            4 -> state = SkyblockReinvented.config.discordCustomText
        }
        val presence = RichPresence.Builder()
            .setState(state)
            .setDetails("In $location")
            .setLargeImage("logodiscord")
            .setStartTimestamp(startTimestamp)
            .build()
        client!!.sendRichPresence(presence)
    }

    val isActive: Boolean
        get() = client != null && connected

    override fun onActivityJoinRequest(client: IPCClient, secret: String, user: User) {
        sendMsg(user.name + " joined the party!")
    }

    override fun onReady(client: IPCClient) {
        connected = true
        updateTimer = Timer()
        updateTimer!!.schedule(object : TimerTask() {
            override fun run() {
                updatePresence()
            }
        }, 0, UPDATE_PERIOD)
    }

    override fun onClose(client: IPCClient, json: JSONObject) {
        this.client = null
        connected = false
        cancelTimer()
    }

    override fun onDisconnect(client: IPCClient, t: Throwable) {
        this.client = null
        connected = false
        cancelTimer()
    }

    private fun cancelTimer() {
        if (updateTimer != null) {
            updateTimer!!.cancel()
            updateTimer = null
        }
    }

    companion object {
        const val APPLICATION_ID = 830851684318183466L
        private const val UPDATE_PERIOD = 4200L
        var profile = ""
        @JvmField
        var state = "Playing Skyblock"
        var partySize = 1
        var partyMax = 5
    }
}