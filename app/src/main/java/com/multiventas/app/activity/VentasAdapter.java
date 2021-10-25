package com.multiventas.app.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.multiventas.app.databinding.VentasListItemBinding;
import com.multiventas.app.dto.Venta;


public class VentasAdapter extends ListAdapter<Venta, VentasAdapter.ventasViewHolder> {
    private VentasAdapter.OnItemClickListener onItemClickListener;
    protected VentasAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(Venta ventas);
    }

    public void setOnItemClickListener(VentasAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<Venta> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Venta>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Venta oldVentas, @NonNull Venta newVentas) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldVentas.getIdVentas().equals(newVentas.getIdVentas());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Venta oldVentas, @NonNull Venta newVentas) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldVentas.equals(newVentas);
                }
            };
    public VentasAdapter.ventasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VentasListItemBinding binding = VentasListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new VentasAdapter.ventasViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull VentasAdapter.ventasViewHolder holder, int position) {
        Venta ventas = getItem(position);

        holder.bind(ventas);
    }

    class ventasViewHolder extends RecyclerView.ViewHolder {

        private final VentasListItemBinding binding;

        public ventasViewHolder(@NonNull VentasListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Venta ventas) {

            binding.costoEnvio.setText(String.valueOf(ventas.getCostoEnvio()));
            binding.costoTotal.setText(String.valueOf(ventas.getCostoTotal()));
            binding.nickname.setText(ventas.getNickname());

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(ventas);
            });

            binding.executePendingBindings();

        }
    }
}
