package dev.mxt.banhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import dev.mxt.banhang.R;
import dev.mxt.banhang.model.Smartphone;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.HorizontalViewHolder> {

    private Context context;
    private ArrayList<Smartphone> smartphones;
    private Integer layout = null;
    private OnPhoneListener mOnPhoneListener;

    public PhoneAdapter(ArrayList<Smartphone> smartphone, Context context, OnPhoneListener onPhoneListener, int layout) {
        this.context = context;
        this.smartphones = smartphone;
        this.layout = layout;
        this.mOnPhoneListener = onPhoneListener;
    }

    @NonNull
    @Override
    public PhoneAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(layout, parent, false);
        return new HorizontalViewHolder(itemView, mOnPhoneListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        Picasso.get().load(smartphones.get(position).getProAvatar()).into(holder.img);
        holder.brandName.setText(smartphones.get(position).getCName());
        holder.name.setText(smartphones.get(position).getProName());
        holder.price.setText(Integer.toString(smartphones.get(position).getProPrice()));
    }

    @Override
    public int getItemCount() {
        return smartphones.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView name;
        private TextView brandName;
        private TextView price;

        private OnPhoneListener onPhoneListener;

        public HorizontalViewHolder(@NonNull View itemView, OnPhoneListener onPhoneListener) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            brandName = (TextView) itemView.findViewById(R.id.brand);
            price = (TextView) itemView.findViewById(R.id.price);

            this.onPhoneListener = onPhoneListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPhoneListener.onPhoneClick(getAdapterPosition());
        }
    }

    public interface OnPhoneListener{
        void onPhoneClick(int position);
    }
}
