package com.multiventas.app.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.multiventas.app.databinding.PujasListItemBinding;
import com.multiventas.app.entity.Pujas;
import com.multiventas.app.utils.Converters;

public class PujasAdapter extends ListAdapter<Pujas, PujasAdapter.pujasViewHolder> {
    private PujasAdapter.OnItemClickListener onItemClickListener;

    protected PujasAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(Pujas pujas);
    }

    public void setOnItemClickListener(PujasAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<Pujas> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Pujas>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Pujas oldPujas, @NonNull Pujas newPujas) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return  oldPujas.getIdPujas().equals(newPujas.getIdPujas());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Pujas oldPujas, @NonNull Pujas newPujas) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldPujas.equals(newPujas);
                }
            };
    public PujasAdapter.pujasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PujasListItemBinding binding = PujasListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new PujasAdapter.pujasViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull PujasAdapter.pujasViewHolder holder, int position) {
        Pujas pujas = getItem(position);

        holder.bind(pujas);
    }

    class pujasViewHolder extends RecyclerView.ViewHolder {

        private final PujasListItemBinding binding;

        public pujasViewHolder(@NonNull PujasListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Pujas pujas) {

            binding.titulo.setText(pujas.getTitulo());
            binding.fechaFinal.setText(Converters.fromTimestamp(pujas.getFechaFinal()));
            binding.fechaPriAbo.setText(Converters.fromTimestampWithOutHour(pujas.getFecPriAbo()));

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(pujas);
            });

            binding.executePendingBindings();

        }
    }
}