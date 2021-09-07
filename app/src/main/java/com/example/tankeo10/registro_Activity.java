package com.example.tankeo10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class registro_Activity extends AppCompatActivity {

    TextView bienvenidoLabel,contLabel,nuevoUsuario;
    ImageView regisImageView;
    TextInputLayout cedulaUser,nomUser,apellidoUser,celularUser, emailTextField,contrasenaTextField,confContrasena;
    MaterialButton inicioSesion;
    TextInputEditText cedulaEditText, nomEditText,apeEditText, celularEditText, mailEditText,passEditText,confPassEditText;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        regisImageView =        findViewById(R.id.regisImageView);
        bienvenidoLabel =       findViewById(R.id.bienvenidoLabel);
        contLabel =             findViewById(R.id.contLabel);
        nuevoUsuario =          findViewById(R.id.nuevoUsuario);
        cedulaUser =            findViewById(R.id.cedulaUser);
        celularUser =            findViewById(R.id.celularUser);
        nomUser =               findViewById(R.id.nomUser);
        apellidoUser =          findViewById(R.id.apellidoUser);
        emailTextField =        findViewById(R.id.emailTextField);
        contrasenaTextField =   findViewById(R.id.contrasenaTextField);
        confContrasena =        findViewById(R.id.confContrasena);
        inicioSesion =          findViewById(R.id.inicioSesion);
        nomEditText =           findViewById(R.id.nomEditText);
        cedulaEditText =           findViewById(R.id.cedulaEditText);
        celularEditText =           findViewById(R.id.celularEditText);
        apeEditText =           findViewById(R.id.apeEditText);
        mailEditText =          findViewById(R.id.mailEditText);
        passEditText =          findViewById(R.id.passEditText);
        confPassEditText =      findViewById(R.id.confPassEditText);


        nuevoUsuario.setOnClickListener(v -> transitionBack());

        inicioSesion.setOnClickListener(v -> validate());
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void validate() {
             String cedula =        cedulaEditText.getText().toString().trim();
             String nombre =        nomEditText.getText().toString().trim();
             String apellido =      apeEditText.getText().toString().trim();
             String celular =       celularEditText.getText().toString().trim();
             String email =         mailEditText.getText().toString().trim();
             String password =      passEditText.getText().toString().trim();
             String confirmPass =   confPassEditText.getText().toString().trim();

            if(cedula.isEmpty()){
                cedulaEditText.setError("Debe ingresar un número");
                return;
            }else{
                cedulaEditText.setError(null);
            }
            if(nombre.isEmpty()){
                nomEditText.setError("Debe ingresar un nombre");
                return;
            }else{
                nomEditText.setError(null);
            }

            if(apellido.isEmpty()){
                apeEditText.setError("Debe ingresar un apellido");
                return;
            }else{
                apeEditText.setError(null);
            }
            if(celular.isEmpty()){
                celularEditText.setError("Debe ingresar un # celular ");
                return;
            }else{
                celularEditText.setError(null);
            }

             if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                 emailTextField.setError("correo invalido");
                 return;
             }else{
                 emailTextField.setError(null);
             }
             if(password.isEmpty() || password.length()<8){
                 passEditText.setError("Se necesitan mas de 8 caracteres");
                 return;
             }else if(!Pattern.compile("[0-9]").matcher(password).find()){
                passEditText.setError("Debe contener un numero");
                return;
             }else{
                passEditText.setError(null);
             }
             if(!confirmPass.equals(password)){
                 confPassEditText.setError("Las contraseñas no coinciden");
                 return;
             }else{
                 registrar(cedula,nombre,apellido,celular,email,password);
             }

    }

    private void registrar(String cedula, String nombre, String apellido,String celular, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){

                        Map<String, Object> map = new HashMap<>();
                        map.put("cedula", cedula);
                        map.put("nombre", nombre);
                        map.put("apellido", apellido);
                        map.put("celular", celular);
                        map.put("Email", email);
                        map.put("contraseña", password);

                        String id = mAuth.getCurrentUser().getUid();

                        mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(task2 -> {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(registro_Activity.this, LoginActivity.class));
                                finish();
                            }
                        });

                    }else{
                        Toast.makeText(registro_Activity.this, "Fallo el registro", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override

    public void onBackPressed() {
        transitionBack();
    }

    public void transitionBack(){
        Intent intent = new Intent (registro_Activity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(regisImageView, "logoImageTrans");
        pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
        pairs[2] = new Pair<View, String>(contLabel, "textTrans");
        pairs[3] = new Pair<View, String>(nuevoUsuario, "newUserTextTrans");
        pairs[4] = new Pair<View, String>(emailTextField, "lemailInputTextTrans");
        pairs[5] = new Pair<View, String>(contrasenaTextField, "passInputTrans");
        pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTextTrans");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(registro_Activity.this,pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }

        startActivity(intent);
        finish();
    }




}