package net.lzzy.cinemanager.framents;

import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.GeneratedAdapter;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;
import java.util.PrimitiveIterator;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinemasFragment extends BaseFragment {

    private ListView lv;
    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
    private Cinema cinema;
    private GenericAdapter<Cinema> adapter1;

    public CinemasFragment() {
    }

    public CinemasFragment(Cinema cinema) {
        this.cinema = cinema;
    }


    @Override
    protected void populate() {
        lv = find(R.id.activity_cinema_lv);
        View empty = find(R.id.activity_cinemas_tv_none);
        lv.setEmptyView(empty);
        cinemas = factory.get();
        adapter1 = new GenericAdapter<Cinema>(getActivity(),
                R.layout.cinemas_item, cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema) {
                holder.setTextView(R.id.cinemas_items_tv_name, cinema.getName())
                        .setTextView(R.id.cinemas_items_tv_location, cinema.getLocation());
            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }
        };
        lv.setAdapter(adapter1);
        if (cinema!=null){
            save(cinema);
        }

    }

    public void save(Cinema cinema) {
        adapter1.add(cinema);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String kw) {
        cinemas.clear();
        if (TextUtils.isEmpty(kw)){
            cinemas.addAll(factory.get());
        }else {
            cinemas.addAll(factory.searchCinemas(kw));
        }
        adapter1.notifyDataSetChanged();
    }


}


