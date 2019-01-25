package com.proyecto.ordenventa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.movil.R;
import com.proyecto.bean.CondicionPagoBean;
import com.proyecto.bean.IndicadorBean;
import com.proyecto.bean.ListaPrecioBean;
import com.proyecto.bean.MonedaBean;
import com.proyecto.bean.OrdenVentaBean;
import com.proyecto.bean.OrdenVentaDetalleBean;
import com.proyecto.bean.TipoCambioBean;
import com.proyecto.dao.ClienteDAO;
import com.proyecto.dao.CorrelativoDAO;
import com.proyecto.dao.ListaPrecioDAO;
import com.proyecto.dao.MonedaDAO;
import com.proyecto.dao.TipoCambioDAO;
import com.proyecto.geolocalizacion.MapFunctions;
import com.proyecto.ordenventa.adapter.recyclerview.RVAdapterAddOrdenVenta;
import com.proyecto.ordenventa.adapter.recyclerview.listeners.IRVAdapterAddOrdenVenta;
import com.proyecto.ordenventa.bean.ItemAddSalesOrder;
import com.proyecto.ordenventa.util.OrdrConstants;
import com.proyecto.sociosnegocio.ClienteBuscarActivity;
import com.proyecto.sociosnegocio.util.ClienteBuscarBean;
import com.proyecto.sociosnegocio.util.ContactoBuscarBean;
import com.proyecto.sociosnegocio.util.DireccionBuscarBean;
import com.proyecto.utils.Constantes;
import com.proyecto.utils.StringDateCast;
import com.proyecto.utils.Variables;
import com.proyecto.ventas.OrdenVentaFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;

public class OrdenVentaActivity extends AppCompatActivity implements IRVAdapterAddOrdenVenta {

    //region ADAPTER_VIEW_ELEMENTS
    private SharedPreferences mSharedPreferences;
    private RecyclerView mRecyclerView;
    private RVAdapterAddOrdenVenta mRVAdapter;
    private List<ItemAddSalesOrder> mListRows;
    //endregion

    //region OBJECT_ORDR_ELEMENTS
    private Location mCurrentLocation = null;
    private OrdenVentaBean mOrdenVenta;
    private List<OrdenVentaDetalleBean> mOrdenVentaDetalle;
    private ClienteBuscarBean mClienteSeleccionado;
    private ListaPrecioBean mListaPrecioSeleccionada;
    private CondicionPagoBean mCondicionPagoSeleccionada;
    private IndicadorBean mIndicadorSeleccionado;
    private DireccionBuscarBean mDireccionFiscalSel;
    private DireccionBuscarBean mDireccionEntregaSel;
    private ContactoBuscarBean mContactoSeleccionado;
    private MonedaBean mMonedaSeleccionada;

    private double mPorcentajeDescuento = 0, mTotalDocumento = 0, mMontoImpuesto = 0, mMontoDescuento = 0;
    //endregion

    //region LISTS_FOR_SELECTION_VALUES
    private List<MonedaBean> mListaMonedas;
    private List<ListaPrecioBean> mListaPrecios;
    private List<CondicionPagoBean> mListaCondPago;
    private List<TipoCambioBean> mListaTipoCambio;
    //endregion

    //region LAYOUT_ELEMENTS
    private TextView mTvTotalDocumento, mTVCantidadArticulos;
    //endregion

    private String mCodigoEmpleado, mNombreEmpleado, mIdDispositivo, mFechaActual, mNroDocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_venta);

        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.tlbAddSalesOrder);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.ttlAgregarOrdenVenta);

            mSharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            mCodigoEmpleado = mSharedPreferences.getString(Variables.CODIGO_EMPLEADO, "");
            mNombreEmpleado = mSharedPreferences.getString(Variables.NOMBRE_EMPLEADO, "");
            mIdDispositivo = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            mRecyclerView = (RecyclerView) findViewById(R.id.rvAddOrdr);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(OrdenVentaActivity.this));
            mRecyclerView.setHasFixedSize(true);

            mTvTotalDocumento = (TextView) findViewById(R.id.tvAddOrdrTotal);
            mTVCantidadArticulos = (TextView) findViewById(R.id.tvAddOrdrQtyItems);

            mRVAdapter = new RVAdapterAddOrdenVenta(OrdenVentaActivity.this);
            mRecyclerView.setAdapter(mRVAdapter);
            mListRows = new ArrayList<>();
        }catch (Exception e){
            showMessage("onCreate() > " + e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            initRecyclerView();
            mCurrentLocation = MapFunctions.getCurrentLocation(OrdenVentaActivity.this);
            mListaPrecios = new ListaPrecioDAO().listar();
            mListaMonedas = new MonedaDAO().listar();
            mListaTipoCambio = new TipoCambioDAO().listar();
        }catch (Exception e){
            showMessage("onStart() > " + e.getMessage());
        }
    }

    private void initRecyclerView(){
        try{
            mFechaActual = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(new Date());
            String fullDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(new Date());
            int nroNC = CorrelativoDAO.obtenerUltimoNumero("ORD");
            mNroDocumento = mIdDispositivo +"-"+fullDate+"-" + nroNC;

            if(mListRows == null || mListRows.size() == 0){
                mListRows = new ArrayList<>();
                mOrdenVenta = new OrdenVentaBean();
                mOrdenVentaDetalle = new ArrayList<>();

                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_NRO_DOCUMENTO,
                        getResources().getString(R.string.ORDR_NRO_DOCUMENTO), mNroDocumento, false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_COD_SOCIO_NEGOCIO,
                        getResources().getString(R.string.ORDR_COD_SOCIO_NEGOCIO), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_NOM_SOCIO_NEGOCIO,
                        getResources().getString(R.string.ORDR_NOM_SOCIO_NEGOCIO), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_CONTACTO_SOCIO,
                        getResources().getString(R.string.ORDR_CONTACTO_SOCIO), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_MONEDA,
                        getResources().getString(R.string.ORDR_MONEDA), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_EMPLEADO,
                        getResources().getString(R.string.ORDR_EMPLEADO), mNombreEmpleado,false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_COMENTARIOS,
                        getResources().getString(R.string.ORDR_COMENTARIOS), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_FECHA_CONTABLE,
                        getResources().getString(R.string.ORDR_FECHA_CONTABLE), StringDateCast.castStringtoDate(mFechaActual), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_FECHA_ENTREGA,
                        getResources().getString(R.string.ORDR_FECHA_ENTREGA), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_OPCION_ARTICULOS,
                        getResources().getString(R.string.ORDR_OPCION_ARTICULOS),
                        String.valueOf(mOrdenVentaDetalle.size()), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_DIRECCION_FISCAL,
                        getResources().getString(R.string.ORDR_DIRECCION_FISCAL), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_DIRECCION_ENTREGA,
                        getResources().getString(R.string.ORDR_DIRECCION_ENTREGA), true));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_CONDICION_PAGO,
                        getResources().getString(R.string.ORDR_CONDICION_PAGO), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_INDICADOR,
                        getResources().getString(R.string.ORDR_INDICADOR), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_TOTAL_SIN_DESCUENTO,
                        getResources().getString(R.string.ORDR_TOTAL_SIN_DESCUENTO), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_PORCENTAJE_DESCUENTO,
                        getResources().getString(R.string.ORDR_PORCENTAJE_DESCUENTO), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_IMPORTE_DESCUENTO,
                        getResources().getString(R.string.ORDR_IMPORTE_DESCUENTO), false));
                mListRows.add(new ItemAddSalesOrder(R.string.ORDR_IMPORTE_IMPUESTO,
                        getResources().getString(R.string.ORDR_IMPORTE_IMPUESTO), false));

                mRVAdapter.clearAndAddAll(mListRows);
                mTvTotalDocumento.setText(String.valueOf(mTotalDocumento));
                mTVCantidadArticulos.setText(String.valueOf(mOrdenVentaDetalle.size()));
            }else{
                iniciarValoresPorDefecto();
            }
        }catch (Exception e){
            showMessage("initRecyclerView() > " + e.getMessage());
        }
    }

    private void iniciarValoresPorDefecto(){
        try{
            mRVAdapter.notifyDataSetChanged();
        }catch(Exception e){
            showMessage("iniciarValoresPorDefecto() > " + e.getMessage());
        }
    }

    private void showMessage(String message){
        Toast.makeText(OrdenVentaActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(ItemAddSalesOrder itemRow) {
        try{
            switch (itemRow.getId()){
                case R.string.ORDR_COD_SOCIO_NEGOCIO:
                    startActivityForResult(new Intent(this, ClienteBuscarActivity.class),
                            ClienteBuscarActivity.REQUEST_CODE_BUSCAR_CLIENTE);
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            showMessage("onItemClick() > " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if(resultCode == RESULT_OK){
                switch (requestCode){
                    case ClienteBuscarActivity.REQUEST_CODE_BUSCAR_CLIENTE:
                        mClienteSeleccionado = data.getParcelableExtra(ClienteBuscarActivity.KEY_PARAM_CLIENTE);
                        if(mClienteSeleccionado != null){
                            setBusinessPartnerValues("", "", "", "");
                            mListaPrecioSeleccionada = mClienteSeleccionado.getListaPrecio();
                            mCondicionPagoSeleccionada = mClienteSeleccionado.getCondicionPago();
                            mIndicadorSeleccionado = mClienteSeleccionado.getIndicador();
                            setBusinessPartnerValues(mClienteSeleccionado.getCodigo(), mClienteSeleccionado.getNombre(),
                                    mCondicionPagoSeleccionada != null ? mCondicionPagoSeleccionada.getDescripcionCondicion() :"",
                                    mIndicadorSeleccionado != null? mIndicadorSeleccionado.getNombre() : "");
                            mPorcentajeDescuento = mClienteSeleccionado.getPorcentajeDescuento();

                            if(mClienteSeleccionado.getDireccionFiscalCodigo() != null) {
                                mDireccionFiscalSel = mClienteSeleccionado.getDireccionByCode(mClienteSeleccionado.getDireccionFiscalCodigo());
                                mRVAdapter.updateItem(R.string.ORDR_DIRECCION_FISCAL, mDireccionFiscalSel.obtenerDescripcion());
                            }

                            if(mClienteSeleccionado.getPersonaContacto() != null){
                                mContactoSeleccionado = mClienteSeleccionado.getContactoByCode(mClienteSeleccionado.getPersonaContacto());
                                mRVAdapter.updateItem(R.string.ORDR_CONTACTO_SOCIO, mContactoSeleccionado.getNombre());
                            }

                            if(mClienteSeleccionado.getMoneda() != null &&
                                    !mClienteSeleccionado.getMoneda().equals(OrdrConstants.CURRENCY_DEF)){
                                mMonedaSeleccionada = getCurrencyByCode(mClienteSeleccionado.getMoneda());
                                mRVAdapter.updateItem(R.string.ORDR_MONEDA, mMonedaSeleccionada != null?
                                        mMonedaSeleccionada.getDescripcion() : "", false);
                            }else if(mClienteSeleccionado.getMoneda() != null && mListaPrecioSeleccionada != null &&
                                    mClienteSeleccionado.getMoneda().equals(OrdrConstants.CURRENCY_DEF) &&
                                    mListaPrecioSeleccionada.getMoneda() != null && !mListaPrecioSeleccionada.getMoneda().equals("")){
                                mMonedaSeleccionada = getCurrencyByCode(mListaPrecioSeleccionada.getMoneda());
                                mRVAdapter.updateItem(R.string.ORDR_MONEDA, mMonedaSeleccionada != null?
                                        mMonedaSeleccionada.getDescripcion() : "", true);
                            }else if(mListaMonedas.size() > 0)
                            {
                                mMonedaSeleccionada = mListaMonedas.get(0);
                                mRVAdapter.updateItem(R.string.ORDR_MONEDA, mMonedaSeleccionada.getDescripcion());
                            }

                            setAutoShipTo();
                        }
                        break;
                }
            }
        }catch (Exception e){
            showMessage("onActivityResult() > " + requestCode + " > " + e.getMessage());
        }
    }

    private void setBusinessPartnerValues(String code, String name, String payTerms, String indic){
        try{
            mRVAdapter.updateItem(R.string.ORDR_COD_SOCIO_NEGOCIO, code);
            mRVAdapter.updateItem(R.string.ORDR_NOM_SOCIO_NEGOCIO, name);
            mRVAdapter.updateItem(R.string.ORDR_CONDICION_PAGO, payTerms);
            mRVAdapter.updateItem(R.string.ORDR_INDICADOR, indic);
        }catch (Exception e){
            showMessage("setBusinessPartnerValues() > " + e.getMessage());
        }
    }

    private void setAutoShipTo(){

        try{
            mCurrentLocation = MapFunctions.getCurrentLocation(OrdenVentaActivity.this);

            float bestDistance = -1;
            DireccionBuscarBean mDireccionEntregaTempSeleccionada = null;

            if(mClienteSeleccionado.getDirecciones() != null && mCurrentLocation != null){
                for (DireccionBuscarBean direccion: mClienteSeleccionado.getDirecciones()) {
                    if(direccion.getTipo().equals(Constantes.TIPO_DIRECCION_ENTREGA) &&
                            direccion.getLatitud() != null && direccion.getLongitud() != null &&
                            !TextUtils.isEmpty(direccion.getLatitud()) && !TextUtils.isEmpty(direccion.getLongitud())){

                        Location locationTo = new Location(GPS_PROVIDER);
                        locationTo.setLatitude(Double.parseDouble(direccion.getLatitud()));
                        locationTo.setLongitude(Double.parseDouble(direccion.getLongitud()));

                        float distance = mCurrentLocation.distanceTo(locationTo);
                        if(bestDistance == -1) {
                            bestDistance = distance;
                            mDireccionEntregaTempSeleccionada = direccion;
                        }else{
                            if(distance < bestDistance){
                                bestDistance = distance;
                                mDireccionEntregaTempSeleccionada = direccion;
                            }
                        }
                    }
                }

                if(mDireccionEntregaTempSeleccionada != null){
                    mDireccionEntregaSel = mDireccionEntregaTempSeleccionada;
                    mRVAdapter.updateItem(R.string.ORDR_DIRECCION_ENTREGA, mDireccionEntregaSel.obtenerDescripcion());
                    showMessage(String.format(getResources().getString(R.string.ORDR_SELECCION_DIRECCION_ENTREGA),
                            mDireccionEntregaSel.getCodigo(),
                            bestDistance * 0.001));
                }
            }
        }catch (Exception e){
            showMessage("setAutoShipTo() > " + e.getMessage());
        }
    }

    private MonedaBean getCurrencyByCode(String code){
        if(mListaMonedas != null){
            for (MonedaBean m: mListaMonedas) {
                if(m.getCodigo().equals(code)){
                    return m;
                }
            }
        }

        return null;
    }
}
