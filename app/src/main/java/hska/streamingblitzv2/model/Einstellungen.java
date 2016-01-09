package hska.streamingblitzv2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Einstellungen implements Parcelable {

    public Einstellungen() {

    }

    public Einstellungen(Boolean netflix, Boolean amazonprime, Boolean maxdome, Boolean snap, String user) {
        this.netflix = netflix;
        this.amazonprime = amazonprime;
        this.maxdome = maxdome;
        this.snap = snap;
        this.user = user;
    }

    private long id;

    public Boolean getNetflix() {
        return netflix;
    }

    public Boolean getAmazonprime() {
        return amazonprime;
    }

    public Boolean getMaxdome() {
        return maxdome;
    }

    public Boolean getSnap() {
        return snap;
    }

    public String getUser() {
        return user;
    }

    public void setNetflix(Boolean netflix) {
        this.netflix = netflix;
    }

    public void setAmazonprime(Boolean amazonprime) {
        this.amazonprime = amazonprime;
    }

    public void setMaxdome(Boolean maxdome) {
        this.maxdome = maxdome;
    }

    public void setSnap(Boolean snap) {
        this.snap = snap;
    }

    public void setUser(String user) {
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

    private Boolean netflix;
    private Boolean amazonprime;
    private Boolean maxdome;
    private Boolean snap;
    private String user;

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
        dest.writeByte((byte) (netflix ? 1 : 0));
        dest.writeByte((byte) (amazonprime ? 1 : 0));
        dest.writeByte((byte) (maxdome ? 1 : 0));
        dest.writeByte((byte) (snap ? 1 : 0));
        dest.writeString(user);
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
        netflix = in.readByte() != 0;
        amazonprime = in.readByte() != 0;
        maxdome = in.readByte() != 0;
        snap = in.readByte() != 0;
        user = in.readString();
    }

}