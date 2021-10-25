package com.multiventas.app.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.multiventas.app.databinding.VentadetalladaListItemBinding;
import com.multiventas.app.entity.Ventadetallada;


public class VentadetalladaAdapter extends ListAdapter<Ventadetallada, VentadetalladaAdapter.ventadetalladaViewHolder> {
    private VentadetalladaAdapter.OnItemClickListener onItemClickListener;
    protected VentadetalladaAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(Ventadetallada ventadetallada);
    }

    public void setOnItemClickListener(VentadetalladaAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<Ventadetallada> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Ventadetallada>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Ventadetallada oldVentadetallada, @NonNull Ventadetallada newVentadetallada) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldVentadetallada.getIdVentaDetallada().equals(newVentadetallada.getIdVentaDetallada());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Ventadetallada oldVentadetallada, @NonNull Ventadetallada newVentadetallada) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldVentadetallada.equals(newVentadetallada);
                }
            };

    public VentadetalladaAdapter.ventadetalladaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VentadetalladaListItemBinding binding = VentadetalladaListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new VentadetalladaAdapter.ventadetalladaViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull VentadetalladaAdapter.ventadetalladaViewHolder holder, int position) {
        Ventadetallada Ventadetalladadetallada = getItem(position);

        holder.bind(Ventadetalladadetallada);
    }

    class ventadetalladaViewHolder extends RecyclerView.ViewHolder {

        private final VentadetalladaListItemBinding binding;

        public ventadetalladaViewHolder(@NonNull VentadetalladaListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ventadetallada ventadetallada) {

            binding.codigoProducto.setText(ventadetallada.getCodigoProducto());
            binding.precio.setText(String.valueOf(ventadetallada.getPrecio()));
            binding.titulo.setText(ventadetallada.getTitulo());

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(ventadetallada);
            });

            binding.executePendingBindings();

        }
    }
}