package com.ecomerce.rwm.projetopdm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ecomerce.rwm.projetopdm.modelo.Produto;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ProdutoDAO extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DBProduto.dp";
    private static final int VERSION = 1;
    private static final String TABELA = "pessoa";

    private static final String ID = "id";
    private static final String VALOR = "valor";
    private static final String DESCRICAO = "descricao";
    private static final String CAMINHO_IMAGEM = "caminho_imagem";

    //CONSTRUTOR RECEBENDOO O CONTEXTO, NOME DO BANCO, E VERSAO
    public ProdutoDAO(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    //CRIA A TABELA
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+" ( "+
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " "+VALOR+" TEXT, "+DESCRICAO+" TEXT, "+
                " "+CAMINHO_IMAGEM+" TEXT );";

        db.execSQL(sql);
    }

    //ATUALIZA A TABELA CASO EXISTA ALGUM RELEASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IS EXISTS "+TABELA;
        db.execSQL(sql);
        onCreate(db);

    }

    //==============================================================================================

    //INSERE PRODUTO NO BANCO
    public long salvarProduto(Produto p){
        ContentValues values = new ContentValues();
        long retornoDP;

        values.put(VALOR, p.getPreco());
        values.put(DESCRICAO, p.getDescricao());
        values.put(CAMINHO_IMAGEM, p.getEnd_imagem());

        retornoDP = getWritableDatabase().insert(TABELA, null, values);
        return retornoDP;
    }

    //ALTERA PRODUTO NO BANCO
    public long alterarProduto(Produto p){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(VALOR, p.getPreco());
        values.put(DESCRICAO, p.getDescricao());
        values.put(CAMINHO_IMAGEM, p.getEnd_imagem());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(p.getId());

        retornoDB = getWritableDatabase().update(TABELA, values, "id=?", parametros);
        return retornoDB;
    }

    //EXCLUIR PRODUTO NO BANCO
    public long excluirProduto(Produto p){
        long retornoDB;

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(p.getId());

        retornoDB = getWritableDatabase().delete(TABELA, ID+"=?", parametros);
        return retornoDB;
    }

    //BUSCAR PRODUTOS NO BANCO
    public ArrayList<Produto> selectAllProdutos(){

        String[]colunas = {ID, VALOR, DESCRICAO, CAMINHO_IMAGEM};

        Cursor cursor = getWritableDatabase().query(TABELA, colunas, null, null, null, null, null);
        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

        while (cursor.moveToNext()){
            Produto p = new Produto();

            p.setId(cursor.getInt(0));
            p.setPreco(cursor.getString(1));
            p.setDescricao(cursor.getString(2));
            p.setEnd_imagem(cursor.getString(3));

            listaProdutos.add(p);
        }

        return listaProdutos;
    }
}
