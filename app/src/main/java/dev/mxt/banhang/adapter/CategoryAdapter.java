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
import dev.mxt.banhang.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> categoryArrayList;
    private Context context;
    private OnCategoryListener onCategoryListener;

    public CategoryAdapter(ArrayList<Category> categoryArrayList, Context context, OnCategoryListener onCategoryListener) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;
        this.onCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.category_item, parent, false);
        return new ViewHolder(itemView, onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(R.drawable.bg_category).into(holder.backgroundImage);
        holder.brand.setText(categoryArrayList.get(position).getcName());
        holder.productQuantity.setText("Co " + categoryArrayList.get(position).getcTotalProduct() + " san pham");
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView backgroundImage;
        private TextView brand;
        private TextView productQuantity;
        private OnCategoryListener onCategoryListener;

        public ViewHolder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
            super(itemView);
            backgroundImage = (ImageView) itemView.findViewById(R.id.category_image);
            brand = (TextView) itemView.findViewById(R.id.phone_brand);
            productQuantity = (TextView) itemView.findViewById(R.id.product_quantity);

            this.onCategoryListener = onCategoryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryListener {
        void onCategoryClick(int position);
    }
}
