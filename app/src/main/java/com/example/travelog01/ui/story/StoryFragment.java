package com.example.travelog01.ui.story;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog01.Adapter.DiaryAdapter;
import com.example.travelog01.Database.DatabaseHelper;
import com.example.travelog01.DetailActivity;
import com.example.travelog01.Model.DiaryBean;

import com.example.travelog01.R;
import com.example.travelog01.write_activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StoryFragment extends Fragment implements DiaryAdapter.OnItemClickListener {

    private StoryViewModel mViewModel;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private RecyclerView mRecyclerView;
    private DiaryAdapter mDiaryAdapter;
    private ArrayList<DiaryBean> mList = new ArrayList<>();

    DatabaseHelper diaryDb;

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.story_fragment, container, false);
        mRecyclerView=view.findViewById(R.id.diary_xrv_list);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        // TODO: Use the ViewModel
        setHasOptionsMenu(true);
        // enable the floating action button to add new story
        FloatingActionButton button = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), write_activity.class);
                getActivity().startActivity(intent);
            }
        });

        mDiaryAdapter=new DiaryAdapter(mList,getActivity());
        //mDiaryAdapter.setmOnItemClickListener(this);
        mRecyclerView.setAdapter(mDiaryAdapter);
        diaryDb = new DatabaseHelper(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mList.clear();
        getAllData();
    }

    private void getAllData(){
        List<DiaryBean> list=diaryDb.getAllData();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
        }
        mDiaryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.length() > 0) {
                        Log.e("onQueryTextChange", "我是点击回车按钮");
                        searchView.setIconified(true);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //Log.i("onQueryTextChange", newText);
                    Log.e("onQueryTextChange","我是内容改变");
                    //return true;
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_bar:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(DiaryBean diary) {

    }
}


