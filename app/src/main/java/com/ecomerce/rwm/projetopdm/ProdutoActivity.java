package com.ecomerce.rwm.projetopdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ecomerce.rwm.projetopdm.dao.ProdutoDAO;
import com.ecomerce.rwm.projetopdm.modelo.Produto;

import java.util.ArrayList;

public class ProdutoActivity extends AppCompatActivity {

    ListView listView;  //listview
    Button btnCadastrar;    //botao inserir novo produto

    Produto produto;
    ProdutoDAO produtoDAO;
    ArrayList<Produto> arrayListProduto;
    ArrayAdapter<Produto> arrayAdapterProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //==========================
        //RECEBE ELEMENTOS DO XML
        listView = (ListView) findViewById(R.id.listProdutos);
        btnCadastrar = (Button) findViewById(R.id.btn_novoCadastro);

        //permite açao ao clicar por longo tempo no botao
        registerForContextMenu(listView);

        //BOTAO ABRINDO FORM DE CADASTRO DE PRODUTOS
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdutoActivity.this, FormProdutoActivity.class);
                startActivity(i);
            }
        });
        //==========================

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoEnviado = (Produto)arrayAdapterProduto.getItem(position);    //pega o item selecionado na listView

                Intent i = new Intent(ProdutoActivity.this, FormProdutoActivity.class);
                i.putExtra("produto-enviado",produtoEnviado); //envia para a classe formProduto
                startActivity(i);
            }
        });

        //ação ao clicar por um longo tempo em um item da lista de produtos
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Produto)arrayAdapterProduto.getItem(position);
                return false;
            }
        });
    }


    public void populaLista(){
        produtoDAO = new ProdutoDAO(ProdutoActivity.this);

        //array recebe a chamada selectAllProdutos() na classe que comunica com o BD
        //essa chamada retorna a consulta SQL
        arrayListProduto = produtoDAO.selectAllProdutos();
        produtoDAO.close(); //fecha conexao com banco

        if(listView != null){   //se a lista da view for diferente de null
            arrayAdapterProduto = new ArrayAdapter<Produto>(ProdutoActivity.this,
                    android.R.layout.simple_list_item_1, arrayListProduto);
            listView.setAdapter(arrayAdapterProduto);
        }
    }


    //ESSE METODO JA EXISTE, POREM ELE É EDITADO AQUI, CHAMANDO O METODO POPULARLISTA();
    @Override
    protected void onResume() {
        super.onResume();

        populaLista();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem mDelete = menu.add(R.string.lbl_confirma_delete);

        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                ProdutoDAO produtoDAO = new ProdutoDAO(ProdutoActivity.this);
                retornoDB = produtoDAO.excluirProduto(produto);
                produtoDAO.close();

                if(retornoDB == -1){
                    alert(getString(R.string.lbl_erro_excluir_prod));
                }else{
                    alert(getString(R.string.lbl_sucesso_excluir_prod));
                }
                populaLista();
                return false;
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
