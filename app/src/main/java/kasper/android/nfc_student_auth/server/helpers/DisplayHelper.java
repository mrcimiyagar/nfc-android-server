package kasper.android.nfc_student_auth.server.helpers;

import kasper.android.nfc_student_auth.server.core.Core;

public class DisplayHelper {
    public float dpToPx(float dp) {
        return Core.getInstance().getResources().getDisplayMetrics().density * dp;
    }
}
