package com.example.sportsapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private EditText mNombresClienteRegistro, mApellidosClienteRegistro, mEmailClienteRegistro, mContraseñaClienteRegistro, mContraseñaClienteRegistroV;
    private Button mRegistroCliente, mCerrarRegistroCliente;

    private FirebaseAuth mAuth;
    private DatabaseReference mBaseDeDatosCliente;
    private FirebaseUser User;

    private String idCliente;

    private String mNombresClienteDato, mApellidosClienteDato, mEmailClienteDato, mContraseñaClienteDato, mContraseñaClienteDatoV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mNombresClienteRegistro     = (EditText) findViewById(R.id.Text_Field_Nombres);
        mApellidosClienteRegistro   = (EditText) findViewById(R.id.Text_Field_Apellidos);
        mEmailClienteRegistro       = (EditText) findViewById(R.id.Text_Field_Correo);
        mContraseñaClienteRegistro  = (EditText) findViewById(R.id.Text_Field_Password);
        mContraseñaClienteRegistroV = (EditText) findViewById(R.id.Text_Field_Password2);
        mRegistroCliente            = (Button) findViewById(R.id.BTN_Registrarse_usuario);
        mCerrarRegistroCliente      = (Button) findViewById(R.id.BTN_volver);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder Aviso = new AlertDialog.Builder(Registro.this);

        Aviso.setTitle("Atencion!");
        Aviso.setMessage("Por favor ingresa tus datos reales ya que despues no vas a poder actualizarlos.");
        Aviso.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });

        Aviso.show();


        mRegistroCliente.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(Validacion()){

                    if(mContraseñaClienteRegistro.getText().toString().trim().length() < 3){

                        mContraseñaClienteRegistro.setError("Debe superar más de dos carácteres.");
                        mContraseñaClienteRegistro.requestFocus();

                    } else if(mNombresClienteRegistro.getText().toString().trim().length() < 3) {

                        mNombresClienteRegistro.setError("Debe superar más de dos carácteres.");
                        mNombresClienteRegistro.requestFocus();

                    } else if(mApellidosClienteRegistro.getText().toString().trim().length() < 3){

                        mApellidosClienteRegistro.setError("Debe superar más de dos carácteres.");
                        mApellidosClienteRegistro.requestFocus();

                    } else{

                        final String email = mEmailClienteRegistro.getText().toString();
                        final String contraseña = mContraseñaClienteRegistro.getText().toString();

                        mAuth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {

                                    Toast.makeText(Registro.this, "Error de registro.", Toast.LENGTH_SHORT).show();

                                } else {

                                    idCliente = mAuth.getCurrentUser().getUid();
                                    User = mAuth.getCurrentUser();
                                    User.sendEmailVerification();
                                    mBaseDeDatosCliente = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Administrador").child(idCliente);
                                    mBaseDeDatosCliente.setValue(true);
                                    GuardarInformacionDelCliente();

                                }

                            }

                        });

                    }

                }

            }

        });
        mCerrarRegistroCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Registro.this, Login.class);

                startActivity(intent);

                finish();

                return;

            }

        });
    }


    private boolean Validacion(){

        boolean Flag = false;

        mNombresClienteDato = mNombresClienteRegistro.getText().toString().trim();
        mApellidosClienteDato = mApellidosClienteRegistro.getText().toString().trim();
        mEmailClienteDato = mEmailClienteRegistro.getText().toString().trim();
        mContraseñaClienteDato = mContraseñaClienteRegistro.getText().toString().trim();
        mContraseñaClienteDatoV = mContraseñaClienteRegistroV.getText().toString().trim();

        if(mEmailClienteDato.equals("")){

            mEmailClienteRegistro.setError("Por favor ingrese su correo.");
            mEmailClienteRegistro.requestFocus();

        }else if(mContraseñaClienteDato.equals("")){

            mContraseñaClienteRegistro.setError("Por favor ingrese su contraseña.");
            mContraseñaClienteRegistro.requestFocus();

        }else if(!mContraseñaClienteDato.equals(mContraseñaClienteDatoV)){

            mContraseñaClienteRegistroV.setError("Las contraseñas no coinciden.");
            mContraseñaClienteRegistroV.requestFocus();

        }else if(mNombresClienteDato.equals("")){

            mNombresClienteRegistro.setError("Por favor ingrese su nombre(s).");
            mNombresClienteRegistro.requestFocus();

        }else if(mApellidosClienteDato.equals("")){

            mApellidosClienteRegistro.setError("Por favor ingrese su apellido(s)");
            mApellidosClienteRegistro.requestFocus();

        }else if(!Validar_Email(mEmailClienteDato)){

            mEmailClienteRegistro.setError("Email no válido.");
            mEmailClienteRegistro.requestFocus();

        }else{

            Flag = true;

        }

        return Flag;

    }

    private boolean Validar_Email(String email) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        return pattern.matcher(email).matches();

    }

    private void GuardarInformacionDelCliente(){

        mNombresClienteDato = mNombresClienteRegistro.getText().toString();
        mApellidosClienteDato = mApellidosClienteRegistro.getText().toString();
        mEmailClienteDato = mEmailClienteRegistro.getText().toString();
        mContraseñaClienteDato = mContraseñaClienteRegistro.getText().toString();


        Map InformacionDelUsuario = new HashMap();
        InformacionDelUsuario.put("Nombre", mNombresClienteDato.trim());
        InformacionDelUsuario.put("Apellido", mApellidosClienteDato.trim());
        InformacionDelUsuario.put("Email", mEmailClienteDato.trim());
        InformacionDelUsuario.put("Contraseña", mContraseñaClienteDato.trim());
        mBaseDeDatosCliente.updateChildren(InformacionDelUsuario);

        AlertDialog.Builder Auten = new AlertDialog.Builder(Registro.this);

        Auten.setTitle("Verificar correo electrónico");
        Auten.setMessage("Por favor verifica tu correo para ser registrado satisfactoriamente.");
        Auten.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Registro.this, Login.class);

                startActivity(intent);

                finish();

            }

        });

        Auten.show();

    }

}