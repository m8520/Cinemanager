package net.lzzy.cinemanager.framents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrderFrament extends Fragment {
    public AddOrderFrament(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cinemas,null);
        TextView textView=view.findViewById(R.id.fragment_addCinemas_tv);
        return view;
    }

}
