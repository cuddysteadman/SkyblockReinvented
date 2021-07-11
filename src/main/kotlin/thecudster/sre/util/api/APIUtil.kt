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
package thecudster.sre.util.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.HttpResponse
import org.apache.http.HttpVersion
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.protocol.HttpContext
import thecudster.sre.SkyblockReinvented
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URISyntaxException
import java.net.URL
import java.util.*

object APIUtil {
    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author My-Name-Is-Jeff
     * @author Sychic
     */
    var client =
        HttpClients.custom().setUserAgent("SkyblockReinvented/" + SkyblockReinvented.VERSION).addInterceptorFirst(
            HttpRequestInterceptor { request: HttpRequest, context: HttpContext? ->
                if (!request.containsHeader("Pragma")) request.addHeader("Pragma", "no-cache")
                if (!request.containsHeader("Cache-Control")) request.addHeader("Cache-Control", "no-cache")
            }).build()

    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author My-Name-Is-Jeff
     * @author Sychic
     */
    @JvmStatic
    fun getJSONResponse(urlString: String): JsonObject {
        val player: EntityPlayer = Minecraft.getMinecraft().thePlayer
        try {
            val request = HttpGet(URL(urlString).toURI())
            request.protocolVersion = HttpVersion.HTTP_1_1
            val response: HttpResponse = client.execute(request)
            val entity = response.entity
            if (response.statusLine.statusCode == 200) {
                val `in` = BufferedReader(InputStreamReader(entity.content))
                var input: String?
                val r = StringBuilder()
                while (`in`.readLine().also { input = it } != null) {
                    r.append(input)
                }
                `in`.close()
                val gson = Gson()
                return gson.fromJson(r.toString(), JsonObject::class.java)
            } else {
                if (urlString.startsWith("https://api.hypixel.net/") || urlString.startsWith("https://sky.shiiyu.moe/api/v2/")) {
                    val errorStream = entity.content
                    Scanner(errorStream).use { scanner ->
                        scanner.useDelimiter("\\Z")
                        val error = scanner.next()
                        if (error.startsWith("{")) {
                            val gson = Gson()
                            return gson.fromJson(error, JsonObject::class.java)
                        }
                    }
                } else if (urlString.startsWith("https://api.mojang.com/users/profiles/minecraft/") && response.statusLine.statusCode == 204) {
                    player.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "Failed with reason: Player does not exist."))
                } else {
                    player.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "Request failed. HTTP Error Code: " + response.statusLine.statusCode))
                }
            }
        } catch (ex: IOException) {
            player.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "An error has occured. See logs for more details."))
            ex.printStackTrace()
        } catch (ex: URISyntaxException) {
            player.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "An error has occured. See logs for more details."))
            ex.printStackTrace()
        }
        return JsonObject()
    }

    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author My-Name-Is-Jeff
     * @author Sychic
     */
    @JvmStatic
    fun getLatestProfileID(UUID: String, key: String): String? {
        val player: EntityPlayer = Minecraft.getMinecraft().thePlayer

        // Get profiles
        println("Fetching profiles...")
        val profilesResponse = getJSONResponse("https://api.hypixel.net/skyblock/profiles?uuid=$UUID&key=$key")
        if (!profilesResponse["success"].asBoolean) {
            val reason = profilesResponse["cause"].asString
            player.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "Failed with reason: " + reason))
            return null
        }
        if (profilesResponse["profiles"].isJsonNull) {
            player.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "This player doesn't appear to have played SkyBlock."))
            return null
        }

        // Loop through profiles to find latest
        println("Looping through profiles...")
        var latestProfile = ""
        var latestSave: Long = 0
        val profilesArray = profilesResponse["profiles"].asJsonArray
        for (profile in profilesArray) {
            val profileJSON = profile.asJsonObject
            var profileLastSave: Long = 1
            if (profileJSON["members"].asJsonObject[UUID].asJsonObject.has("last_save")) {
                profileLastSave = profileJSON["members"].asJsonObject[UUID].asJsonObject["last_save"].asLong
            }
            if (profileLastSave > latestSave) {
                latestProfile = profileJSON["profile_id"].asString
                latestSave = profileLastSave
            }
        }
        return latestProfile
    }

    @JvmStatic
    fun getUUID(username: String): String {
        val uuidResponse = getJSONResponse("https://api.mojang.com/users/profiles/minecraft/$username")
        return uuidResponse["id"].asString
    }
}