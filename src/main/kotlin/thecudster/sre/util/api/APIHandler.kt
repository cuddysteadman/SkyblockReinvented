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
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/*
 * Modified from Danker's Skyblock Mod under GNU General Public License.
 * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
 * @author bowser0000
 */
object APIHandler {
    @JvmStatic
    fun getResponse(urlString: String): JsonObject {
        val player: EntityPlayer = Minecraft.getMinecraft().thePlayer
        try {
            val url = URL(urlString)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                val `in` = BufferedReader(InputStreamReader(conn.inputStream))
                var input: String?
                val response = StringBuilder()
                while (`in`.readLine().also { input = it } != null) {
                    response.append(input)
                }
                `in`.close()
                val gson = Gson()
                return gson.fromJson(response.toString(), JsonObject::class.java)
            } else {
                if (urlString.startsWith("https://api.hypixel.net/")) {
                    val errorStream = conn.errorStream
                    Scanner(errorStream).use { scanner ->
                        scanner.useDelimiter("\\Z")
                        val error = scanner.next()
                        val gson = Gson()
                        return gson.fromJson(error, JsonObject::class.java)
                    }
                } else if (urlString.startsWith("https://api.mojang.com/users/profiles/minecraft/") && conn.responseCode == 204) {
                    player.addChatMessage(ChatComponentText("Failed with reason: Player does not exist."))
                } else {
                    player.addChatMessage(ChatComponentText("Request failed. HTTP Error Code: " + conn.responseCode))
                }
            }
        } catch (ex: IOException) {
            player.addChatMessage(ChatComponentText("An error has occured. See logs for more details."))
            ex.printStackTrace()
        }
        return JsonObject()
    }
}