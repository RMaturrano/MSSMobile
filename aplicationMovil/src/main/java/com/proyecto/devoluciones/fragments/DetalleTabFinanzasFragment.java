package com.proyecto.devoluciones.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyect.movil.R;
import com.proyecto.bean.DevolucionBean;
import com.proyecto.devoluciones.DevolucionDetalleActivity;
import com.proyecto.devoluciones.ItemDetailModel;
import com.proyecto.devoluciones.adapter.recyclerview.RVAdapterDetalleDevolucion;
import com.proyecto.devoluciones.adapter.recyclerview.listeners.IRVAdapterDetalleDevolucion;

import java.util.ArrayList;
import java.util.List;

public class DetalleTabFinanzasFragment extends Fragment  implements IRVAdapterDetalleDevolucion {

    private RecyclerView mRV;
    private RVAdapterDetalleDevolucion mAdapter;
    private DevolucionBean mDevolucion;

    private static String TTL_CONDICION_PAGO = "Condicion de pago";
    private static String TTL_INDICADOR = "Indicador";
    private static String TTL_LISTA_PRECIO = "Lista de precios";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView =  inflater.inflate(R.layout.fragment_detalle_devolucion_tab_general, container, false);

        mRV = (RecyclerView) mView.findViewById(R.id.rvDetalleDevTabGeneral);
        mRV.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        mRV.setHasFixedSize(true);

        mAdapter = new RVAdapterDetalleDevolucion(this);
        mRV.setAdapter(mAdapter);

        if(getActivity().getIntent().getExtras() != null &&
                getActivity().getIntent().getExtras().containsKey(DevolucionDetalleActivity.KEY_PARAM_DEVOLUCION)){
            mDevolucion = getActivity().getIntent().getParcelableExtra(DevolucionDetalleActivity.KEY_PARAM_DEVOLUCION);
        }

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mDevolucion != null) {
            List<ItemDetailModel> items = new ArrayList<>();
            items.add(new ItemDetailModel(TTL_CONDICION_PAGO, mDevolucion.getCondicionPagoNombre(), false));
            items.add(new ItemDetailModel(TTL_INDICADOR, mDevolucion.getIndicadorNombre(), false));
            items.add(new ItemDetailModel(TTL_LISTA_PRECIO, mDevolucion.getListaPrecioNombre(), false));
            mAdapter.clearAndAddAll(items);
        }
    }

    @Override
    public void onItemClick(ItemDetailModel item) {

    }

}
