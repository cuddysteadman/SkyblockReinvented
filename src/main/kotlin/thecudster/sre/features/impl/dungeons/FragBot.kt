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

class FragBot {
    var username: String? = null
        private set
    var type: String? = null
        private set
    var status: String? = null
        private set
    var uuid: String? = null
        private set
    var isOur = false
        private set
    var queue: List<String>? = null
        private set

    fun setUsername(username: String?): FragBot {
        this.username = username
        return this
    }

    fun setType(type: String?): FragBot {
        this.type = type
        return this
    }

    fun setStatus(status: String?): FragBot {
        this.status = status
        return this
    }

    fun setUuid(uuid: String?): FragBot {
        this.uuid = uuid
        return this
    }

    fun setOur(our: Boolean): FragBot {
        isOur = our
        return this
    }

    fun setQueue(queue: List<String>?): FragBot {
        this.queue = queue
        return this
    }
}