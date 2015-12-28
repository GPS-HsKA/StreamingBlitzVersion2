package hska.streamingblitzv2.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class Content implements Parcelable {

    public Content() {

    }

    public Content(String name, String genre, String laufzeit, Integer serie, Integer film, Double imdbScore, Uri image) {
        this.name = name;
        this.genre = genre;
        this.laufzeit = laufzeit;
        this.serie = serie;
        this.film = film;
        this.imdbScore = imdbScore;
        this.image = image;
    }

    private long id;
    private Uri image;
    private String name;
    private String genre;
    private String laufzeit;
    private Integer serie;
    private Integer film;
    private Double imdbScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getFilm() {
        return film;
    }

    public void setFilm(Integer film) {
        this.film = film;
    }

    public Double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    public Uri getBild() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                "image=" + image +
                ", Name='" + name + '\'' +
                ", Genre='" + genre + '\'' +
                ", Laufzeit='" + laufzeit + '\'' +
                ", Serie='" + serie + '\'' +
                ", Film=" + film + '\'' +
                ", ImdbScore=" + imdbScore +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(genre);
        dest.writeString(image.getPath());
        dest.writeString(laufzeit);
        dest.writeDouble(imdbScore);
        dest.writeInt(serie);
        dest.writeInt(film);
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
        id = in.readLong();
        name = in.readString();
        genre = in.readString();
        image = Uri.parse(in.readString());
        laufzeit = in.readString();
        imdbScore = in.readDouble();
        film = in.readInt();
        serie = in.readInt();
    }
}