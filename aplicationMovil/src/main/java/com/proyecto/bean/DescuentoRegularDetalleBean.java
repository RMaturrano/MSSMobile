package com.proyecto.bean;

public class DescuentoRegularDetalleBean {
    private String Code;
    private String LineId;
    private String CodigoArticulo;
    private String NombreArticulo;
    private String Descuento1;

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

    public String getDescuento1() {
        return Descuento1;
    }

    public double getDescuento1Double() {
        return Double.parseDouble(Descuento1);
    }

    public void setDescuento1(String descuento1) {
        Descuento1 = descuento1;
    }
}
