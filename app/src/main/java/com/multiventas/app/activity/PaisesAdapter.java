package com.multiventas.app.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.multiventas.app.databinding.PaisesListItemBinding;
import com.multiventas.app.entity.Paises;

public class PaisesAdapter  extends ListAdapter<Paises, PaisesAdapter.paisesViewHolder>{
    private PaisesAdapter.OnItemClickListener onItemClickListener;
    protected PaisesAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(Paises paises);
    }


    public void setOnItemClickListener(PaisesAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<Paises> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Paises>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Paises oldPaises, @NonNull Paises newPaises) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldPaises.getIdPais().equals(newPaises.getIdPais());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Paises oldPaises, @NonNull Paises newPaises) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldPaises.equals(newPaises);
                }
            };
    public PaisesAdapter.paisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaisesListItemBinding binding = PaisesListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new PaisesAdapter.paisesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisesAdapter.paisesViewHolder holder, int position) {
        Paises paises = getItem(position);

        holder.bind(paises);
    }

    class paisesViewHolder extends RecyclerView.ViewHolder {

        private final PaisesListItemBinding binding;

        public paisesViewHolder(@NonNull PaisesListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Paises paises) {

            binding.Pais.setText(paises.getPais());
            binding.precioExcento.setText(String.valueOf(paises.getPrecioExcento()));
            binding.valorEnvio.setText(String.valueOf(paises.getValorEnvio()));

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(paises);
            });

            binding.executePendingBindings();

        }
    }
}
