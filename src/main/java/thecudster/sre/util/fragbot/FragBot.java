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

package thecudster.sre.util.fragbot;

import java.util.List;

public class FragBot {

  private String username;
  private String type;
  private String status;
  private String uuid;
  private boolean our;
  private List<String> queue;

  public String getUsername() {
    return username;
  }

  public FragBot setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getType() {
    return type;
  }

  public FragBot setType(String type) {
    this.type = type;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public FragBot setStatus(String status) {
    this.status = status;
    return this;
  }

  public String getUuid() {
    return uuid;
  }

  public FragBot setUuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public boolean isOur() {
    return our;
  }

  public FragBot setOur(boolean our) {
    this.our = our;
    return this;
  }

  public List<String> getQueue() {
    return queue;
  }

  public FragBot setQueue(List<String> queue) {
    this.queue = queue;
    return this;
  }

}
