package com.example.groupsmsv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditSendTo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_send_to);

        // Get the intent for this activity. Every activity has an intent and
        // set the EditText contents to the string in the extra info that comes with
        // the intent
        Intent editIntent;
        EditText etPhone;
        editIntent = this.getIntent();
        String thePhone;
        thePhone = editIntent.getStringExtra("CURRENT_PHONE");
        etPhone = (EditText) this.findViewById(R.id.etPhone);
        etPhone.setText(thePhone);

        // Get an event handler going for the Done button so that we can return the
        // new message via an Intent object
        Button btnDone = (Button) this.findViewById(R.id.btnEditSendToDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("NEW_PHONE", ((EditText) findViewById(R.id.etPhone)).getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}