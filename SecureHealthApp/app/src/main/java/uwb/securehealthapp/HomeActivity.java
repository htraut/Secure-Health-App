package uwb.securehealthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 9/13/2015.
 */
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button messaging = (Button) findViewById(R.id.mess);
        messaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapActivity("messaging");
            }
        });

        Button medRecords = (Button) findViewById(R.id.medi);
        medRecords.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                swapActivity("medical records");
            }
        });

        Button appointments = (Button) findViewById(R.id.appoint);
        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapActivity("appointments");
            }
        });

        Button preferences = (Button) findViewById(R.id.pref);
        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapActivity("preferences");
            }
        });

        Button billing = (Button) findViewById(R.id.bill);
        billing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                swapActivity("billing");
            }
        });
    }

    //TODO error handling
    //Switch case of activities the application swaps between
    private int swapActivity(String act){
        Intent intent = null;
        if(act == null) return 1;
        switch(act){
            case "messaging":
                intent = new Intent(HomeActivity.this, MessagingActivity.class);
                startActivity(intent);
                break;
            case "medical records":
                intent = new Intent(HomeActivity.this, MedicalRecordsActivity.class);
                startActivity(intent);
                break;
            case "appointments":
                intent = new Intent(HomeActivity.this, AppointmentsActivity.class);
                startActivity(intent);
                break;
            case "preferences":
                intent = new Intent(HomeActivity.this, PreferencesActivity.class);
                startActivity(intent);
                break;
            case "billing":
                intent = new Intent(HomeActivity.this, BillingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return 0;
    }


}
