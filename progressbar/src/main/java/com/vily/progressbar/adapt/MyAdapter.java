package com.vily.progressbar.adapt;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vily.progressbar.R;

import java.util.List;

/**
 *    section
 */
public class MyAdapter extends BaseSectionQuickAdapter<MySection,BaseViewHolder> {

    public MyAdapter( List<MySection> data) {
        super(R.layout.adapt_item, R.layout.adapt_head, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {

        TextView tv_data = helper.getView(R.id.tv_data);
        tv_data.setText(item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {


        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.t.getName());

    }

//    public MyAdapter( List<Student> data) {
//        super(R.layout.adapt_item, data);
//    }



}
