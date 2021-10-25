package com.multiventas.app.activity;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.multiventas.app.databinding.PujaActivaListItemBinding;
import com.multiventas.app.dto.PujaActivaOverview;


public class PujaactivaAdapter extends ListAdapter<PujaActivaOverview, PujaactivaAdapter.pujaActivaViewHolder> {

    private PujaactivaAdapter.OnItemClickListener onItemClickListener;

    protected PujaactivaAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(PujaActivaOverview pujaActivaOverview);
    }

    public void setOnItemClickListener(PujaactivaAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<PujaActivaOverview> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PujaActivaOverview>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull PujaActivaOverview oldPujaActiva, @NonNull PujaActivaOverview newPujaActiva) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldPujaActiva.getIdVentaDetallada().equals(newPujaActiva.getIdVentaDetallada());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull PujaActivaOverview oldPujaActiva, @NonNull PujaActivaOverview newPujaActiva) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldPujaActiva.equals(newPujaActiva);
                }
            };

    public PujaactivaAdapter.pujaActivaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PujaActivaListItemBinding binding = PujaActivaListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new PujaactivaAdapter.pujaActivaViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull PujaactivaAdapter.pujaActivaViewHolder holder, int position) {
        PujaActivaOverview pujaActivaOverview = getItem(position);

        holder.bind(pujaActivaOverview);
    }

    class pujaActivaViewHolder extends RecyclerView.ViewHolder {

        private final PujaActivaListItemBinding binding;

        public pujaActivaViewHolder(@NonNull PujaActivaListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PujaActivaOverview pujaActivaOverview) {

            binding.codigoProducto.setText(pujaActivaOverview.getcProducto());
            binding.nickname.setText(pujaActivaOverview.getNickname());
            binding.tituloProducto.setText(pujaActivaOverview.getTituloProducto());

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(pujaActivaOverview);
            });

            binding.executePendingBindings();

        }
    }
}
