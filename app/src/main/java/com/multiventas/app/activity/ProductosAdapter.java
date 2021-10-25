package com.multiventas.app.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.multiventas.app.databinding.ProductoListItemBinding;
import com.multiventas.app.entity.Productos;

public class ProductosAdapter extends ListAdapter<Productos, ProductosAdapter.productoViewHolder> {
    private OnItemClickListener onItemClickListener;
    protected ProductosAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(Productos productos);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<Productos> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Productos>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Productos oldProductos, @NonNull Productos newProductos) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldProductos.getIdProducto().equals(newProductos.getIdProducto());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Productos oldProductos, @NonNull Productos newProductos) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldProductos.equals(newProductos);
                }
            };
    public ProductosAdapter.productoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductoListItemBinding binding = ProductoListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new productoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosAdapter.productoViewHolder holder, int position) {
        Productos productos = getItem(position);

        holder.bind(productos);
    }

    class productoViewHolder extends RecyclerView.ViewHolder {

        private final ProductoListItemBinding binding;

        public productoViewHolder(@NonNull ProductoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Productos productos) {

            binding.titulo.setText(productos.getTitulo());
            binding.codigoProducto.setText(productos.getCodigoProducto());
            binding.precio.setText(String.valueOf(productos.getPrecio()));

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(productos);
            });

            binding.executePendingBindings();

        }
    }
}
