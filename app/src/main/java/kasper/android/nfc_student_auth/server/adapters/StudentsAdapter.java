package kasper.android.nfc_student_auth.server.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import kasper.android.nfc_student_auth.server.R;
import kasper.android.nfc_student_auth.server.models.memory.Student;

/**
 * Created by keyhan1376 on 12/1/2017.
 */

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentVH> {

    private List<Student> students;

    public StudentsAdapter(List<Student> students) {
        this.students = students;
        this.notifyDataSetChanged();
    }

    public void updateStudent(Student student) {
        for (int counter = 0; counter < this.students.size(); counter++) {
            if (this.students.get(counter).getStudentId().equals(student.getStudentId())) {
                this.students.set(counter, student);
                this.notifyItemChanged(counter);
            }
        }
    }

    @Override
    public StudentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudentVH(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.adapter_students, parent, false));
    }

    @Override
    public void onBindViewHolder(StudentVH holder, int position) {
        Student student = this.students.get(position);
        holder.studentIdTV.setText(student.getStudentId());
        for (int counter = 0; counter < student.getCheckArr().length(); counter++) {
            holder.checkCBArr[counter].setChecked(student.getCheckArr().charAt(counter) == '1');
        }
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    class StudentVH extends RecyclerView.ViewHolder {

        TextView studentIdTV;
        CheckBox[] checkCBArr;

        StudentVH(View itemView) {
            super(itemView);
            this.studentIdTV = itemView.findViewById(R.id.adapter_students_student_id_text_view);
            this.checkCBArr = new CheckBox[7];
            this.checkCBArr[0] = itemView.findViewById(R.id.adapter_students_checkbox0);
            this.checkCBArr[1] = itemView.findViewById(R.id.adapter_students_checkbox1);
            this.checkCBArr[2] = itemView.findViewById(R.id.adapter_students_checkbox2);
            this.checkCBArr[3] = itemView.findViewById(R.id.adapter_students_checkbox3);
            this.checkCBArr[4] = itemView.findViewById(R.id.adapter_students_checkbox4);
            this.checkCBArr[5] = itemView.findViewById(R.id.adapter_students_checkbox5);
            this.checkCBArr[6] = itemView.findViewById(R.id.adapter_students_checkbox6);

        }
    }
}
