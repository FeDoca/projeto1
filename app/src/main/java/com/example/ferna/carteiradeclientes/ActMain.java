package com.example.ferna.carteiradeclientes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.ferna.carteiradeclientes.database.DadosOpenHelper;
import com.example.ferna.carteiradeclientes.dominio.entidades.Cliente;
import com.example.ferna.carteiradeclientes.dominio.repositorio.ClienteRepositorio;
import java.util.List;

public class ActMain extends AppCompatActivity {
private RecyclerView lstDados;
private FloatingActionButton fab;
private ConstraintLayout LayoutContenMain;
private SQLiteDatabase conexao;
private DadosOpenHelper dadosOpenHelper;
private ClienteAdaper clienteAdaper;
private ClienteRepositorio clienteRepositorio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        lstDados = (RecyclerView) findViewById(R.id.lsdDados);
        LayoutContenMain = (ConstraintLayout) findViewById(R.id.layoutContenMain);

        criarConexao();
        lstDados.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDados.setLayoutManager(linearLayoutManager);

        clienteRepositorio = new ClienteRepositorio(conexao);
        List <Cliente> dados = clienteRepositorio.buscarTodos();

        clienteAdaper = new ClienteAdaper(dados);
        lstDados.setAdapter(clienteAdaper);

        FloatingActionButton fab = findViewById(R.id.fab);

    }

    private void criarConexao(){

        try{

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(LayoutContenMain, R.string.message_conexao, Snackbar.LENGTH_SHORT)
                    .setAction("ok", null).show();
        }
        catch (SQLiteException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok,null);
            dlg.show();
        }
    }
    public void cadastrar (View view){
        Intent it = new Intent(ActMain.this, ActCadCliente.class);
        startActivity(it);
    }
}
