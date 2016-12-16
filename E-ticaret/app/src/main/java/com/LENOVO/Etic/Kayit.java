package com.LENOVO.Etic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

public class Kayit extends AppCompatActivity {
    private static final String tag = "Kayit Ol";

    @Bind(R.id.kayit_isim) EditText isim_text;
    @Bind(R.id.kayit_kullaniciadi) EditText kuladi_text;
    @Bind(R.id.kayit_mail) EditText mail_text;
    @Bind(R.id.kayit_telefon) EditText telefon_text;
    @Bind(R.id.kayit_sifre) EditText sifre_text;
    @Bind(R.id.kayit_sifretekrar) EditText sifretekrar;
    @Bind(R.id.btn_kayitol) Button kayitol;
    @Bind(R.id.link_giris) TextView giris_linki;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        ButterKnife.bind(this);

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        giris_linki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Giris.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(tag, "Kayıt Ol");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        kayitol.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Kayit.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Hesap Oluştur...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onSignupSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        kayitol.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Giriş Hatası", Toast.LENGTH_LONG).show();

        kayitol.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String isim = isim_text.getText().toString();
        String adres = kuladi_text.getText().toString();
        String mail = mail_text.getText().toString();
        String telefon = telefon_text.getText().toString();
        String sifre = sifre_text.getText().toString();
        String sifretekrarr = sifretekrar.getText().toString();

        if (isim.isEmpty() || isim.length() < 5) {
            isim_text.setError("En Az 5 Karakter Olmalıdır!");
            valid = false;
        } else {
            isim_text.setError(null);
        }

        if (adres.isEmpty()) {
            kuladi_text.setError("Geçerli Bir Kullanıcı Adı Giriniz");
            valid = false;
        } else {
            kuladi_text.setError(null);
        }


        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mail_text.setError("Geçerli Bir Mail Adresi Giriniz");
            valid = false;
        } else {
            mail_text.setError(null);
        }

        if (telefon.isEmpty() || telefon.length()!=10) {
            telefon_text.setError("Geçerli Bir Telefon Numarası Giriniz");
            valid = false;
        } else {
            telefon_text.setError(null);
        }

        if (sifre.isEmpty() || sifre.length() < 4 || sifre.length() > 15) {
            sifre_text.setError("4-15 Karakter Giriniz ");
            valid = false;
        } else {
            sifre_text.setError(null);
        }

        if (sifretekrarr.isEmpty() || sifretekrarr.length() < 4 || sifretekrarr.length() > 10 || !(sifretekrarr.equals(sifre))) {
            sifretekrar.setError("Şifreler Eşleşmiyor");
            valid = false;
        } else {
            sifretekrar.setError(null);
        }

        return valid;
    }
}