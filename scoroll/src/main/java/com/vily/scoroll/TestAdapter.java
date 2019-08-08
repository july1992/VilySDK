package com.vily.scoroll;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/5
 *  
 **/
public class TestAdapter extends BaseQuickAdapter<String,BaseViewHolder> {



    public TestAdapter(  @Nullable List<String> data) {
        super(R.layout.adapter_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
