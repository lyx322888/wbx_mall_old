package com.wbx.mall.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wbx.mall.R;
import com.wbx.mall.activity.CookListActivity;
import com.wbx.mall.adapter.CookClassAdapter;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.CookInfo;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/8/24.
 */

public class CookBookFragment extends BaseFragment {
    @Bind(R.id.cook_book_recycler_view)
    RecyclerView recyclerView;
    private CookClassAdapter cookClassAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_cook_book;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void fillData() {
        List<CookInfo> cookList = (List<CookInfo>) getArguments().getSerializable("cookList");
        cookClassAdapter = new CookClassAdapter(cookList,getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(cookClassAdapter);
    }

    @Override
    protected void bindEvent() {
        cookClassAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                CookInfo item = cookClassAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), CookListActivity.class);
                intent.putExtra("cookClassId",item.getClassid());
                startActivity(intent);
            }
        });
    }
}
