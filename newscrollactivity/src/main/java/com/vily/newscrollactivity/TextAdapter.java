package com.vily.newscrollactivity;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/
public class TextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TextAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
