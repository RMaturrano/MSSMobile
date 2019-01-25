package com.proyecto.ordenventa.bean;

public class ItemAddSalesOrder {
    private int id;
    private String titulo;
    private String contenido;
    private String claveInterna;
    private boolean isEditable;

    public ItemAddSalesOrder(){contenido = "";}

    public ItemAddSalesOrder(int id, String titulo, String contenido, boolean editable){
        this.titulo = titulo;
        this.contenido = contenido;
        this.isEditable = editable;
        this.id = id;
    }

    public ItemAddSalesOrder(int id, String titulo, boolean editable){
        this.titulo = titulo;
        this.contenido = null;
        this.isEditable = editable;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public String getClaveInterna() {
        return claveInterna;
    }

    public void setClaveInterna(String claveInterna) {
        this.claveInterna = claveInterna;
    }
}
