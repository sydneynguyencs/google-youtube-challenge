package com.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** A class used to represent a Playlist */
class VideoPlaylist implements Comparable< VideoPlaylist >{
    private List<Video> playlist;
    private String name;

    public VideoPlaylist(String name) {
        this.playlist = new ArrayList<>();
        this.name = name;
    }

    public List<Video> getVideoList() {
        return playlist;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(VideoPlaylist videoPlaylist) {
        return this.getName().compareTo(videoPlaylist.getName());
    }
}
