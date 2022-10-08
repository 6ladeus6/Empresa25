package com.ladeus.empresa25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText jetusuario,jetnombre,jetcorreo,jetclave;
    CheckBox jetactivo;
    RequestQueue rq;
    JsonRequest jrq;
    String usr,nombre,correo,clave;
    public byte sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        //Ocultar la barrra, asociar objetos Java con objetos XML
        //Inicializar la cola de consulta
        sw = 0;
        getSupportActionBar().hide();
        jetusuario = findViewById(R.id.etusuario);
        jetnombre = findViewById(R.id.etnombre);
        jetcorreo = findViewById(R.id.etcorreo);
        jetclave = findViewById(R.id.etclave);
        jetactivo = findViewById(R.id.cbactivo);
        rq = Volley.newRequestQueue(this);
    }
    public void Guardar (View view){
        usr  = jetusuario.getText().toString();
        nombre = jetnombre.getText().toString();
        correo = jetcorreo.getText().toString();
        clave = jetclave.getText().toString();
        if (usr.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }
    public  void Consultar(View view){
        usr = jetusuario.getText().toString();
        if (usr.isEmpty()){
            Toast.makeText(this, "El usuario es requerido para la busqueda", Toast.LENGTH_SHORT).show();
        }else {
            String url = "http://172.18.59.83:80/WebServices/consulta.php?usr="+usr;
            jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            rq.add(jrq);
        }
    }

    public void Limpiar(View view){
        Limpiar_Campos();
    }
    public void  Regresar (View view){
        Intent intmain = new Intent(this,MainActivity.class);
        startActivity(intmain);
    }
    private void Limpiar_Campos(){
        sw = 0;
        jetnombre.setText("");
        jetcorreo.setText("");
        jetclave.setText("");
        jetusuario.setText("");
        jetactivo.setChecked(false);
        jetusuario.requestFocus();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        sw = 1;
        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            jetnombre.setText(jsonObject.optString("nombre"));
            jetcorreo.setText(jsonObject.optString("correo"));
            jetclave.setText(jsonObject.optString("clave"));
            if(jsonObject.optString("activo").equals("si")){
                jetactivo.setChecked(true);
            }else{
                jetactivo.setChecked(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}