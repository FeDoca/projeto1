package com.example.ferna.carteiradeclientes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ferna.carteiradeclientes.dominio.entidades.Cliente;

import java.util.List;
 public class ClienteAdaper extends RecyclerView.Adapter<ClienteAdaper.ViewHolderClient>{
        private List<Cliente> dados;

        public ClienteAdaper(List<Cliente>dados){
            this.dados = dados;
        }

        @Override
        public ClienteAdaper.ViewHolderClient onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.linha_clientes, parent, false);
            ViewHolderClient holderClient = new ViewHolderClient(view);

            return holderClient;
        }

        @Override
        public void onBindViewHolder(ClienteAdaper.ViewHolderClient holder, int position) {

            if ((dados != null) && (dados.size()>0)){

                Cliente cliente = dados.get(position);
                holder.txtNome.setText(cliente.nome);
                holder.txtTelefone.setText(cliente.telefone);
            }
        }

        @Override
        public int getItemCount() {
            return dados.size();
        }

        public  class ViewHolderClient extends RecyclerView.ViewHolder{

            public TextView txtNome;
            public TextView txtTelefone;

            public ViewHolderClient(View itemView) {
                super(itemView);
                txtNome = itemView.findViewById(R.id.txtNome);
                txtTelefone =itemView.findViewById(R.id.txtTelefone);
            }
        }
 }