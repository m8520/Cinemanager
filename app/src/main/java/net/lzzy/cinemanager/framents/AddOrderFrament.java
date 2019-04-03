package net.lzzy.cinemanager.framents;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.simpledatepicker.CustomDatePicker;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrderFrament extends BaseFragment {
    private OnFragmentTnteractionListener searchListener;

    private CustomDatePicker picker;
    private TextView tvDate;
    private Spinner edtName;
    private EditText editText;
    private ImageView imgQRCude;
    private List<Cinema> cinemas;

    @Override
    protected void populate() {
        searchListener.hidesearch();

        cinemas= CinemaFactory.getInstance().get();

        TextView tv = find(R.id.fragment_order_tv);

    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_addorder;
    }

    @Override
    public void search(String kw) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            searchListener.hidesearch();
        super.onHiddenChanged(hidden);
    }

    /**
     *
     *
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            searchListener = (OnFragmentTnteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "必须实现OnFragmentTnteractionListener");
        }
    }

    /**
     * 销毁
     */
    @Override
    public void onDetach() {
        super.onDetach();
        searchListener = null;
    }
}
