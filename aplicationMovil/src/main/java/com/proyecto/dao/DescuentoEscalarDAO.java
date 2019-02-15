package com.proyecto.dao;

import android.database.Cursor;

import com.proyecto.bean.AlmacenBean;
import com.proyecto.bean.DescuentoEscalarBean;
import com.proyecto.bean.DescuentoEscalarDetalleBean;
import com.proyecto.database.DataBaseHelper;
import com.proyecto.utils.DateFormatMobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.services.events.QueueFileEventStorage;

/**
 * Created by PC on 03/07/2018.
 */

public class DescuentoEscalarDAO {

    public List<DescuentoEscalarBean> listar() {

        List<DescuentoEscalarBean> lst = new ArrayList<>();

        try {
            Date currentTime = Calendar.getInstance().getTime();

            String query = "";
            query += "SELECT * FROM TD_DESCUENTO_ESCALA ";
            query += "WHERE [FECHA] BETWEEN CAST(U_MSS_FEINI AS INTEGER)  AND CAST(U_MSS_FEFI AS INTEGER) ";
            query = query.replace("[FECHA]", DateFormatMobile.ConvertDateToString(currentTime));

            Cursor cursor = DataBaseHelper
                    .getHelper(null)
                    .getDataBase()
                    .rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    DescuentoEscalarBean model = transformCursorCabecera(cursor);
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

    public ArrayList<DescuentoEscalarDetalleBean> listarDetalle(DescuentoEscalarBean model) {

        ArrayList<DescuentoEscalarDetalleBean> lst = new ArrayList<>();

        try {

            String query = "";
            query += "SELECT * FROM TD_DESCUENTO_ESCALA_1 WHERE Code = '" + model.getCode() + "'";

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

    private DescuentoEscalarBean transformCursorCabecera(Cursor cursor) {
        DescuentoEscalarBean bean = new DescuentoEscalarBean();
        bean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
        bean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
        bean.setFechaInicio(cursor.getString(cursor.getColumnIndex("U_MSS_FEINI")));
        bean.setFechaFin(cursor.getString(cursor.getColumnIndex("U_MSS_FEFI")));
        bean.setObservacion(cursor.getString(cursor.getColumnIndex("U_MSS_OBSERV")));
        bean.setTipo(cursor.getString(cursor.getColumnIndex("U_MSS_TIPO")));
        return bean;
    }

    private DescuentoEscalarDetalleBean transformCursorDetalle(Cursor cursor) {
        DescuentoEscalarDetalleBean bean = new DescuentoEscalarDetalleBean();
        bean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
        bean.setLineId(cursor.getString(cursor.getColumnIndex("LINEID")));
        bean.setCodigoArticulo(cursor.getString(cursor.getColumnIndex("U_MSS_COAR")));
        bean.setNombreArticulo(cursor.getString(cursor.getColumnIndex("U_MSS_DEAR")));
        bean.setEscala1(cursor.getString(cursor.getColumnIndex("U_MSS_ESCA1")));
        bean.setDescuento1(cursor.getString(cursor.getColumnIndex("U_MSS_DESC1")));
        bean.setEscala2(cursor.getString(cursor.getColumnIndex("U_MSS_ESCA2")));
        bean.setDescuento2(cursor.getString(cursor.getColumnIndex("U_MSS_DESC2")));
        bean.setEscala3(cursor.getString(cursor.getColumnIndex("U_MSS_ESCA3")));
        bean.setDescuento3(cursor.getString(cursor.getColumnIndex("U_MSS_DESC3")));
        bean.setDivision(cursor.getString(cursor.getColumnIndex("U_MSS_DIVISION")));

        return bean;
    }

}
