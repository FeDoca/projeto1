package com.example.ferna.carteiradeclientes.database;

public class ScriptDLL {

    public static String getCreateTabelCliente() {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS CLIENTE (");
        sql.append("       CODIGO     INTEGER        PRIMARY KEY AUTOINCREMENT NOT NULL, " );
        sql.append("       NOME       VARCHAR (250)  NOT NULL DEFAULT (''), " );
        sql.append("       TELEFONE   VARCHAR (20)   NOT NULL DEFAULT ('')  )");

        return sql.toString();
        }
}
