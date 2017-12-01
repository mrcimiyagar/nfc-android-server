package kasper.android.nfc_student_auth.server.activities;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import kasper.android.nfc_student_auth.server.R;
import kasper.android.nfc_student_auth.server.adapters.StudentsAdapter;
import kasper.android.nfc_student_auth.server.core.Core;
import kasper.android.nfc_student_auth.server.extras.LinearDecoration;
import kasper.android.nfc_student_auth.server.helpers.DatabaseHelper;
import kasper.android.nfc_student_auth.server.models.memory.Student;

public class MainActivity extends AppCompatActivity {

    private RecyclerView studentsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.studentsRV = findViewById(R.id.activity_main_students_recycler_view);
        this.studentsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.studentsRV.addItemDecoration(new LinearDecoration((int) Core.getInstance().getDisplayHelper().dpToPx(12)
                , (int) Core.getInstance().getDisplayHelper().dpToPx(12)));
        this.studentsRV.setAdapter(new StudentsAdapter(Core.getInstance().getDatabaseHelper().getStudents()));
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0];
            String msgStr = new String(message.getRecords()[0].getPayload());
            this.processStudentAuth(msgStr);
        }
    }

    private void processStudentAuth(String auth) {
        String[] authParts = auth.split(" ");
        String studentId = authParts[0];
        String password = authParts[1];

        try {
            Core.getInstance().getDatabaseHelper().checkStudent(studentId, password);

            Student student = Core.getInstance().getDatabaseHelper().getStudentById(studentId);

            ((StudentsAdapter) this.studentsRV.getAdapter()).updateStudent(student);
        }
        catch (DatabaseHelper.StudentNotFoundException snfEx) {
            Toast.makeText(this, "Student not found in database.", Toast.LENGTH_SHORT).show();
        }
        catch (DatabaseHelper.InvalidStudentPasswordException ispEx) {
            Toast.makeText(this, "Invalid Student password detected.", Toast.LENGTH_SHORT).show();
        }
        catch (DatabaseHelper.StudentAlreadyCheckedException sacEx) {
            Toast.makeText(this, "Student already checked today.", Toast.LENGTH_SHORT).show();
        }
    }
}
