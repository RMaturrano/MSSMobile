package com.proyecto.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class AlmacenBean implements Parcelable {

	private String codigo;
	private String descripcion;
	private Double descuento;

	public AlmacenBean(){}

	@Override
	public String toString() {
		return this.descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	protected AlmacenBean(Parcel in) {
		codigo = in.readString();
		descripcion = in.readString();
		descuento = in.readByte() == 0x00 ? null : in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(codigo);
		dest.writeString(descripcion);
		if (descuento == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeDouble(descuento);
		}
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<AlmacenBean> CREATOR = new Parcelable.Creator<AlmacenBean>() {
		@Override
		public AlmacenBean createFromParcel(Parcel in) {
			return new AlmacenBean(in);
		}

		@Override
		public AlmacenBean[] newArray(int size) {
			return new AlmacenBean[size];
		}
	};
}
