package com.wzy.leftswiperemove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 王中阳 on 2016/11/28.
 * 邮箱 : wangzhongyang@maiyatian.com
 * 微信(QQ)：425772719
 */
public class CollectorListAdapter extends BaseAdapter {

    private List<MyBean> listItems;//数据集合
    private LayoutInflater layoutinflater;//视图容器，用来导入布局

    static class ViewHolder {
        private TextView tv_name;
    }

    /*
     * 实例化Adapter
     */
    public CollectorListAdapter(Context context, List<MyBean> dataSet) {
        this.listItems = dataSet;
        this.layoutinflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyBean device = listItems.get(position);
        ViewHolder holder;
        View view;
        if (convertView == null) {
            holder = new ViewHolder();
            //获取listitem布局文件
            view = layoutinflater.inflate(com.wzy.leftswiperemove.R.layout.collectorlist_listitem, null);

            //获取控件对象
            holder.tv_name = (TextView) view.findViewById(com.wzy.leftswiperemove.R.id.tv_name);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        //设置图片和文字
        holder.tv_name.setText(String.valueOf(device.getName()));

        return view;
    }

}