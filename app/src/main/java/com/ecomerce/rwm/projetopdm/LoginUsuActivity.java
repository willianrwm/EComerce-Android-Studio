package com.ecomerce.rwm.projetopdm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUsuActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtEmail = (EditText) findViewById(R.id.edt_email_login_usu);
        edtSenha = (EditText) findViewById(R.id.edt_senha_login_usu);
        botao = (Button) findViewById(R.id.btn_login_usu);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar();
            }
        });
    }


    private void confirmar(){
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if(validarCampos() == false) {
            if ((email.equals("renatow@gmail.com")) && (senha.equals("8790"))) {
                Toast toast = Toast.makeText(this, R.string.aviso_login_sucesso,Toast.LENGTH_LONG);
                toast.show();
                finish();

                Intent it = new Intent(this, ProdutoActivity.class);
                startActivity(it);
            } else {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle(R.string.lbl_aviso);
                dlg.setMessage(R.string.lbl_login_invalido);
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        }
    }


    //VALIDA CAMPOS DE TEXTO
    private boolean validarCampos(){

        boolean res = false;

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();


        if(res = isCampoVazio(email)){
            edtEmail.requestFocus();
        }
        else if(res = isCampoVazio(senha)){
            edtSenha.requestFocus();
        }
        else if(res = !isEmailValido(email)){
            edtEmail.requestFocus();
        }

        if(res){
            android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(this);
            dlg.setTitle(R.string.lbl_aviso); //alt+enter pra colocar string no xml
            dlg.setMessage(R.string.lbl_campos_invalidos);
            dlg.setNeutralButton("OK",null); //botao que aparece no alerta, podendo aqui chamar uma função
            dlg.show();
        }
        return res;
    }

    //VERIFICA CAMPOS VAZIOS
    private boolean isCampoVazio(String valor){
        //se for vazia e nao tiver nenhum caracter espaço por ex
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    //VERIFICA SE EMAIL NAO E VAZIO E É VALIDO
    private boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

}
