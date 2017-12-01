package kasper.android.nfc_student_auth.server.core;

import android.app.Application;

import kasper.android.nfc_student_auth.server.helpers.DatabaseHelper;
import kasper.android.nfc_student_auth.server.helpers.DisplayHelper;

/**
 * Created by keyhan1376 on 12/1/2017.
 */

public class Core extends Application {

    private static Core instance;
    public static Core getInstance() {
        return instance;
    }

    private DatabaseHelper databaseHelper;
    public DatabaseHelper getDatabaseHelper() {
        return this.databaseHelper;
    }

    private DisplayHelper displayHelper;
    public DisplayHelper getDisplayHelper() {
        return this.displayHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.databaseHelper = new DatabaseHelper(this);
        this.displayHelper = new DisplayHelper();
    }
}
