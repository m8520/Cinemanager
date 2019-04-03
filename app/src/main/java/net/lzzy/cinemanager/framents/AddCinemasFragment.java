package net.lzzy.cinemanager.framents;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddCinemasFragment extends BaseFragment {
    private String province="广西壮族自治区";
    private String city="柳州市";
    private String area="鱼峰区";
    private TextView tvArea;
    private EditText edtName;
    private OnFragmentTnteractionListener listener;
    private OnCinemaCretedListener cinemaListener;


    @Override
    protected void populate() {
        listener.hidesearch();
        tvArea = find(R.id.dialog_add_tv_area);
        edtName = find(R.id.dialog_add_cinema_edt_name);
        find(R.id.dialog_add_cinema_layout_area).setOnClickListener(v -> {
            JDCityPicker cityPicker = new JDCityPicker();
            cityPicker.init(getActivity());
            cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                    AddCinemasFragment.this.province = province.getName();
                    AddCinemasFragment.this.city = city.getName();
                    AddCinemasFragment.this.area = district.getName();
                    String loc = province.getName() + city.getName() + district.getName();
                    tvArea.setText(loc);

                }
            });
            cityPicker.showCityPicker();
        });
        find(R.id.dialog_add_cinema_btn_save).setOnClickListener(v -> {
            String name=edtName.getText().toString();
            if(TextUtils.isEmpty(name)){
                Toast.makeText(getActivity(),"要有名称",Toast.LENGTH_SHORT).show();
                return;
            }
            Cinema cinema=new Cinema();
            cinema.setName(name);
            cinema.setArea(area);
            cinema.setCity(city);
            cinema.setProvince(province);
            cinema.setLocation(tvArea.getText().toString());
            edtName.setText("");
            cinemaListener.saveCinema(cinema);
        });
        find(R.id.dialog_add_cinema_btn_cancel).setOnClickListener(v ->{cinemaListener.cancelAddCinema();
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_addcinemas;
    }

    @Override
    public void search(String kw) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            listener.hidesearch();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFragmentTnteractionListener) context;
            cinemaListener = (OnCinemaCretedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "必须实现OnFragmentTnteractionListener&OnCinemaCretedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        cinemaListener =null;
    }

    public interface OnCinemaCretedListener{
        /**
         *
         */
        void cancelAddCinema();


        void saveCinema(Cinema cinema);
    }

}
