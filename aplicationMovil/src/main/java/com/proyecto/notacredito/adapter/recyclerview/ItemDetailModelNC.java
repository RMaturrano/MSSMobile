package com.proyecto.notacredito.adapter.recyclerview;

/**
 * Created by PC on 05/06/2018.
 */

public class ItemDetailModelNC {

    private String title, content;
    private boolean isEditable;

    public ItemDetailModelNC(String titulo, String contenido, boolean editable){
        title = titulo;
        content = contenido;
        isEditable = editable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

}
