package com.proyecto.dao;

import android.database.Cursor;

import com.proyecto.bean.MonedaBean;
import com.proyecto.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MonedaDAO {

    public List<MonedaBean> listar() {

        Cursor cursor = DataBaseHelper
                .getHelper(null)
                .getDataBase()
                .rawQuery("select T0.CODIGO, " +
                        "T0.NOMBRE " +
                        " FROM TB_MONEDA T0", null);

        List<MonedaBean> lst = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lst.add(transformCursorToMoneda(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lst;
    }

    private MonedaBean transformCursorToMoneda(Cursor cursor){
        MonedaBean bean = new MonedaBean();
        bean.setCodigo(cursor.getString(cursor.getColumnIndex("CODIGO")));
        bean.setDescripcion(cursor.getString(cursor.getColumnIndex("NOMBRE")));
        return bean;
    }


}
