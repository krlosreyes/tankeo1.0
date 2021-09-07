package com.example.tankeo10;

import static com.example.tankeo10.R.id.inicioSesion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView bienvenidoLabel, continuarlabel, regisUsuario;
    ImageView loginImageView;
    TextInputLayout usuarioTextField, contrasenaTextField, emailTextField;
    TextInputEditText mailEditText, passEditText;
    MaterialButton inicioSesion;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginImageView = findViewById(R.id.loginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarlabel = findViewById(R.id.continuarlabel);
        regisUsuario = findViewById(R.id.regisUsuario);
        usuarioTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);

        mailEditText = findViewById(R.id.mailEditText);
        passEditText = findViewById(R.id.passEditText);

        mAuth = FirebaseAuth.getInstance();


        regisUsuario.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, registro_Activity.class);

            Pair[] pairs = new Pair[7];
            pairs[0] = new Pair<View, String>(loginImageView, "logoImageTrans");
            pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
            pairs[2] = new Pair<View, String>(continuarlabel, "textTrans");
            pairs[3] = new Pair<View, String>(regisUsuario, "newUserTrans");
            pairs[4] = new Pair<View, String>(usuarioTextField, "emailInputTextTrans");
            pairs[5] = new Pair<View, String>(contrasenaTextField, "passInputTrans");
            pairs[6] = new Pair<View, String>(inicioSesion, "buttonInicioTrans");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
                finish();
            }

            startActivity(intent);
            finish();
        });

        inicioSesion.setOnClickListener(v -> validate());
    }

    private void validate() {
        String email = mailEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailEditText.setError("correo invalido");
            return;
        } else {
            mailEditText.setError(null);
        }
        if (password.isEmpty() || password.length() < 8) {
            passEditText.setError("Se necesitan mas de 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passEditText.setError("Debe contener un numero");
            return;
        } else {
            passEditText.setError(null);
        }
        iniciarSesion(email, password);
    }

    public void iniciarSesion(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, principalActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales equivocadas ", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
