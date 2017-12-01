package kasper.android.nfc_student_auth.server.helpers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kasper.android.nfc_student_auth.server.models.database.Student;
import kasper.android.nfc_student_auth.server.models.database.Students;
import kasper.android.nfc_student_auth.server.utils.PersianCalendar;

/**
 * Created by keyhan1376 on 12/1/2017.
 */

public class DatabaseHelper {

    public DatabaseHelper(Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(Students.class).count() == 0) {
            realm.beginTransaction();
            Students students = realm.createObject(Students.class);
            Student sampleStudent = realm.createObject(Student.class);
            sampleStudent.setStudentId("123456789");
            sampleStudent.setPassword("fs654g31b65s4e98ew7rt");
            sampleStudent.setCheckArr("0000000");
            students.getStudents().add(sampleStudent);
            realm.commitTransaction();
        }
        realm.close();
    }

    public List<kasper.android.nfc_student_auth.server.models.memory.Student> getStudents() {
        Realm realm = Realm.getDefaultInstance();
        List<kasper.android.nfc_student_auth.server.models.memory.Student> mStudents = new ArrayList<>();
        for (Student dStudent : realm.where(Students.class).findFirst().getStudents()) {
            kasper.android.nfc_student_auth.server.models.memory.Student mStudent =
                    new kasper.android.nfc_student_auth.server.models.memory.Student();
            mStudent.setStudentId(dStudent.getStudentId());
            mStudent.setPassword(dStudent.getPassword());
            mStudent.setCheckArr(dStudent.getCheckArr());
            mStudents.add(mStudent);
        }
        realm.close();
        return mStudents;
    }

    public void checkStudent(String studentId, String password)
            throws StudentAlreadyCheckedException, StudentNotFoundException
            , InvalidStudentPasswordException {
        Realm realm = Realm.getDefaultInstance();

        Student student = realm.where(Students.class).findFirst().getStudents().where().equalTo("studentId", studentId).findFirst();

        if (student != null) {
            if (student.getPassword().equals(password)) {
                realm.beginTransaction();
                String checkArr = student.getCheckArr();
                PersianCalendar persianCalendar = new PersianCalendar();
                int dayIndex = persianCalendar.getPersianWeekDayIndex();
                if (checkArr.charAt(dayIndex) == '0') {
                    StringBuilder checkArrStr = new StringBuilder(checkArr);
                    checkArrStr.setCharAt(dayIndex, '1');
                    checkArr = checkArrStr.toString();
                    student.setCheckArr(checkArr);
                    realm.commitTransaction();
                    realm.close();
                } else {
                    realm.commitTransaction();
                    realm.close();
                    throw new StudentAlreadyCheckedException();
                }
            }
            else {
                realm.close();
                throw new InvalidStudentPasswordException();
            }
        }
        else {
            realm.close();
            throw new StudentNotFoundException();
        }
    }

    public kasper.android.nfc_student_auth.server.models.memory.Student getStudentById(String studentId)
            throws StudentNotFoundException {
        Realm realm = Realm.getDefaultInstance();

        Student dStudent = realm.where(Students.class).findFirst().getStudents().where()
                .equalTo("studentId", studentId).findFirst();

        if (dStudent != null) {

            kasper.android.nfc_student_auth.server.models.memory.Student mStudent =
                    new kasper.android.nfc_student_auth.server.models.memory.Student();
            mStudent.setStudentId(dStudent.getStudentId());
            mStudent.setPassword(dStudent.getPassword());
            mStudent.setCheckArr(dStudent.getCheckArr());

            realm.close();

            return mStudent;
        }
        else {
            realm.close();
            throw new StudentNotFoundException();
        }
    }

    public class StudentAlreadyCheckedException extends Exception {

    }

    public class InvalidStudentPasswordException extends Exception {

    }

    public class StudentNotFoundException extends Exception {

    }
}
