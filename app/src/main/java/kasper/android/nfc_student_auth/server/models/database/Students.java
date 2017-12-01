package kasper.android.nfc_student_auth.server.models.database;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by keyhan1376 on 12/1/2017.
 */

public class Students extends RealmObject {

    private RealmList<Student> students;

    public RealmList<Student> getStudents() {
        return students;
    }

    public void setStudents(RealmList<Student> students) {
        this.students = students;
    }
}
