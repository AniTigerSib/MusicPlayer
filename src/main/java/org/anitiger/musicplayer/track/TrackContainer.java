package org.anitiger.musicplayer.track;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serial;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class TrackContainer implements Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected ArrayList<Track> tracks;

    public TrackContainer() {
        tracks = new ArrayList<>();
    }
    public abstract void addTrack(Track track) throws ParseException;
    public Track getTrackById(long id) {
        for (Track track : tracks) {
            if (track.getTrackId() == id) {
                return track;
            }
        }
        return null;
    }
    public Track getTrackByTitle(String title) {
        for (Track track : tracks) {
            if (track.getTrackTitle().equals(title)) {
                return track;
            }
        }
        return null;
    }
    public Track removeTrackById(long id) {
        Track trackToRemove = getTrackById(id);
        tracks.remove(trackToRemove);
        return trackToRemove;
    }
    public Track removeTrackByTitle(String title) {
        Track trackToRemove = getTrackByTitle(title);
        tracks.remove(trackToRemove);
        return trackToRemove;
    }
    public void deleteTrackById(long id) {
        Track trackToDelete = removeTrackById(id);
    }
    public void deleteTrackByTitle(String title) {
        Track trackToDelete = removeTrackByTitle(title);
    }
    public void clear() {
        tracks.clear();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws java.io.IOException {
        out.writeInt(tracks.size());
        for (Track track : tracks) {
            track.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws java.io.IOException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Track track;
            try {
                track = new Track();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            track.readExternal(in);
            tracks.add(track);
        }
    }
}
