package com.ecomerce.rwm.projetopdm;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ecomerce.rwm.projetopdm.modelo.Produto;

import java.util.List;

public class ListaProdutoAdapter extends BaseAdapter {

    private Context context;
    private List<Produto> listaProdutos;

    public ListaProdutoAdapter(Context context, List<Produto> listaProdutos) {
        this.context = context;
        this.listaProdutos = listaProdutos;
    }

    @Override
    public int getCount() {
        //retorna tamanho da lista
        return listaProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        //retorna item da posiçao
        return listaProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        //retorna posiçao
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_produto, null);

        TextView txt_imagem = (TextView) v.findViewById(R.id.txt_imagem);
        TextView txt_preco = (TextView) v.findViewById(R.id.txt_valor);
        TextView txt_descricao = (TextView) v.findViewById(R.id.txt_descricao);

        txt_imagem.setText(listaProdutos.get(position).getEnd_imagem());
        txt_preco.setText("R$ "+listaProdutos.get(position).getPreco());
        txt_descricao.setText(listaProdutos.get(position).getDescricao());

        v.setTag(listaProdutos.get(position).getId());

        return v;
    }
}
