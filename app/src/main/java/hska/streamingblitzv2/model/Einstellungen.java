package hska.streamingblitzv2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Einstellungen implements Parcelable {

    public Einstellungen() {

    }

    public Einstellungen(Integer netflix, Integer amazonprime, Integer maxdome, Integer snap, User user) {
        this.netflix = netflix;
        this.amazonprime = amazonprime;
        this.maxdome = maxdome;
        this.snap = snap;
        this.user = user;
    }

    private long id;

    public Integer getNetflix() {
        return netflix;
    }

    public Integer getAmazonprime() {
        return amazonprime;
    }

    public Integer getMaxdome() {
        return maxdome;
    }

    public Integer getSnap() {
        return snap;
    }

    public User getUser() {
        return user;
    }

    public void setNetflix(Integer netflix) {
        this.netflix = netflix;
    }

    public void setAmazonprime(Integer amazonprime) {
        this.amazonprime = amazonprime;
    }

    public void setMaxdome(Integer maxdome) {
        this.maxdome = maxdome;
    }

    public void setSnap(Integer snap) {
        this.snap = snap;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Einstellungen{" +
                "id=" + id +
                ", netflix=" + netflix +
                ", amazonprime=" + amazonprime +
                ", maxdome=" + maxdome +
                ", snap=" + snap +
                ", user=" + user +
                '}';
    }

    private Integer netflix;
    private Integer amazonprime;
    private Integer maxdome;
    private Integer snap;
    private User user;

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(netflix);
        dest.writeInt(amazonprime);
        dest.writeInt(maxdome);
        dest.writeInt(snap);
        dest.writeParcelable(user, flags);
    }


    public static final Parcelable.Creator<Einstellungen> CREATOR
            = new Parcelable.Creator<Einstellungen>() {

        public Einstellungen createFromParcel(Parcel in) {
            return new Einstellungen(in);
        }

        public Einstellungen[] newArray(int size) {
            return new Einstellungen[size];
        }
    };

    private Einstellungen(Parcel in) {
        id = in.readLong();
        netflix = in.readInt();
        amazonprime = in.readInt();
        maxdome = in.readInt();
        snap = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
    }

}