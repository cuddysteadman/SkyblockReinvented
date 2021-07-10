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
package thecudster.sre.features.impl.dungeons

import com.google.gson.JsonIOException
import thecudster.sre.util.api.APIUtil.getJSONResponse
import java.util.function.Consumer
import java.net.MalformedURLException
import thecudster.sre.features.impl.dungeons.FragStatus
import java.util.*
import java.util.stream.Collectors

object FragStatus {
    @get:Throws(JsonIOException::class)
    val bots: List<FragBot>
        get() {
            val bots: MutableList<FragBot> = ArrayList()
            val `object` = getJSONResponse("https://fragrunner.me:3001/fragbots")
            println(`object`)
            val jsonArray = `object`.getAsJsonArray("bots")
            for (i in 0 until jsonArray.size()) {
                val bot = jsonArray[i].asJsonObject
                val array = bot.getAsJsonArray("queue")
                val queue: MutableList<String> = ArrayList()
                for (a in 0 until array.size()) {
                    queue.add(array.toString())
                }
                bots.add(
                    FragBot().setType("group").setStatus(bot["status"].asString).setUsername(bot["name"].asString)
                        .setUuid(bot["uuid"].asString).setQueue(queue)
                )
                println("Bots List: ")
                bots.forEach(Consumer { botObj: FragBot -> println(botObj.username + " : " + botObj.type + " : " + botObj.status) })
            }
            return bots
        }

    @JvmStatic
    @Throws(MalformedURLException::class, JsonIOException::class)
    fun getBestBot(amount: Int): List<FragBot> {
        var amount = amount
        var bots = bots
        bots = bots.stream().filter { bot: FragBot -> bot.status == "online" }.collect(Collectors.toList())
        if (bots.size == 0) return ArrayList()
        if (amount > bots.size) {
            amount = bots.size
        }
        val out: MutableList<FragBot> = ArrayList()
        var i = 0
        while (i < amount) {
            val random = Random().nextInt(bots.size)
            val bot = bots[random]
            if (out.contains(bot)) {
                i--
                i++
                continue
            }
            out.add(bot)
            i++
        }
        return out
    }
}