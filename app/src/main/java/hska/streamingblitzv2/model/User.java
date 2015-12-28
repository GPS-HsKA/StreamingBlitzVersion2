package hska.streamingblitzv2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwort='" + passwort + '\'' +
                ", mail='" + mail + '\'' +
                ", einstellungen=" + einstellungen +
                '}';
    }

    public User(String username, String passwort, String mail, Einstellungen einstellungen) {
        this.username = username;
        this.passwort = passwort;
        this.mail = mail;
        this.einstellungen = einstellungen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    private String username;

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswort() {
        return passwort;
    }

    private String passwort;

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    private String mail;

    public Einstellungen getEinstellungen() {
        return einstellungen;
    }

    public void setEinstellungen(Einstellungen einstellungen) {
        this.einstellungen = einstellungen;
    }

    private Einstellungen einstellungen;



    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(passwort);
        dest.writeString(mail);
        dest.writeParcelable(einstellungen, flags);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        id = in.readLong();
        username = in.readString();
        passwort = in.readString();
        mail = in.readString();
        einstellungen = in.readParcelable(Einstellungen.class.getClassLoader());
    }
}
