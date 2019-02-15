package com.proyecto.sociosnegocio.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.proyecto.bean.CondicionPagoBean;
import com.proyecto.bean.IndicadorBean;
import com.proyecto.bean.ListaPrecioBean;

import java.util.ArrayList;
import java.util.List;

public class ClienteBuscarBean implements Parcelable {
    private String codigo;
    private String nombre;
    private String telefono;
    private String numeroDocumento;
    private String direccionFiscalCodigo;
    private String direccionFiscalNombre;
    private String moneda;
    private double porcentajeDescuento;
    private double porcentajeDescuentoBase;
    private String personaContacto;
    private ListaPrecioBean listaPrecio;
    private CondicionPagoBean condicionPago;
    private IndicadorBean indicador;
    private List<ContactoBuscarBean> contactos;
    private List<DireccionBuscarBean> direcciones;

    public ClienteBuscarBean(){};

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public List<ContactoBuscarBean> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoBuscarBean> contactos) {
        this.contactos = contactos;
    }

    public List<DireccionBuscarBean> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionBuscarBean> direcciones) {
        this.direcciones = direcciones;
    }

    public ListaPrecioBean getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(ListaPrecioBean listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public CondicionPagoBean getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(CondicionPagoBean condicionPago) {
        this.condicionPago = condicionPago;
    }

    public IndicadorBean getIndicador() {
        return indicador;
    }

    public void setIndicador(IndicadorBean indicador) {
        this.indicador = indicador;
    }

    public String getDireccionFiscalCodigo() {
        return direccionFiscalCodigo;
    }

    public void setDireccionFiscalCodigo(String direccionFiscalCodigo) {
        this.direccionFiscalCodigo = direccionFiscalCodigo;
    }

    public String getDireccionFiscalNombre() {
        return direccionFiscalNombre;
    }

    public void setDireccionFiscalNombre(String direccionFiscalNombre) {
        this.direccionFiscalNombre = direccionFiscalNombre;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getPorcentajeDescuentoBase() {
        return porcentajeDescuentoBase;
    }

    public void setPorcentajeDescuentoBase(double porcentajeDescuentoBase) {
        this.porcentajeDescuentoBase = porcentajeDescuentoBase;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    //método propio
    public DireccionBuscarBean getDireccionByCode(String code){
        if(this.direcciones != null){
            for (DireccionBuscarBean dir: this.direcciones) {
                if(dir.getCodigo().equals(code)){
                    return dir;
                }
            }
        }
        return null;
    }

    //método propio
    public ContactoBuscarBean getContactoByCode(String code){
        if(this.contactos != null){
            for (ContactoBuscarBean contacto: this.contactos) {
                if(contacto.getNombre().equals(code)){
                    return contacto;
                }
            }
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codigo);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
        dest.writeString(this.numeroDocumento);
        dest.writeString(this.direccionFiscalCodigo);
        dest.writeString(this.direccionFiscalNombre);
        dest.writeString(this.moneda);
        dest.writeDouble(this.porcentajeDescuento);
        dest.writeDouble(this.porcentajeDescuentoBase);
        dest.writeString(this.personaContacto);
        dest.writeParcelable(this.listaPrecio, flags);
        dest.writeParcelable(this.condicionPago, flags);
        dest.writeParcelable(this.indicador, flags);
        dest.writeTypedList(this.contactos);
        dest.writeTypedList(this.direcciones);
    }

    protected ClienteBuscarBean(Parcel in) {
        this.codigo = in.readString();
        this.nombre = in.readString();
        this.telefono = in.readString();
        this.numeroDocumento = in.readString();
        this.direccionFiscalCodigo = in.readString();
        this.direccionFiscalNombre = in.readString();
        this.moneda = in.readString();
        this.porcentajeDescuento = in.readDouble();
        this.porcentajeDescuentoBase = in.readDouble();
        this.personaContacto = in.readString();
        this.listaPrecio = in.readParcelable(ListaPrecioBean.class.getClassLoader());
        this.condicionPago = in.readParcelable(CondicionPagoBean.class.getClassLoader());
        this.indicador = in.readParcelable(IndicadorBean.class.getClassLoader());
        this.contactos = in.createTypedArrayList(ContactoBuscarBean.CREATOR);
        this.direcciones = in.createTypedArrayList(DireccionBuscarBean.CREATOR);
    }

    public static final Creator<ClienteBuscarBean> CREATOR = new Creator<ClienteBuscarBean>() {
        @Override
        public ClienteBuscarBean createFromParcel(Parcel source) {
            return new ClienteBuscarBean(source);
        }

        @Override
        public ClienteBuscarBean[] newArray(int size) {
            return new ClienteBuscarBean[size];
        }
    };
}