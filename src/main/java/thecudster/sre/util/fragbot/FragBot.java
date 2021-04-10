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
