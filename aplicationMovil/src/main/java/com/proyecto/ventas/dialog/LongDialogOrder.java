package com.proyecto.ventas.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.proyect.movil.R;
import com.proyecto.bean.DialogBean;
import com.proyecto.bean.OrdenVentaBean;
import com.proyecto.conectividad.Connectivity;
import com.proyecto.dao.OrdenVentaDAO;
import com.proyecto.database.Insert;
import com.proyecto.database.Select;
import com.proyecto.utils.Constantes;
import com.proyecto.utils.CustomDialogPedido;
import com.proyecto.utils.ListViewCustomAdapterImgAndLine;
import com.proyecto.ws.InvocaWS;
import com.proyecto.ws.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LongDialogOrder {

    private static final String ENVIAR_AL_SERVIDOR = "Enviar al servidor";
    private static final String ELIMINAR_BORRADOR = "Eliminar borrador";
    private Context contexto;
    private String res = "";
    private OrdenVentaBean ov = null;

    @SuppressLint("InflateParams")
    public Dialog CreateGroupDialog(final Context context, final String key)
    {
        contexto = context;

        final boolean wifi = Connectivity.isConnectedWifi(contexto);
        final boolean movil = Connectivity.isConnectedMobile(contexto);
        final boolean isConnectionFast = Connectivity.isConnectedFast(contexto);

        ArrayList<DialogBean> elements = new ArrayList<DialogBean>();
        DialogBean b = new DialogBean();

        b = new DialogBean();
        b.setDescription(ENVIAR_AL_SERVIDOR);
        b.setImage(R.drawable.ic_send_gray_24dp);
        elements.add(b);

        b = new DialogBean();
        b.setDescription(ELIMINAR_BORRADOR);
        b.setImage(R.drawable.ic_delete_forever_silver_24dp);
        elements.add(b);

        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.custom_dialog, null);

        final Dialog dialog = new Dialog(context);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        toolbar.setTitle("Acciones");

        ListView listView = (ListView) view.findViewById(R.id.group_listview);
        ListViewCustomAdapterImgAndLine customAdapter = new ListViewCustomAdapterImgAndLine(context, elements);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if(position == 0){

                    Select select = new Select(context);
                    ov = new OrdenVentaBean();
                    ov = select.obtenerOrdenVenta(key);
                    select.close();

                    if(wifi || movil && isConnectionFast){

                        Toast.makeText(contexto, "Preparando...", Toast.LENGTH_SHORT).show();
                        addDocument(ov);
                        dialog.dismiss();

                    }else{
                        Toast.makeText(contexto, "No está conectado a ninguna red de datos", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }else if(position == 1){
                    new OrdenVentaDAO().eliminarOrdenVenta(key);
                    Toast.makeText(contexto, "El borrador fue eliminado...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        });

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);

        return dialog;
    }


    private void addDocument(final OrdenVentaBean ov){

        final Insert insert = new Insert(contexto);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(contexto);
        String ip = mSharedPreferences.getString("ipServidor", Constantes.DEFAULT_IP);
        String port = mSharedPreferences.getString("puertoServidor", Constantes.DEFAULT_PORT);
        String sociedad = mSharedPreferences.getString("sociedades", "-1");
        String ruta = "http://" + ip + ":" + port + "/MSS_MOBILE/service/";

        JSONObject jsonObject = OrdenVentaBean.transformOVToJSON(ov, sociedad);

        if(jsonObject != null){
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.POST, ruta + "salesorder/addSalesOrder.xsjs", jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try
                                    {
                                        if(response.getString("ResponseStatus").equals("Success")){
                                            insert.updateEstadoOrdenVenta(ov.getClaveMovil());
                                            showToast("Enviado al servidor con exito.");
                                        }else{
                                            String messageError = response.getJSONObject("Response")
                                                    .getJSONObject("message")
                                                    .getString("value");
                                            showToast(messageError);
                                        }

                                    }catch (Exception e){showToast("Response - " + e.getMessage());}
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {showToast("VolleyError - " + error.getMessage());}
                            }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
                    };
            VolleySingleton.getInstance(contexto).addToRequestQueue(jsonObjectRequest);
        }else{
            showToast("Error convirtiendo a JSON los datos del documento.");
        }
    }

    private  void showToast(String message){
        Toast.makeText(contexto, message, Toast.LENGTH_SHORT).show();
    }
}
