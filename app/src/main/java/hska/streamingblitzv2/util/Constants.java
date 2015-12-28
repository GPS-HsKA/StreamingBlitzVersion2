package hska.streamingblitzv2.util;

public class Constants {

    //Parcel keys
    public static final String PARCEL_USER = "hska.streamingblitzv2.model.USER";
    public static final String PARCEL_CONTENT = "hska.streamingblitzv2.model.CONTENT";
    public static final String PARCEL_EINSTELLUNGEN = "hska.streamingblitzv2.model.EINSTELLUNGEN";

    //Activity request codes
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_IMAGE_CROP = 1;
    public static final int REQUEST_IMAGE_CHOOSE = 2;

    //Dialogs
    public static final String DIALOG_IMAGE_TITLE = "Add photo!";
    public static final String DIALOG_CAPTURE_IMAGE = "Take picture";
    public static final String DIALOG_CHOOSE_IMAGE = "Choose from gallery";
    public static final CharSequence[] DIALOG_IMAGE_OPTIONS = {DIALOG_CAPTURE_IMAGE, DIALOG_CHOOSE_IMAGE, "Cancel"};
    public static final String DIALOG_DELETE_TITLE = "Please confirm!";
    public static final String DIALOG_DELETE_MESSAGE = "Are you sure you want to delete this contact?";
    public static final String DIALOG_DELETE_POSITIVE = "Delete";
    public static final String DIALOG_DELETE_NEGATIVE = "Cancel";

}