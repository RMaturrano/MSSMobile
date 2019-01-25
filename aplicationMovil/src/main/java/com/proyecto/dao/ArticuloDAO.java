package com.proyecto.dao;

import android.database.Cursor;

import com.google.firebase.perf.metrics.AddTrace;
import com.proyecto.bean.ArticuloBean;
import com.proyecto.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    @AddTrace(name = "listarArticuloDAOTrace", enabled = true)
    public List<ArticuloBean> listar(String listaPrecio, String almacen) {

        String filterPriceList = "", filterAlmacen = "", columnStock = "";

        if (listaPrecio != null && !listaPrecio.equals("") && !listaPrecio.equals("-1")) {
            //filterPriceList = " AND P.CodigoLista = " + listaPrecio;
            filterPriceList = " AND X0.CodigoLista = " + listaPrecio;
        }

        if (almacen != null && !almacen.equals("") && !almacen.equals("-1")) {
            //filterAlmacen = " AND (SELECT COUNT(*) FROM TB_CANTIDAD WHERE ARTICULO = A.Codigo AND ALMACEN = '" + almacen + "') > 0 ";
            filterAlmacen = "  T0.Almacen = '" + almacen + "' and ";
        }

        /*"select " +
                "A.Codigo, " +
                "A.Nombre," +
                //TODO: Se debe cambiar es select. Hace que la consulta se realice con demoras.
                "(select IFNULL(SUM(CAST(STOCK AS NUMERIC)),0) from TB_CANTIDAD where ARTICULO = A.Codigo "+columnStock+"), " +
                //"G.NOMBRE, "+
                "G.NOMBRE "
                + "from TB_ARTICULO A join TB_GRUPO_ARTICULO G " +
                "ON A.GrupoArticulo = G.CODIGO left join TB_PRECIO P on " +
                " P.Articulo = A.Codigo AND P.CodigoLista IN(SELECT X0.ListaPrecio from TB_SOCIO_NEGOCIO X0) "+
                filterPriceList + filterAlmacen +
                " GROUP BY A.Codigo, A.Nombre  " +
                "  having SUM(P.PrecioVenta) > 0 " +
                " order by G.NOMBRE,A.Nombre" */

        String query = "select T0.Articulo as Articulo, " +
                "T1.Nombre as Nombre, " +
                "SUM(T0.Stock) as Stock, " +
                "T2.Nombre as Grupo, " +
                "T1.CodigoBarras " +
                " from TB_CANTIDAD T0 join TB_ARTICULO T1 " +
                " ON T0.Articulo = T1.Codigo join TB_GRUPO_ARTICULO T2 " +
                " ON T1.GrupoArticulo = T2.Codigo " +
                " where T0.Stock > 0 and " + filterAlmacen +
                "(SELECT COUNT(X0.CodigoLista) FROM TB_PRECIO X0 where X0.Articulo = T0.Articulo " +
                " and X0.PrecioVenta > 0 " + filterPriceList +
                " and X0.CodigoLista in (select X0.ListaPrecio from TB_SOCIO_NEGOCIO X0)) > 0"+
                " GROUP BY 1 " +
                " order by T2.Nombre, T1.Nombre";

        Cursor cursor = DataBaseHelper
                .getHelper(null)
                .getDataBase()
                .rawQuery(query, null);

        List<ArticuloBean> lst = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lst.add(transformCursorToArticulo(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lst;
    }

    @AddTrace(name = "listarPorPrecioArticuloDAOTrace", enabled = true)
    public List<ArticuloBean> listarPorPrecio(String listaPrecio) {

        Cursor cursor = DataBaseHelper
                .getHelper(null)
                .getDataBase()
                .rawQuery("select " +
                        "A.Codigo, " +
                        "A.Nombre," +
                        "G.NOMBRE as Grupo, " +
                        "IFNULL(P.PrecioVenta, '0') as Precio "
                        + "from TB_ARTICULO A join TB_GRUPO_ARTICULO G " +
                        "ON A.GrupoArticulo = G.CODIGO left join TB_PRECIO P on " +
                        " P.Articulo = A.Codigo " +
                        " where P.CodigoLista = " + listaPrecio +
                        " AND CAST(IFNULL(P.PrecioVenta, '0') AS DECIMAL) > 0" +
                        " order by G.NOMBRE,A.Nombre ", null);

        List<ArticuloBean> lst = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lst.add(transformCursorToArticuloxPrecio(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lst;
    }

    private ArticuloBean transformCursorToArticulo(Cursor cursor) {
        ArticuloBean bean = new ArticuloBean();
        bean.setCod(cursor.getString(cursor.getColumnIndex("Articulo")));
        bean.setDesc(cursor.getString(cursor.getColumnIndex("Nombre")));
        bean.setStock(cursor.getString(cursor.getColumnIndex("Stock")));
        bean.setGrupoArticulo(cursor.getString(cursor.getColumnIndex("Grupo")));
        bean.setCodigoBarras(cursor.getString(cursor.getColumnIndex("CodigoBarras")));
        return bean;
    }

    private ArticuloBean transformCursorToArticuloxPrecio(Cursor cursor) {
        ArticuloBean bean = new ArticuloBean();
        bean.setCod(cursor.getString(cursor.getColumnIndex("Codigo")));
        bean.setDesc(cursor.getString(cursor.getColumnIndex("Nombre")));
        bean.setPre(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Precio"))));
        bean.setNombreGrupoArt(cursor.getString(cursor.getColumnIndex("Grupo")));
        return bean;
    }

}
