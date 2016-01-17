package hska.streamingblitzv2.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.SearchView;


public class Content implements Parcelable {

    public Content() {

    }

    public Content(Long id, String name, String genre, String laufzeit, Integer netflix, Integer amazon, Integer maxdome, Integer snap, String imdbScore, String jahr, String imageUrl) {
        this._id = id;
        this.name = name;
        this.genre = genre;
        this.laufzeit = laufzeit;
        this.netflix = netflix;
        this.amazon = amazon;
        this.maxdome = maxdome;
        this.snap = snap;
        this.imdbScore = imdbScore;
        this.jahr = jahr;
        this.imageUrl = imageUrl;
    }

    private long _id;
    private String name;
    private String genre;
    private String laufzeit;
    private Integer netflix;
    private Integer amazon;
    private Integer maxdome;
    private Integer snap;
    private String imdbScore;
    private String jahr;
    private String imageUrl;

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(String laufzeit) {
        this.laufzeit = laufzeit;
    }

    public void setNetflix(Integer netflix) {
        this.netflix = netflix;
    }

    public void setAmazon(Integer amazon) {
        this.amazon = amazon;
    }

    public void setMaxdome(Integer maxdome) {
        this.maxdome = maxdome;
    }

    public void setSnap(Integer snap) {
        this.snap = snap;
    }

    public Integer getNetflix() {
        return netflix;
    }

    public Integer getAmazon() {
        return amazon;
    }

    public Integer getMaxdome() {
        return maxdome;
    }

    public Integer getSnap() {
        return snap;
    }
    public String getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(String imdbScore) {
        this.imdbScore = imdbScore;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getJahr() {
        return jahr;
    }

    public void setJahr(String jahr) {
        this.jahr = jahr;
    }

    public String getImage() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + _id +
                "image=" + imageUrl +
                ", Name='" + name + '\'' +
                ", Genre='" + genre + '\'' +
                ", Laufzeit='" + laufzeit + '\'' +
                ", Netflix='" + netflix + '\'' +
                ", Amazon=" + amazon + '\'' +
                ", Maxdome=" + maxdome + '\'' +
                ", Snap=" + snap + '\'' +
                ", ImdbScore=" + imdbScore +
                ", Jahr=" + jahr +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(name);
        dest.writeString(genre);
        dest.writeString(laufzeit);
        dest.writeString(imdbScore);
        dest.writeInt(netflix);
        dest.writeInt(amazon);
        dest.writeInt(maxdome);
        dest.writeInt(snap);
        dest.writeString(jahr);
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Content> CREATOR
            = new Parcelable.Creator<Content>() {

        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    private Content(Parcel in) {
        _id = in.readLong();
        name = in.readString();
        genre = in.readString();
        laufzeit = in.readString();
        imdbScore = in.readString();
        netflix = in.readInt();
        amazon = in.readInt();
        maxdome = in.readInt();
        snap = in.readInt();
        jahr = in.readString();
        imageUrl = in.readString();
    }
}