package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video implements Comparable< Video >{

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean paused = false;
  private boolean flagged = false;
  private String flagReason;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.flagReason = null;
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  public boolean isPaused() {
    return paused;
  }

  public void pause(boolean bool) {
    paused = bool;
  }

  public void flag(boolean bool, String flagReason) {
    this.flagged = bool;
    this.flagReason = flagReason;
  }

  public boolean isFlagged() {
    return flagged;
  }

  @Override
  public int compareTo(Video video) {
    return this.getVideoId().compareTo(video.getVideoId());
  }

  public String getFlagReason() {
    return flagReason;
  }
}
