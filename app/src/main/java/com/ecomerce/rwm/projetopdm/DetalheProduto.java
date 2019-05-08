package com.ecomerce.rwm.projetopdm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecomerce.rwm.projetopdm.dao.ProdutoDAO;
import com.ecomerce.rwm.projetopdm.modelo.Produto;

public class DetalheProduto extends AppCompatActivity {

    TextView txtValor, txtDescricao, txtCaminhoImagem;
    Button btnCarrinho;
    Produto produto, altProduto;
    ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        altProduto = (Produto) i.getSerializableExtra("produto-enviado");   //pega o produto enviado pela lista de produtos no mainActivity
        produto = new Produto();
        produtoDAO = new ProdutoDAO(DetalheProduto.this);

        //RECEBE OS COMPONENTES DO XML====
        txtValor = (TextView) findViewById(R.id.txt_valor_detalhe);
        txtDescricao = (TextView) findViewById(R.id.txt_descricao_detalhe);
        txtCaminhoImagem = (TextView) findViewById(R.id.txt_imagem_detalhe);
        btnCarrinho = (Button) findViewById(R.id.btn_add_cart);
        //=================================

        //ATRIBUI TEXTO NO BOTAO
        if(altProduto != null){
            txtValor.setText(altProduto.getPreco());
            txtDescricao.setText(altProduto.getDescricao());
            txtCaminhoImagem.setText(altProduto.getEnd_imagem());

            produto.setId(altProduto.getId());  //sera setado o id direto no objeto pessoa, pois o id nao é alterado
        }

        btnCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context contexto = getApplicationContext();
                String texto = (getString(R.string.lbl_produto_add));
                int duracao = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(contexto, texto,duracao);
                toast.show();
            }
        });
    }

}
