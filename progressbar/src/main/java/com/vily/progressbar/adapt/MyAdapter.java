package com.vily.progressbar.adapt;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vily.progressbar.R;

import java.util.List;

public class MyAdapter extends BaseQuickAdapter<Student,BaseViewHolder> {

    public MyAdapter( List<Student> data) {
        super(R.layout.adapt_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {

    }
}
