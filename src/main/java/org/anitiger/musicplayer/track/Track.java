package org.anitiger.musicplayer.track;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serial;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Track implements Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat sdfForReleaseDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfForAddedAt = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    private static long globalTrackId;
    private long trackId;
    private String trackTitle;
    private String trackAuthors;
    private long trackDuration;
    private String genre;
    private String trackReleaseAlbum;
    private Date trackReleaseDate;
    private Date trackAddedAt;

    public Track() throws ParseException {
        this.trackId = globalTrackId++;
        this.trackTitle = "";
        this.trackAuthors = "";
        this.trackDuration = 0;
        this.genre = "";
        this.trackReleaseAlbum = "";
        this.trackReleaseDate = sdfForReleaseDate.get2DigitYearStart();
        this.trackAddedAt = sdfForAddedAt.parse(sdfForAddedAt.format(new Date()));
    }

    public Track(String trackTitle, String trackAuthors, long trackDuration, String genre, String trackReleaseAlbum, String trackReleaseDate, String trackAddedAt) throws ParseException {
        this.trackId = globalTrackId++;
        this.trackTitle = trackTitle;
        this.trackAuthors = trackAuthors;
        this.trackDuration = trackDuration;
        this.genre = genre;
        this.trackReleaseAlbum = trackReleaseAlbum;
        this.trackReleaseDate = sdfForReleaseDate.parse(trackReleaseDate);
        this.trackAddedAt = sdfForAddedAt.parse(sdfForAddedAt.format(new Date()));
    }

    public long getTrackId() {
        return this.trackId;
    }

    public String getTrackTitle() {
        return this.trackTitle;
    }

    public String getTrackAuthors() {
        return this.trackAuthors;
    }

    public long getTrackDuration() {
        return this.trackDuration;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getTrackReleaseAlbum() {
        return this.trackReleaseAlbum;
    }

    public Date getTrackReleaseDate() {
        return this.trackReleaseDate;
    }

    public Date getTrackAddedAt() {
        return this.trackAddedAt;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws java.io.IOException {
        out.writeLong(trackId);
        out.writeUTF(trackTitle);
        out.writeUTF(trackAuthors);
        out.writeLong(trackDuration);
        out.writeUTF(genre);
        out.writeUTF(trackReleaseAlbum);
        out.writeUTF(sdfForReleaseDate.format(trackReleaseDate));
        out.writeUTF(sdfForAddedAt.format(trackAddedAt));
    }

    @Override
    public void readExternal(ObjectInput in) throws java.io.IOException {
        long idBuffer = in.readLong();
        if (idBuffer <  globalTrackId) {
            this.trackId = globalTrackId++;
        } else {
            this.trackId = idBuffer;
        }
        this.trackTitle = in.readUTF();
        this.trackAuthors = in.readUTF();
        long durationBuffer = in.readLong();
        if (durationBuffer < 0) {
            throw new RuntimeException("Track duration is negative");
        }
        this.trackDuration = durationBuffer;
        this.genre = in.readUTF();
        this.trackReleaseAlbum = in.readUTF();
        try {
            this.trackReleaseDate = sdfForReleaseDate.parse(in.readUTF());
            this.trackAddedAt = sdfForAddedAt.parse(in.readUTF());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
