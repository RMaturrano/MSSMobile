package com.proyecto.ordenventa.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyect.movil.R;
import com.proyecto.ordenventa.adapter.recyclerview.listeners.IRVAdapterAddOrdenVenta;
import com.proyecto.ordenventa.bean.ItemAddSalesOrder;

import java.util.ArrayList;
import java.util.List;

public class RVAdapterAddOrdenVenta  extends RecyclerView.Adapter<RVAdapterAddOrdenVenta.RVAdapterAddOrdenVentaViewHolder>{

    private List<ItemAddSalesOrder> mList;
    private IRVAdapterAddOrdenVenta mIRVListener;

    public RVAdapterAddOrdenVenta(IRVAdapterAddOrdenVenta listener){
        mList = new ArrayList<>();
        mIRVListener = listener;
    }

    public void addItem(ItemAddSalesOrder item){
        if(!updateItem(item.getId(), item.getTitulo())) {
            mList.add(item);
            notifyItemInserted(mList.size() - 1);
        }
    }

    public String getItem(String title){
        String result = "";
        for (ItemAddSalesOrder item: this.mList) {
            if(title.equals(item.getTitulo())) {
                result = item.getContenido();
                break;
            }
        }

        return result;
    }

    public boolean updateItem(int id, String content){
        boolean res = false;
        for (ItemAddSalesOrder item: this.mList) {
            if(id == item.getId()) {
                item.setContenido(content);
                notifyDataSetChanged();
                res = true;
                break;
            }
        }

        return res;
    }

    public boolean updateItem(int id, String content, boolean isEditable){
        boolean res = false;
        for (ItemAddSalesOrder item: this.mList) {
            if(id == item.getId()) {
                item.setContenido(content);
                item.setEditable(isEditable);
                notifyDataSetChanged();
                res = true;
                break;
            }
        }

        return res;
    }

    public void clearAndAddAll(List<ItemAddSalesOrder> lstItems) {
        mList.clear();
        mList.addAll(lstItems);
        notifyDataSetChanged();
    }

    @Override
    public RVAdapterAddOrdenVenta.RVAdapterAddOrdenVentaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapterAddOrdenVenta.RVAdapterAddOrdenVentaViewHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.rv_add_sales_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RVAdapterAddOrdenVenta.RVAdapterAddOrdenVentaViewHolder holder, int position) {
        ItemAddSalesOrder item = mList.get(position);

        holder.tvTitulo.setText(item.getTitulo());
        holder.tvContenido.setText(item.getContenido());

        if(item.isEditable()){
            holder.ivPuntero.setImageResource(R.drawable.ic_chevron_right_24dp);
        }else{
            holder.ivPuntero.setImageResource(R.drawable.ic_chevron_right_24dp_white);
        }

        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(position);
    }

    private View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIRVListener.onItemClick(mList.get((int) v.getTag()));
        }
    };

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class RVAdapterAddOrdenVentaViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitulo, tvContenido;
        private ImageView ivPuntero;

        public RVAdapterAddOrdenVentaViewHolder(View itemView) {
            super(itemView);

            tvTitulo = (TextView) itemView.findViewById(R.id.tvAddOrdrTitulo);
            tvContenido = (TextView) itemView.findViewById(R.id.tvAddOrdrDescripcion);
            ivPuntero = (ImageView) itemView.findViewById(R.id.ivAddOrdrPuntero);
        }
    }


}
