package kasper.android.nfc_student_auth.server.models.memory;

/**
 * Created by keyhan1376 on 12/1/2017.
 */

public class Student {

    private String studentId;
    private String password;
    private String checkArr;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }
}
