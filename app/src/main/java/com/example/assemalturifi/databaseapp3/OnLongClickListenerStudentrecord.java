package com.example.assemalturifi.databaseapp3;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//step28
public class OnLongClickListenerStudentrecord  implements View.OnLongClickListener{
    //step29
    Context context;
    String id;

    //step30
    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Student Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {


                        dialog.dismiss();

                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }


                        else if (item == 1) {

                            boolean deleteSuccessful = new TableControllerStudent(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete student record.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                        }


                    }
                }).show();

        return false;
    }
    //step31
    public void editRecord(final int studentId) {
        final TableControllerStudent tableControllerStudent = new TableControllerStudent(context);
        Student objectStudent = tableControllerStudent.readSingleRecord(studentId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_form, null, false);

        final EditText editTextStudentFirstname = (EditText) formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail = (EditText) formElementsView.findViewById(R.id.editTextStudentEmail);

        editTextStudentFirstname.setText(objectStudent.name);
        editTextStudentEmail.setText(objectStudent.email);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                                Student objectStudent = new Student();
                                objectStudent.id = studentId;
                                objectStudent.name = editTextStudentFirstname.getText().toString();
                                objectStudent.email = editTextStudentEmail.getText().toString();

                                boolean updateSuccessful = tableControllerStudent.update(objectStudent);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Student record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update student record.", Toast.LENGTH_SHORT).show();
                                }
                                ((MainActivity) context).countRecords();
                                ((MainActivity) context).readRecords();

                            }

                        }).show();
    }

}
