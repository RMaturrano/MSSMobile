package com.proyecto.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TipoCambioBean implements Parcelable {

    private String fecha;
    private String moneda;
    private String tasa;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTasa() {
        return tasa;
    }

    public void setTasa(String tasa) {
        this.tasa = tasa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fecha);
        dest.writeString(this.moneda);
        dest.writeString(this.tasa);
    }

    public TipoCambioBean() {
    }

    protected TipoCambioBean(Parcel in) {
        this.fecha = in.readString();
        this.moneda = in.readString();
        this.tasa = in.readString();
    }

    public static final Parcelable.Creator<TipoCambioBean> CREATOR = new Parcelable.Creator<TipoCambioBean>() {
        @Override
        public TipoCambioBean createFromParcel(Parcel source) {
            return new TipoCambioBean(source);
        }

        @Override
        public TipoCambioBean[] newArray(int size) {
            return new TipoCambioBean[size];
        }
    };
}
