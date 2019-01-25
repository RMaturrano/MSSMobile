package com.proyecto.dao;

import android.database.Cursor;

import com.proyecto.bean.TipoCambioBean;
import com.proyecto.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TipoCambioDAO {
    public List<TipoCambioBean> listar() {

        Cursor cursor = DataBaseHelper
                .getHelper(null)
                .getDataBase()
                .rawQuery("select T0.FECHA, " +
                        "T0.MONEDA, " +
                        "T0.TASA " +
                        " FROM TB_TIPO_CAMBIO T0", null);

        List<TipoCambioBean> lst = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lst.add(transformCursorToTipoCambio(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lst;
    }

    private TipoCambioBean transformCursorToTipoCambio(Cursor cursor){
        TipoCambioBean bean = new TipoCambioBean();
        bean.setFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
        bean.setMoneda(cursor.getString(cursor.getColumnIndex("MONEDA")));
        bean.setTasa(cursor.getString(cursor.getColumnIndex("TASA")));
        return bean;
    }

}
