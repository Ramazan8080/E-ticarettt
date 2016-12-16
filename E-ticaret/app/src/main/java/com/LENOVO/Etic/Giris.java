package com.LENOVO.Etic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Giris extends AppCompatActivity {
    private static final String tag = "Giris";
    private static final int istek = 0;
    ProgressDialog pDialog;

    @Bind(R.id.giris_kullaniciadi) EditText mail_text;
    @Bind(R.id.giris_sifre) EditText sifre_text;
    @Bind(R.id.btn_giris) Button giris_buton;
    @Bind(R.id.link_kayitt) TextView giris_link;
    @Bind(R.id.textView_donus) TextView donus;
    SoapPrimitive resultString;
    private static final String SOAP_ACTİON="http://elemegim.somee.com/KullaniciAdiKontrol";
    private static final String METHOD_NAME="Elemegi";
    private static final String NAMESPACE="http://elemegim.somee.com/WebService.asmx";
    private static final String URL="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        ButterKnife.bind(this);
        
        giris_buton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            SoapObject so=new SoapObject(NAMESPACE,METHOD_NAME);
            }
        });

        giris_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Kayit.class);
                startActivityForResult(intent, istek);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(tag, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        giris_buton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Giris.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Kimlik doğrulanıyor...");
        progressDialog.show();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onLoginSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == istek) {
            if (resultCode == RESULT_OK) {

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        giris_buton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Giriş Hatası", Toast.LENGTH_LONG).show();

        giris_buton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mail = mail_text.getText().toString();
        String sifre = sifre_text.getText().toString();

        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mail_text.setError("Üye Olurken Aldığınız Mail Adresini Giriniz");
            valid = false;
        } else {
            mail_text.setError(null);
        }

        if (!(sifre.equals(R.id.kayit_sifre))) {
            sifre_text.setError("Yanlış Sifre Girdiniz");
            valid = false;
        } else {
            sifre_text.setError(null);
        }

        return valid;
    }

    public class asyncGiris extends AsyncTask <String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
         uye_kontrol();
        return null;
        }
        @Override
        protected void onPreExecute() { // Post tan önce yapılacak işlemler.

        }
        protected void onPostExecute(String result) { //Posttan sonra

        }
    }
    public void uye_kontrol() {
        String SOAP_ACTION = "http://elemegim.somee.com/kontrol";
        String METHOD_NAME = "kontrol";
        String NAMESPACE = "http://elemegim.somee.com/";
        String URL = "http://elmegim.somee.com/Webservice.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("Id",mail_text);
            Request.addProperty("sifre", sifre_text);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();
            donus.setText(resultString.toString());

        } catch (Exception ex) {
            donus.setText(ex.toString());

        }
    }
}
