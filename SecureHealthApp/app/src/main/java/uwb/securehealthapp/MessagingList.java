package uwb.securehealthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by herbt on 1/28/2016.
 */
public class MessagingList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Button Compose = (Button) findViewById(R.id.Compose);
        Compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comp();
            }
        });

        Button Refresh = (Button) findViewById(R.id.Refresh);
        Refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    private void comp(){
        Intent intent = new Intent(MessagingList.this, MessagingActivity.class);
        startActivity(intent);
    }

    private int refresh(){
        return 0;
    }
}
