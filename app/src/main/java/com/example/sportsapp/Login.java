package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private EditText mCorreo, mContraseña;
    private Button mIngresar, mRegistrarse, Olvide_Contraseña;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser User;


    private Task<Void> mBaseDeDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verificación de internet

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {



        } else {

            //Si no hay internet

            AlertDialog.Builder Sin_Internet = new AlertDialog.Builder(Login.this);
            Sin_Internet.setTitle("Sin conexión");
            Sin_Internet.setMessage("Revisa tu conexión a inernet e intenta \nnuevamente.");
            Sin_Internet.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    System.exit(0);

                }

            });

            Sin_Internet.show();

        }
        //Si hay internet

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser Usuario = FirebaseAuth.getInstance().getCurrentUser();

            }
        };


        mCorreo             = (EditText) findViewById(R.id.txt_field_Correo);
        mContraseña         =(EditText) findViewById(R.id.txt_field_Contraseña);
        mIngresar           =(Button) findViewById(R.id.btn_Ingresar);
        mRegistrarse        =(Button)findViewById(R.id.btn_Registrarse);
        Olvide_Contraseña   =(Button)findViewById(R.id.btn_OlvidoPass);

        mRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, Registro.class);

                startActivity(intent);

                finish();

                return;

            }
        });

        mIngresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(mCorreo.getText().toString().trim().equals("")) {

                    mCorreo.setError("Por favor digite su correo.");
                    mCorreo.requestFocus();

                }else if(mContraseña.getText().toString().trim().equals("")){

                    mContraseña.setError("Por favor digite su contraseña.");
                    mContraseña.requestFocus();

                } else if (!Validar_Email(mCorreo.getText().toString())){

                    mCorreo.setError("Email no válido.");
                    mCorreo.requestFocus();

                }else if(mCorreo.getText().toString().trim().length() > 0 && mContraseña.getText().toString().trim().length()>0) {

                    final String email = mCorreo.getText().toString();

                    final String contrasena = mContraseña.getText().toString();

                    mAuth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                Toast.makeText(Login.this, "Error de ingreso.", Toast.LENGTH_SHORT).show();

                            }else {

                                //Dar valor al usuario actual

                                User = mAuth.getCurrentUser();

                                //Verifica si el usuario ya reviso su correo

                                if(!User.isEmailVerified()){

                                    //Aviso que no verifico

                                    AlertDialog.Builder Auten = new AlertDialog.Builder(Login.this);

                                    Auten.setTitle("Correo no verificado");
                                    Auten.setMessage("Por favor verifica tu correo electrónico.");
                                    Auten.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }

                                    });

                                    Auten.show();

                                }else{

                                    //Si ya verifico el correo

                                    mBaseDeDatos = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Administrador").child(User.getUid()).child("Contraseña").setValue(mContraseña.getText().toString().trim());

                                    Intent intent = new Intent(Login.this, MenuPrincipal.class);
                                    startActivity(intent);
                                    finish();
                                    return;

                                }

                            }

                        }
                    });

                } else{

                    Toast.makeText(Login.this,"Por favor llene los datos solicitados.",Toast.LENGTH_SHORT).show();

                }

            }

        });

        Olvide_Contraseña.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, MenuPrincipal.class);

                startActivity(intent);

                finish();

            }

        });

    }

    private boolean Validar_Email(String email) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        return pattern.matcher(email).matches();

    }

    @Override
    protected void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(firebaseAuthListener);

    }

    @Override
    protected void onStop() {

        super.onStop();

        mAuth.addAuthStateListener(firebaseAuthListener);

    }
}
