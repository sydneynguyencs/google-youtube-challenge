package com.google;

import java.util.*;

public class VideoPlayer {

    private final VideoLibrary videoLibrary;
    private Video current;
    private List<VideoPlaylist> allPlaylists;
    private List<Video> nonFlagVideos;

    public VideoPlayer() {
        this.videoLibrary = new VideoLibrary();
        this.allPlaylists = new ArrayList<>();
        this.nonFlagVideos = videoLibrary.getVideos();
    }

    public void numberOfVideos() {
        System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
    }

    private String getVideoData(Video video) {
        String data = video.getTitle() + " (" + video.getVideoId() + ") " + video.getTags().toString().replace(",", "");
        if (video.isFlagged()) {
            data += " - FLAGGED (reason: " + video.getFlagReason() + ")";
        }
        return data;
    }

    public void showAllVideos() {
        System.out.println("Here's a list of all available videos:");
        for (Video video : videoLibrary.getVideos()) {
            System.out.println(getVideoData(video));
        }
    }

    public void playVideo(String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if (video != null) {
            if (!video.isFlagged()) {
                if (current != null) {
                    stopVideo();
                }
                current = video;
                current.pause(false);
                System.out.println("Playing video: " + current.getTitle());
            } else {
                System.out.println("Cannot play video: Video is currently flagged (reason: " + video.getFlagReason() + ")");
            }
        } else {
            System.out.println("Cannot play video: Video does not exist");
        }

    }

    public void stopVideo() {
        if (current != null) {
            System.out.println("Stopping video: " + current.getTitle());
            current.pause(false);
            current = null;
        } else {
            System.out.println("Cannot stop video: No video is currently playing");
        }
    }

    public void playRandomVideo() {
        Random ran = new Random();
        if (nonFlagVideos.size() > 0) {
            int randomInt = ran.nextInt(nonFlagVideos.size());
            Video video = nonFlagVideos.get(randomInt);
            playVideo(video.getVideoId());
        } else {
            System.out.println("No videos available");
        }
    }

    public void pauseVideo() {
        if (current != null) {
            if (current.isPaused()) {
                System.out.println("Video already paused: " + current.getTitle());
            } else {
                System.out.println("Pausing video: " + current.getTitle());
                current.pause(true);
            }
        } else {
            System.out.println("Cannot pause video: No video is currently playing");
        }
    }

    public void continueVideo() {
        if (current != null) {
            if (current.isPaused()) {
                System.out.println("Continuing video: Amazing Cats");
                current.pause(false);
            } else {
                System.out.println("Cannot continue video: Video is not paused");
            }
        } else {
            System.out.println("Cannot continue video: No video is currently playing");
        }
    }

    public void showPlaying() {
        String playing;
        if (current != null) {
            playing = "Currently playing: " + getVideoData(current);
            if (current.isPaused()) {
                playing += " - PAUSED";
            }
        } else {
            playing = "No video is currently playing";
        }
        System.out.println(playing);
    }

    private VideoPlaylist playlistExists(String playlistName) {
        VideoPlaylist exists = null;
        for (VideoPlaylist v : allPlaylists) {
            if (v.getName().equalsIgnoreCase(playlistName)) {
                exists = v;
                break;
            }
        }
        return exists;
    }

    public void createPlaylist(String playlistName) {
        if (playlistExists(playlistName) == null) {
            VideoPlaylist videoPlaylist = new VideoPlaylist((playlistName));
            allPlaylists.add(videoPlaylist);
            System.out.println("Successfully created new playlist: " + playlistName);
        } else {
            System.out.println("Cannot create playlist: A playlist with the same name already exists");
        }
    }

    public void addVideoToPlaylist(String playlistName, String videoId) {
        VideoPlaylist videoPlaylist = playlistExists(playlistName);
        if (videoPlaylist != null) {
            Video video = videoLibrary.getVideo(videoId);
            if (video != null) {
                if (!videoPlaylist.getVideoList().contains(video)) {
                    if (!video.isFlagged()) {
                        videoPlaylist.getVideoList().add(video);
                        System.out.println("Added video to " + playlistName + ": " + video.getTitle());
                    } else {
                        System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " + video.getFlagReason() + ")");
                    }
                } else {
                    System.out.println("Cannot add video to " + playlistName + ": Video already added");
                }
            } else {
                System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
            }
        } else {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }
    }

    public void showAllPlaylists() {
        if (allPlaylists.size() != 0) {
            System.out.println("Showing all playlists:");
            Collections.sort(allPlaylists);
            for (VideoPlaylist v : allPlaylists) {
                System.out.println(v.getName());
            }
        } else {
            System.out.println("No playlists exist yet");
        }
    }

    public void showPlaylist(String playlistName) {
        VideoPlaylist videoPlaylist = playlistExists(playlistName);
        if (videoPlaylist != null) {
            System.out.println("Showing playlist: " + playlistName);
            if (videoPlaylist.getVideoList().size() != 0) {
                for (Video video : videoPlaylist.getVideoList()) {
                    System.out.println(getVideoData(video));
                }
            } else {
                System.out.println("No videos here yet");
            }
        } else {
            System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
        }
    }

    public void removeFromPlaylist(String playlistName, String videoId) {
        VideoPlaylist videoPlaylist = playlistExists(playlistName);
        if (videoPlaylist != null) {
            Video video = videoLibrary.getVideo(videoId);
            if (video != null) {
                // check if videoPlaylist contains video
                if (videoPlaylist.getVideoList().contains(video)) {
                    videoPlaylist.getVideoList().remove(video);
                    System.out.println("Removed video from " + playlistName + ": " + video.getTitle());
                } else {
                    System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
                }
            } else {
                System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
            }
        } else {
            System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
        }
    }

    public void clearPlaylist(String playlistName) {
        VideoPlaylist videoPlaylist = playlistExists(playlistName);
        if (videoPlaylist != null) {
            videoPlaylist.getVideoList().clear();
            System.out.println("Successfully removed all videos from " + playlistName);
        } else {
            System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
        }
    }

    public void deletePlaylist(String playlistName) {
        VideoPlaylist videoPlaylist = playlistExists(playlistName);
        if (videoPlaylist != null) {
            allPlaylists.remove(videoPlaylist);
            System.out.println("Deleted playlist: " + playlistName);
        } else {
            System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
        }
    }

    private void showSearchHits(List<Video> searchHits, String searchTerm) {
        if (searchHits.size() != 0) {
            System.out.println("Here are the results for " + searchTerm + ":");
            int counter = 1;
            for (Video video : searchHits) {
                System.out.println(counter + ") " + getVideoData(video));
                counter++;
            }
            System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
            System.out.println("If your answer is not a valid number, we will assume it's a no.");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            int index = input.charAt(0) - 48;
            if (index < videoLibrary.getVideos().size() && index >= 0) {
                playVideo(searchHits.get(index - 1).getVideoId());
            }
        } else {
            System.out.println("No search results for " + searchTerm);
        }
    }

    public void searchVideos(String searchTerm) {
        List<Video> searchHits = new ArrayList<>();
        for (Video video : videoLibrary.getVideos()) {
            String currTitle = video.getTitle().toLowerCase();
            if (currTitle.contains(searchTerm.toLowerCase())) {
                if (!video.isFlagged()) {
                    searchHits.add(video);
                }
            }
        }
        showSearchHits(searchHits, searchTerm);
    }

    public void searchVideosWithTag(String videoTag) {
        List<Video> searchHits = new ArrayList<>();
        for (Video video : videoLibrary.getVideos()) {
            if (video.getTags().contains(videoTag.toLowerCase())) {
                if (!video.isFlagged()) {
                    searchHits.add(video);
                }
            }
        }
        showSearchHits(searchHits, videoTag);
    }

    public void flagVideo(String videoId) {
        String reason = "Not supplied";
        flagVideo(videoId, reason);
    }

    public void flagVideo(String videoId, String reason) {
        Video video = videoLibrary.getVideo(videoId);
        if (video != null) {
            if (!video.isFlagged()) { // edge case: video.getFlagReason() = null ?!
                video.flag(true, reason);
                nonFlagVideos.remove(video);
                if (current == video) {
                    stopVideo();
                }
                System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: " + reason + ")");
            } else {
                System.out.println("Cannot flag video: Video is already flagged");
            }
        } else {
            System.out.println("Cannot flag video: Video does not exist");
        }
    }

    public void allowVideo(String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if (video != null) {
            if (video.isFlagged()) {
                video.flag(false, null);
                System.out.println("Successfully removed flag from video: " + video.getTitle());
            } else {
                System.out.println("Cannot remove flag from video: Video is not flagged");
            }
        } else {
            System.out.println("Cannot remove flag from video: Video does not exist");
        }
    }
}