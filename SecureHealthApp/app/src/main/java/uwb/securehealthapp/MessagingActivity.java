package uwb.securehealthapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Administrator on 9/13/2015.
 */
public class MessagingActivity extends Activity {

    private SendMessageTask mMessage = null;
    private MyTrustManagerFactory trustFac = null;

    private EditText mEmailAddress;
    private EditText mSubject;
    private EditText mBody;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        mEmailAddress = (EditText) findViewById(R.id.eMailAddress);
        mSubject = (EditText) findViewById(R.id.subject);
        mBody = (EditText) findViewById(R.id.body);

        Button Send = (Button) findViewById(R.id.send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        Button Delete = (Button) findViewById(R.id.delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        InputStream is = this.getResources().openRawResource(R.raw.rbfsecurehealthcom);
        trustFac = new MyTrustManagerFactory(is);

    }

    private int send(){
        if(mMessage != null){
            return -1;
        }
        String email, subject, body;

        email = mEmailAddress.getText().toString();
        subject = mSubject.getText().toString();
        body = mBody.getText().toString();

        if(email == null){
            return -1;
        }
        if(subject == null){
            return -1;
        }
        if(body == null){
            return -1;
        }

        mMessage = new SendMessageTask(email, subject, body, trustFac.getTrustFac());
        mMessage.execute((Void) null);
        return 0;
    }

    private int delete(){
        return 0;
    }

    public class SendMessageTask extends AsyncTask<Void, Void, Boolean>{

        private final String messageEmail;
        private final String messageSub;
        private final String messageBody;
        private final TrustManagerFactory tmf;

        SendMessageTask(String email, String subject, String body, TrustManagerFactory Mytmf){
            messageEmail = email;
            messageSub = subject;
            messageBody = body;
            tmf = Mytmf;
        }

        protected Boolean doInBackground(Void... params){

            try {
                SSLContext logContext = SSLContext.getInstance("TLSv1.2");
                logContext.init(null, tmf.getTrustManagers(), null);
                String httpsURL = "https://www.rbfsecurehealth.com";
                URL loginURL = new URL(httpsURL);
                HttpsURLConnection logCon = (HttpsURLConnection) loginURL.openConnection();
                logCon.setSSLSocketFactory(logContext.getSocketFactory());
                logCon.setRequestMethod("POST");
                logCon.setDoInput(true);
                logCon.setDoOutput(true);
                logCon.setRequestProperty("Content-Type", "application/json");

                OutputStream ostream = logCon.getOutputStream();
                OutputStreamWriter owriter = new OutputStreamWriter(ostream);
                JsonWriter jwriter = new JsonWriter(owriter);
                jwriter.beginObject();
                jwriter.name("task").value("Message");
                jwriter.name("email").value(messageEmail);
                jwriter.name("subject").value(messageSub);
                jwriter.name("body").value(messageBody);
                jwriter.endObject();
                jwriter.flush();

                Thread.sleep(1000);


                String name = "";
                boolean auth = false;
                InputStream istream = logCon.getInputStream();
                InputStreamReader ireader = new InputStreamReader(istream);
                JsonReader jreader = new JsonReader(ireader);
                jreader.beginObject();
                if(jreader.hasNext()){
                    name = jreader.nextName();
                    if(name.equals("auth")) auth = jreader.nextBoolean();
                }
                jreader.endObject();

                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("my super tag", "UserLoginTask", e);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}
