package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.framents.AddCinemasFragment;
import net.lzzy.cinemanager.framents.AddOrderFrament;
import net.lzzy.cinemanager.framents.BaseFragment;
import net.lzzy.cinemanager.framents.CinemasFragment;
import net.lzzy.cinemanager.framents.OnFragmentTnteractionListener;
import net.lzzy.cinemanager.framents.OrderFragment;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.cinemanager.utils.ViewUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;
import net.lzzy.sqllib.GenericAdapter;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnFragmentTnteractionListener,AddCinemasFragment.OnCinemaCretedListener{
    private FragmentManager manager=getSupportFragmentManager();
    private View layoutMenu;
    private TextView tvTitle;
    private SearchView search;
    private SparseArray<String> titleArrat=new SparseArray<>();
    private SparseArray<Fragment> fragmentArray = new SparseArray<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏 **/
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setTitleMenu();
        search.setOnQueryTextListener(new ViewUtils.AbstractQueryHandler(){
            @Override
            public boolean handleQuery(String kw) {
                Fragment fragment = manager.findFragmentById(R.id.fragment_container);
                if (fragment!=null){
                    if (fragment instanceof BaseFragment){
                        ((BaseFragment)fragment).search(kw);
                    }
                }
                return true;
            }
        });
    }

    /** 标题栏 **/
    private void setTitleMenu() {
        titleArrat.put(R.id.bar_title_tv_add_cinema,"添加影院");
        titleArrat.put(R.id.bar_title_tv_view_cinema,"影院列表");
        titleArrat.put(R.id.bar_title_tv_add_order,"添加订单");
        titleArrat.put(R.id.bar_title_tv_view_search,"我的订单");
        layoutMenu = findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_img_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible=layoutMenu.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE;
                layoutMenu.setVisibility(visible);
            }
        });
        tvTitle = findViewById(R.id.bar_title_tv_title);
        tvTitle.setText(R.string.bar_title_menu_orders);
        search = findViewById(R.id.main_sv_search);
        findViewById(R.id.bar_title_tv_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_search).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }


    /** 对标题栏的点击监听 **/
    @Override
    public void onClick(View v) {
        layoutMenu.setVisibility(View.GONE);
        tvTitle.setText(titleArrat.get(v.getId()));
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = fragmentArray.get(v.getId());
        if (fragment == null) {
            fragment = createFragment(v.getId());
            fragmentArray.put(v.getId(), fragment);
            transaction.add(R.id.fragment_container, fragment);
        }
        for (Fragment f : manager.getFragments()){
            transaction.hide(f);
        }
        transaction.show(fragment).commit();
    }

    private Fragment createFragment(int id){
        switch (id) {
            case R.id.bar_title_tv_add_cinema:
                return new AddCinemasFragment();

            case R.id.bar_title_tv_view_cinema:
                return new CinemasFragment();

            case R.id.bar_title_tv_add_order:
                return new AddOrderFrament();

            case R.id.bar_title_tv_view_search:
                return new OrderFragment();
            default:
                return new OrderFragment();
        }
    }


    @Override
    public void hidesearch() {
        search.setVisibility(View.INVISIBLE);
    }

    /**
     * 保存
     */
    @Override
    public void cancelAddCinema() {
        Fragment addCinemaSFragment = fragmentArray.get(R.id.bar_title_tv_add_cinema);
        if (addCinemaSFragment == null) {
            return;
        }
        Fragment cinemasFragment = fragmentArray.get(R.id.bar_title_tv_view_cinema);
        FragmentTransaction transaction = manager.beginTransaction();
        if (cinemasFragment == null) {
            cinemasFragment = new CinemasFragment();
            fragmentArray.put(R.id.bar_title_tv_view_cinema, cinemasFragment);
            transaction.add(R.id.fragment_container, cinemasFragment);
        }
        transaction.hide(addCinemaSFragment).show(cinemasFragment).commit();
        tvTitle.setText(titleArrat.get(R.id.bar_title_tv_view_cinema));
    }

    /**
     *取消
     */
    @Override
    public void saveCinema(Cinema cinema) {
        Fragment addCinemaFrament = fragmentArray.get(R.id.bar_title_tv_add_cinema);
        if (addCinemaFrament == null){
            return;
    }
        Fragment cinemasFragment = fragmentArray.get(R.id.bar_title_tv_view_cinema);
        FragmentTransaction transaction = manager.beginTransaction();
        if (cinemasFragment ==null) {
            //创建CinemaFragment同时要传Cinema对象进来
            cinemasFragment = new CinemasFragment(cinema);
            fragmentArray.put(R.id.bar_title_tv_view_cinema, cinemasFragment);
            transaction.add(R.id.fragment_container, cinemasFragment);
        }else {
            ((CinemasFragment) cinemasFragment).save(cinema);
        }
        transaction.hide(addCinemaFrament).show(cinemasFragment).commit();
        tvTitle.setText(titleArrat.get(R.id.bar_title_tv_view_cinema));
        }
    }


