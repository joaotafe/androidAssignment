package com.example.groupsmsv1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * The main activity of the application where the user can edit a message and the recipient phone number
 * and then send an SMS.
 */
public class HomeScreen extends AppCompatActivity {
    private String message = "";
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvMessageDetails = findViewById(R.id.tvMessageDetails);
        tvMessageDetails.setBackgroundColor(Color.GREEN);
        tvMessageDetails.setMovementMethod(new ScrollingMovementMethod());
        message = "No Message Set";
        phone = "No Phone Set";

        setSummary();


        ActivityResultContract simpleRawIntentContract;
        //message
        HandleActivityResultForMessage handleActivityResultForMessage;
        ActivityResultLauncher launchEditMessageActivity;
        //phone
        HandleActivityResultForPhone handleActivityResultForPhone;
        ActivityResultLauncher launchEditSendToActivity;

        simpleRawIntentContract = new ActivityResultContracts.StartActivityForResult();
        handleActivityResultForMessage = new HandleActivityResultForMessage();
        handleActivityResultForPhone = new HandleActivityResultForPhone();

        //Instantiating our launchActivity object for EditMessage
        launchEditMessageActivity = registerForActivityResult(simpleRawIntentContract, handleActivityResultForMessage);
        launchEditSendToActivity = registerForActivityResult(simpleRawIntentContract, handleActivityResultForPhone);

        Button btnEditMessage;
        btnEditMessage =(Button) this.findViewById(R.id.btnEditMessage);
        btnEditMessage.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the "Edit Message" button is clicked.
             *
             * @param v The view that was clicked (the "Edit Message" button).
             */
            @Override
            public void onClick(View v) {
                Intent editIntent;
                Activity sourceActivity;
                Class destinationClass;

                sourceActivity = HomeScreen.this;
                destinationClass = EditMessage.class;

                editIntent = new Intent(sourceActivity, destinationClass);
                editIntent.putExtra("CURRENT_MESSAGE", message);

                //HomeScreen.this.startActivity(editIntent);
                launchEditMessageActivity.launch(editIntent);

            }
        });

        Button btnEditSendTo;
        btnEditSendTo=(Button) this.findViewById(R.id.btnEditSendTo);
        btnEditSendTo.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the "Edit Send To" button is clicked.
             *
             * @param v The view that was clicked (the "Edit Send To" button).
             */
            @Override
            public void onClick(View v) {
                Intent editIntent;
                Activity sourceActivity;
                Class destinationClass;

                sourceActivity = HomeScreen.this;
                destinationClass = EditSendTo.class;

                editIntent = new Intent(sourceActivity, destinationClass);
                editIntent.putExtra("CURRENT_PHONE", phone);

                //HomeScreen.this.startActivity(editIntent);
                launchEditSendToActivity.launch(editIntent);

            }
        });

        Button btnSend;
        btnSend=(Button) this.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the "Send" button is clicked.
             *
             * @param v The view that was clicked (the "Send" button).
             */
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent to " + phone, Toast.LENGTH_LONG).show();

            }
        });

    }
    /**
     * Set the summary text in the UI to display the selected recipient phone number and message.
     */
    private void setSummary(){
        StringBuilder summary;
        summary = new StringBuilder("Sending To:\n");
        summary.append(phone);
        summary.append("\n\nMessage:\n");
        summary.append(message);
        TextView tvMessagesDetails = (TextView) findViewById(R.id.tvMessageDetails);
        tvMessagesDetails.setText(summary);
    }

    /**
     * This class handles the result of editing the message in the EditMessage activity.
     */
    public class HandleActivityResultForMessage implements ActivityResultCallback {
        /**
         * This method is called when the result of editing the message is received.
         *
         * @param dataIn The result data from the EditMessage activity.
         */
        public void onActivityResult(Object dataIn) {
            ActivityResult result;
            result = (ActivityResult)dataIn;
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String newMessage = (String) (data.getStringExtra("NEW_MESSAGE"));
                message = newMessage;
                setSummary();
            }
        }
    }

    /**
     * This class handles the result of editing the recipient phone number in the EditSendTo activity.
     */
    public class HandleActivityResultForPhone implements ActivityResultCallback {
        /**
         * This method is called when the result of editing the recipient phone number is received.
         *
         * @param dataIn The result data from the EditSendTo activity.
         */
        public void onActivityResult(Object dataIn) {
            ActivityResult result;
            result = (ActivityResult)dataIn;
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String newPhone = (String) (data.getStringExtra("NEW_PHONE"));
                phone = newPhone;
                setSummary();
            }
        }
    }



}