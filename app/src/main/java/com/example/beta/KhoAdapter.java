package com.example.beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class KhoAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Kho> mListKho;
    private int mLayoutResId;
    private boolean isDeleting = false;
    public KhoAdapter(Context context, int layoutResId, ArrayList<Kho> listKho) {
        this.mContext = context;
        this.mListKho = listKho;
        this.mLayoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        return mListKho.size();
    }

    @Override
    public Kho getItem(int position) {
        return mListKho.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);

            holder = new ViewHolder();
            holder.tvTenKho = view.findViewById(R.id.tv_ten_kho);
            holder.tvDiaChi = view.findViewById(R.id.tv_dia_chi);
            holder.tvMaKho = view.findViewById(R.id.tv_ma_kho);
            holder.tvTaiTrong = view.findViewById(R.id.tv_tai_trong);
            holder.cbSelected = view.findViewById(R.id.cb_selected);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Kho kho = mListKho.get(position);

        if (holder.tvTenKho != null && holder.tvDiaChi != null && kho != null) {
            holder.tvTenKho.setText(String.format("Tên kho: %s", kho.getTenKho()));
            holder.tvDiaChi.setText(String.format("Địa chỉ: %s", kho.getDiaChi()));
            holder.tvMaKho.setText(String.format("Mã kho: %s", kho.getMaKho()));
            float taiTrong = kho.getTaiTrong();
            holder.tvTaiTrong.setText(String.format("Tải trọng: %.2f", taiTrong));
        }

        // Set the checkbox state and listener
        holder.cbSelected.setChecked(kho.isSelected());
        holder.cbSelected.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Get the clicked item
                Kho kho = mListKho.get(position);

                // Update the selected state of the item
                kho.setSelected(isChecked);

                if (isChecked && isDeleting) {
                    // Delete the item from the list
                    mListKho.remove(kho);
                    notifyDataSetChanged();
                }
            }
        });


        return view;
    }

    public ArrayList<Kho> getList() {
        return mListKho;
    }

    static class ViewHolder {
        TextView tvTenKho;
        TextView tvDiaChi;
        TextView tvMaKho;
        TextView tvTaiTrong;
        CheckBox cbSelected;
    }

    public void add(Kho kho) {

        mListKho.add(kho);
        notifyDataSetChanged();
    }

    public void remove(Kho kho) {
        mListKho.remove(kho);
    }



    public void removeAll(List<Kho> items) {
        mListKho.removeAll(items);
        notifyDataSetChanged();
    }


}
