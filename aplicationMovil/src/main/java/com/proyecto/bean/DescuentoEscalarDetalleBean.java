package com.proyecto.bean;

public class DescuentoEscalarDetalleBean {
    private String Code;
    private String LineId;
    private String CodigoArticulo;
    private String NombreArticulo;
    private String Escala1;
    private String Descuento1;
    private String Escala2;
    private String Descuento2;
    private String Escala3;
    private String Descuento3;
    private String Division;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getCodigoArticulo() {
        return CodigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        CodigoArticulo = codigoArticulo;
    }

    public String getNombreArticulo() {
        return NombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        NombreArticulo = nombreArticulo;
    }

    public String getEscala1() {
        return Escala1;
    }

    public double getEscala1Double() {
        return Double.parseDouble(Escala1);
    }

    public void setEscala1(String escala1) {
        Escala1 = escala1;
    }

    public String getDescuento1() {
        return Descuento1;
    }

    public double getDescuento1Double() {
        return Double.parseDouble(Descuento1);
    }

    public void setDescuento1(String descuento1) {
        Descuento1 = descuento1;
    }

    public String getEscala2() {
        return Escala2;
    }

    public double getEscala2Double() {
        return Double.parseDouble(Escala2);
    }

    public void setEscala2(String escala2) {
        Escala2 = escala2;
    }

    public String getDescuento2() {
        return Descuento2;
    }

    public double getDescuento2Double() {
        return Double.parseDouble(Descuento2);
    }

    public void setDescuento2(String descuento2) {
        Descuento2 = descuento2;
    }

    public String getEscala3() {
        return Escala3;
    }

    public double getEscala3Double() {
        return Double.parseDouble(Escala3);
    }

    public void setEscala3(String escala3) {
        Escala3 = escala3;
    }

    public String getDescuento3() {
        return Descuento3;
    }

    public Double getDescuento3Double() {
        return Double.parseDouble(Descuento3);
    }

    public void setDescuento3(String descuento3) {
        Descuento3 = descuento3;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }
}
