package com.proyecto.bean;

import com.proyecto.utils.DateFormatMobile;

import java.util.ArrayList;
import java.util.Date;

public class DescuentoRegularBean {

    private String Code;
    private String Name;
    private String FechaInicio;
    private String FechaFin;
    private String Observacion;
    private String Tipo;
    private ArrayList<DescuentoRegularDetalleBean> detalle;

    public DescuentoRegularBean() {
        detalle = new ArrayList<>();
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public ArrayList<DescuentoRegularDetalleBean> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<DescuentoRegularDetalleBean> detalle) {
        this.detalle = detalle;
    }

    public Date getFechaInicioDate() {
        return DateFormatMobile.ConvertStringToDate(FechaInicio);
    }

    public Date getFechaFinDate() {
        return DateFormatMobile.ConvertStringToDate(FechaFin);
    }
}
