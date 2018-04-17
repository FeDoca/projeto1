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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ferna.carteiradeclientes.database.DadosOpenHelper;
import com.example.ferna.carteiradeclientes.dominio.entidades.Cliente;
import com.example.ferna.carteiradeclientes.dominio.repositorio.ClienteRepositorio;

import java.util.regex.Pattern;

public class ActCadCliente extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTelefone;
    private ConstraintLayout layoutContenActCadCliente;

    private ClienteRepositorio clienteRepositorio;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNome = findViewById(R.id.edtNome);
        edtTelefone = findViewById(R.id.edtTelefone);

        layoutContenActCadCliente = findViewById(R.id.layoutContenActCadCliente);

        criarConexao();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    }

    private void criarConexao(){

        try{

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(layoutContenActCadCliente, R.string.message_conexao, Snackbar.LENGTH_SHORT)
                    .setAction("ok", null).show();
            clienteRepositorio = new ClienteRepositorio(conexao);

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
        Intent it = new Intent(ActCadCliente.this, ActMain.class);
        startActivity(it);
    }

    private void confrimar(){
        cliente = new Cliente();
        if (validaCampos() == false){
            try {
                clienteRepositorio.inserir(cliente);

                finish();

            } catch (SQLiteException ex){

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro");
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton(R.string.action_ok,null);
                dlg.show();
                }

        }



    }

    private boolean validaCampos(){

        boolean res = false;

        String nome = edtNome.getText().toString();
        String telefone = edtTelefone.getText().toString();

        cliente.nome = nome;
        cliente.telefone = telefone;

        if (res = isCampoVazio(nome)){
            edtNome.requestFocus();
            }
            else
                if (res =  isCampoVazio(telefone)){
            edtTelefone.requestFocus();}

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.messege_campos_invalidos_brancos);
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();}
        return res;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim( ).isEmpty() );
        return resultado;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                confrimar();
                //Toast.makeText(this, "Botão Ok selecionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_cancelar:
                //Toast.makeText(this, R.string.botao_cancelar, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
