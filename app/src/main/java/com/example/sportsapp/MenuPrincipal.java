package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuPrincipal extends AppCompatActivity {

    private Button mbtn_Ingresar_icn_atletismo, mbtn_Ingresar_icn_basket, mbtn_Ingresar_ic_soccer, mbtn_Ingresar_icn_baseball, mbtn_Ingresar_icn_tennis, mbtn_Ingresar_icn_rugby, mbtn_Ingresar_icn_volley;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

     mbtn_Ingresar_icn_basket       = (Button) findViewById(R.id.btn_Ingresar_icn_basket);
     mbtn_Ingresar_ic_soccer        = (Button) findViewById(R.id.btn_Ingresar_ic_soccer);
     mbtn_Ingresar_icn_baseball     = (Button) findViewById(R.id.btn_Ingresar_icn_baseball);
     mbtn_Ingresar_icn_tennis       = (Button) findViewById(R.id.btn_Ingresar_icn_tennis);
     mbtn_Ingresar_icn_rugby        = (Button) findViewById(R.id.btn_Ingresar_icn_rugby);
     mbtn_Ingresar_icn_volley       = (Button) findViewById(R.id.btn_Ingresar_icn_volley);
     mbtn_Ingresar_icn_atletismo    = (Button) findViewById(R.id.btn_Ingresar_icn_runner);


     mbtn_Ingresar_icn_atletismo.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionAtletismo.class);

             startActivity(intent);

             finish();
         }
     });

     mbtn_Ingresar_icn_basket.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionBasket.class);

             startActivity(intent);

             finish();
            }
         });


     mbtn_Ingresar_ic_soccer.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionSoccer.class);

             startActivity(intent);

             finish();
         }
     });

     mbtn_Ingresar_icn_baseball.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionBaseball.class);

             startActivity(intent);

             finish();
         }
     });

     mbtn_Ingresar_icn_tennis.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionTennis.class);

             startActivity(intent);

             finish();
         }
     });

     mbtn_Ingresar_icn_rugby.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionRugby.class);

             startActivity(intent);

             finish();
         }
     });

     mbtn_Ingresar_icn_volley.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MenuPrincipal.this, SeccionVolley.class);

             startActivity(intent);

             finish();
         }
     });

   }
}