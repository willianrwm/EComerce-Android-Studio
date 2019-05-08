package com.ecomerce.rwm.projetopdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecomerce.rwm.projetopdm.dao.ProdutoDAO;
import com.ecomerce.rwm.projetopdm.modelo.Produto;

public class FormProdutoActivity extends AppCompatActivity {

    EditText edtValor, edtDescricao, edtCaminhoImagem;
    Button btnVariavel;
    Produto produto, altProduto;
    ProdutoDAO produtoDAO;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        altProduto = (Produto) i.getSerializableExtra("produto-enviado");   //pega o produto enviado pela lista de produtos no mainActivity
        produto = new Produto();
        produtoDAO = new ProdutoDAO(FormProdutoActivity.this);

        //RECEBE OS COMPONENTES DO XML====
        edtValor = (EditText) findViewById(R.id.edtValorProduto);
        edtDescricao = (EditText) findViewById(R.id.edtDescricaoProduto);
        edtCaminhoImagem = (EditText) findViewById(R.id.edtImagemProduto);
        btnVariavel = (Button) findViewById(R.id.btn_Variael);
        //=================================

        //ATRIBUI TEXTO NO BOTAO
        if(altProduto != null){
            btnVariavel.setText(R.string.lbl_alterar);
            edtValor.setText(altProduto.getPreco());
            edtDescricao.setText(altProduto.getDescricao());
            edtCaminhoImagem.setText(altProduto.getEnd_imagem());

            produto.setId(altProduto.getId());  //sera setado o id direto no objeto pessoa, pois o id nao é alterado
        }else{
            btnVariavel.setText(R.string.lbl_salvar);
        }

        //EVENTO AO CLICAR NO BOTAO
        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            //ATRIBUI VALORES NA CLASSE MODELO/PRODUTO
            public void onClick(View v) {
                produto.setPreco(edtValor.getText().toString());
                produto.setDescricao(edtDescricao.getText().toString());
                produto.setEnd_imagem(edtCaminhoImagem.getText().toString());

                String lbl_botao = btnVariavel.getText().toString();
                //caso o botao esteja como salvar
                if((lbl_botao.equals("Salvar")) || (lbl_botao.equals("Save"))){
                    retornoDB = produtoDAO.salvarProduto(produto);   //Chama metodo do produtoDAO que insere produto no banco
                    produtoDAO.close();
                    if(retornoDB == -1){
                        alert(getString(R.string.lbl_erro_cadastrar_prod));
                    }else{
                        alert(getString(R.string.lbl_sucesso_cadastrar_prod));
                    }
                }else{  //se o botao estiver como alterar
                    retornoDB = produtoDAO.alterarProduto(produto);   //Chama metodo do produtoDAO que altera produto no banco
                    produtoDAO.close();
                    if(retornoDB == -1){
                        alert(getString(R.string.lbl_erro_alterar_prod));
                    }else{
                        alert(getString(R.string.lbl_sucesso_alterar_prod));
                    }
                }
                finish();
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
