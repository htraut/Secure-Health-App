package uwb.securehealthapp;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManagerFactory;

/**
 * Created by herbt on 1/28/2016.
 */
public class MyTrustManagerFactory  {

    public static TrustManagerFactory trustFac;
    private InputStream is;

    MyTrustManagerFactory(InputStream cert){
        is = cert;
    }

    public TrustManagerFactory getTrustFac(){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca = cf.generateCertificate(is);

            KeyStore trusted = KeyStore.getInstance(KeyStore.getDefaultType());
            trusted.load(null, null);
            trusted.setCertificateEntry("ca", ca);
            trustFac = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustFac.init(trusted);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trustFac;
    }




}
