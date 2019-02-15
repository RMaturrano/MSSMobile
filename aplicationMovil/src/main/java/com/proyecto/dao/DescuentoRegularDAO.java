package com.proyecto.dao;

import android.database.Cursor;

import com.proyecto.bean.DescuentoEscalarBean;
import com.proyecto.bean.DescuentoRegularDetalleBean;
import com.proyecto.bean.DescuentoRegularBean;
import com.proyecto.database.DataBaseHelper;
import com.proyecto.utils.DateFormatMobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 03/07/2018.
 */

public class DescuentoRegularDAO {

    public List<DescuentoRegularBean> listar() {

        List<DescuentoRegularBean> lst = new ArrayList<>();

        try {
            Date currentTime = Calendar.getInstance().getTime();

            String query = "";
            query += "SELECT * FROM TD_DESCUENTO_REGULAR ";
            query += "WHERE [FECHA] BETWEEN CAST(U_MSS_FEINI AS INTEGER)  AND CAST(U_MSS_FEFI AS INTEGER) ";
            query = query.replace("[FECHA]", DateFormatMobile.ConvertDateToString(currentTime));

            Cursor cursor = DataBaseHelper
                    .getHelper(null)
                    .getDataBase()
                    .rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    DescuentoRegularBean model = transformCursorCabecera(cursor);
                    model.setDetalle(listarDetalle(model));
                    lst.add(model);
                } while (cursor.moveToNext());
            }

            if (cursor != null && !cursor.isClosed())
                cursor.close();

        } catch (Exception e) {
            String error = e.toString();
        }

        return lst;
    }

    public ArrayList<DescuentoRegularDetalleBean> listarDetalle(DescuentoRegularBean model) {

        ArrayList<DescuentoRegularDetalleBean> lst = new ArrayList<>();

        try {

            String query = "";
            query += "SELECT * FROM TD_DESCUENTO_REGULAR_1 WHERE Code = '" + model.getCode() + "'";

            Cursor cursor = DataBaseHelper
                    .getHelper(null)
                    .getDataBase()
                    .rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    lst.add(transformCursorDetalle(cursor));
                } while (cursor.moveToNext());
            }

            if (cursor != null && !cursor.isClosed())
                cursor.close();

        } catch (Exception e) {
            String error = e.toString();
        }

        return lst;
    }

    private DescuentoRegularBean transformCursorCabecera(Cursor cursor) {
        DescuentoRegularBean bean = new DescuentoRegularBean();
        bean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
        bean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
        bean.setFechaInicio(cursor.getString(cursor.getColumnIndex("U_MSS_FEINI")));
        bean.setFechaFin(cursor.getString(cursor.getColumnIndex("U_MSS_FEFI")));
        bean.setObservacion(cursor.getString(cursor.getColumnIndex("U_MSS_OBSERV")));
        bean.setTipo(cursor.getString(cursor.getColumnIndex("U_MSS_TIPO")));
        return bean;
    }

    private DescuentoRegularDetalleBean transformCursorDetalle(Cursor cursor) {
        DescuentoRegularDetalleBean bean = new DescuentoRegularDetalleBean();
        bean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
        bean.setLineId(cursor.getString(cursor.getColumnIndex("LINEID")));
        bean.setCodigoArticulo(cursor.getString(cursor.getColumnIndex("U_MSS_COAR")));
        bean.setNombreArticulo(cursor.getString(cursor.getColumnIndex("U_MSS_DEAR")));
        bean.setDescuento1(cursor.getString(cursor.getColumnIndex("U_MSS_DESC1")));

        return bean;
    }

}
