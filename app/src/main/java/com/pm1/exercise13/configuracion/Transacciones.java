package com.pm1.exercise13.configuracion;

public class Transacciones {
//    DASE DE DATOS DE SQLITE
    public static final String NameDataBase = "pm01DB";
//    TABLA DE SQLITE
    public static final String tablaPersonas = "personas";
//    CAMPOS DE LA TABLA PERSONAS
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";
//    TRANSACCIONES DDL de PERSONAS
    public static final String CreateTablePersonas = "CREATE TABLE personas (id INTEGER PRIMARY KEY AUTOINCREMENT,"+
        "nombres TEXT, apellidos TEXT, edad INTEGER, correo TEXT, direccion TEXT)";
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS personas";
    public static final String test1 = "select * from personas";

}
