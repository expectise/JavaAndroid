package com.multiventas.app.activity;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.multiventas.app.databinding.UsuariosListItemBinding;
import com.multiventas.app.entity.Usuarios;

public class UsuariosAdapter  extends ListAdapter<Usuarios, UsuariosAdapter.usuariosViewHolder> {
    private UsuariosAdapter.OnItemClickListener onItemClickListener;
    protected UsuariosAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(Usuarios usuarios);
    }

    public void setOnItemClickListener(UsuariosAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static final DiffUtil.ItemCallback<Usuarios> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Usuarios>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Usuarios oldUsuarios, @NonNull Usuarios newUsuarios) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUsuarios.getIdUsuarios().equals(newUsuarios.getIdUsuarios());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Usuarios oldUsuarios, @NonNull Usuarios newUsuarios) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUsuarios.equals(newUsuarios);
                }
            };

    public UsuariosAdapter.usuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UsuariosListItemBinding binding = UsuariosListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
       return new UsuariosAdapter.usuariosViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosAdapter.usuariosViewHolder holder, int position) {
        Usuarios usuarios = getItem(position);

        holder.bind(usuarios);
    }

    class usuariosViewHolder extends RecyclerView.ViewHolder {

        private final UsuariosListItemBinding binding;

        public usuariosViewHolder(@NonNull UsuariosListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Usuarios usuarios) {

            binding.emailT.setText(usuarios.getEmail());
            binding.nickNameT.setText(usuarios.getNickname());
            binding.nombresT.setText(usuarios.getNombre() + " " + usuarios.getApmaterno() + " " + usuarios.getApmaterno());

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(usuarios);
            });

            binding.executePendingBindings();

        }
    }
}
