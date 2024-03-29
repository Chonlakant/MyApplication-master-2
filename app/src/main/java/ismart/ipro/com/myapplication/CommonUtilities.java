package ismart.ipro.com.myapplication;

/**
 * Created by ELMTRIX on 28/3/2559.
 */
import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    // give your server registration url here
    public static final String SERVER_URL = "http://192.168.1.104:8080/gcm_server_php/register.php";

    // Google project id
    public static final String SENDER_ID = "423097242723";

    /**
     * Tag used on log messages.
     */
    public static final String TAG = "GCM TEST";

    public static final String DISPLAY_MESSAGE_ACTION =
            "ismart.ipro.com.myapplication.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}

