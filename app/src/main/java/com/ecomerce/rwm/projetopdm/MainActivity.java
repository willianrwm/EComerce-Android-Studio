package com.ecomerce.rwm.projetopdm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ecomerce.rwm.projetopdm.dao.ProdutoDAO;
import com.ecomerce.rwm.projetopdm.modelo.Produto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout fl; //frame que recebe os fragments
    FrameLayout flInicio;   //frame que recebe a lista de produtos

    ListView listView;  //listview

    Produto produto;
    ProdutoDAO produtoDAO;
    List<Produto> arrayListProduto;
    ListaProdutoAdapter arrayAdapterProduto;


    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //==========================
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context contexto = getApplicationContext();
                String texto = getString(R.string.lbl_app_desenvolvido);
                int duracao = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(contexto, texto,duracao);
                toast.show();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });

        //==========================
        //RECEBE ELEMENTOS DO XML
        fl = (FrameLayout) findViewById(R.id.conteudo_fragment);
        flInicio = (FrameLayout) findViewById(R.id.conteudo_inicio);
        listView = (ListView) findViewById(R.id.listProdutos);
        //==========================

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //==========================

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoEnviado = (Produto)arrayAdapterProduto.getItem(position);    //pega o item selecionado na listView

                Intent i = new Intent(MainActivity.this, DetalheProduto.class);
                i.putExtra("produto-enviado",produtoEnviado); //envia para a classe formProduto
                startActivity(i);
            }
        });
    }

    //==============================================================================================

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent it;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            it = new Intent(this, LoginUsuActivity.class);
            startActivity(it);
        } else if (id == R.id.action_loginAdm) {
            flInicio.setVisibility(View.INVISIBLE);
            fl.setVisibility(View.VISIBLE);
            LoginAdmFragment loginAdmFragment = new LoginAdmFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, loginAdmFragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fl.setVisibility(View.INVISIBLE);
            flInicio.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_calca) {

        } else if (id == R.id.nav_camiseta) {

        } else if (id == R.id.nav_sapato) {

        } else if (id == R.id.nav_fale_conosco) {
            flInicio.setVisibility(View.INVISIBLE);
            fl.setVisibility(View.VISIBLE);
            FaleConoscoFragment faleConoscoFragment = new FaleConoscoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, faleConoscoFragment).commit();

        } else if (id == R.id.nav_site) {
            Uri uri = Uri.parse("https://material.io/");
            Intent abrirBrowser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(abrirBrowser);

        } else if (id == R.id.nav_wv) {
            flInicio.setVisibility(View.INVISIBLE);
            fl.setVisibility(View.VISIBLE);
            WebViewFragment webViewFragment = new WebViewFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, webViewFragment).commit();
        } else if (id == R.id.nav_sobre) {
            flInicio.setVisibility(View.INVISIBLE);
            fl.setVisibility(View.VISIBLE);
            SobreDialogFragment dialogFragment = new SobreDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), getString(R.string.lbl_sobre));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class SobreDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == DialogInterface.BUTTON_NEGATIVE){
                        Intent it = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.utfpr.edu.br"));
                        startActivity(it);
                    }
                }
            };
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.lbl_projeto)
                    .setMessage(R.string.lbl_pdm)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(R.string.lbl_site, listener)
                    .create();
            return dialog;
        }
    }

    public void populaLista(){
        produtoDAO = new ProdutoDAO(MainActivity.this);

        //array recebe a chamada selectAllProdutos() na classe que comunica com o BD
        //essa chamada retorna a consulta SQL
        arrayListProduto = produtoDAO.selectAllProdutos();
        produtoDAO.close(); //fecha conexao com banco

        if(listView != null){   //se a lista da view for diferente de null
            arrayAdapterProduto = new ListaProdutoAdapter(MainActivity.this, arrayListProduto); //pode passar getContext() no lugar de mainActivity.class
            listView.setAdapter(arrayAdapterProduto);
        }
    }


    //ESSE METODO JA EXISTE, POREM ELE É EDITADO AQUI, CHAMANDO O METODO POPULARLISTA();
    @Override
    protected void onResume() {
        super.onResume();

        populaLista();
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
