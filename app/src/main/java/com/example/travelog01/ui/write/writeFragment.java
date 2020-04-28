package com.example.travelog01.ui.write;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.travelog01.R;
import com.example.travelog01.datepicker.CustomDatePicker;
import com.example.travelog01.write_activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//implements XRecyclerView.LoadingListener
public class writeFragment extends Fragment {

    //@BindView(R.id.diary_xrv_list)
    //XRecyclerView diaryXRvList;
    //@BindView(R.id.diary_fab_add)
    ////FloatingActionButton diaryFabAdd;
    //Unbinder unbinder;

    private static final int REQUEST_BACK = 1000;
    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mTimerPicker;
    public LinearLayout lDate;
    public LinearLayout lTime;
    private TextView textView;

    private WriteViewModel mViewModel;
    FloatingActionButton fab;

    public static writeFragment newInstance() {
        return new writeFragment();
    }

    //@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.write_fragment, container, false);
        View view = inflater.inflate(R.layout.write_fragment, container, false);

       /* fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), write_activity.class));
            }
        });*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WriteViewModel.class);
        // TODO: Use the ViewModel
       Button button = (Button) getActivity().findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), write_activity.class);
                    getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //unbinder = ButterKnife.bind(this, view);
        //initView();
    }
/*
    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(), write_activity.class), REQUEST_BACK);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        //diaryXRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        //diaryXRvList.setLoadingListener(this);
    }

    @Override
    public void onRefresh() {
        diaryXRvList.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        diaryXRvList.loadMoreComplete();
    }
    */

}
