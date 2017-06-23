package com.tmbdnews.view.adapters;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.tmbdnews.R;
import com.tmbdnews.databinding.ItemFilmBinding;
import com.tmbdnews.model.TopRated;
import com.tmbdnews.view.handlers.Handlers;
import com.tmbdnews.viewmodel.RvFilmViewModel;

import java.util.List;

public class FilmsAdapter extends BaseRecyclerAdapter<ItemFilmBinding, RvFilmViewModel, FilmsAdapter.FilmViewHolder, TopRated> implements Handlers.FilmListAdapterHandlers {
    private ListItemClickListener listener;

    public FilmsAdapter(List<TopRated> item) {
        super(item);
    }

    @Override
    protected int initLayout() {
        return R.layout.item_film;
    }

    public void setListener(ListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RvFilmViewModel initViewModel() {
        return new RvFilmViewModel();
    }

    @Override
    public FilmViewHolder initHolder() {
        return new FilmViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        holder.getBinding().getVm().init(item.get(holder.getAdapterPosition()));
        holder.getBinding().getRoot().setTag(holder.getAdapterPosition());
        holder.getBinding().setHandlers(this);
    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public void onItemClick(View v) {
        listener.onListItemClick((Integer) v.getTag());
    }


    public interface ListItemClickListener {
        void onListItemClick(int position);
    }

    class FilmViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
        private final ItemFilmBinding binding;

        FilmViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.setVm(viewModel);
        }

        ItemFilmBinding getBinding() {
            return binding;
        }
    }

}
