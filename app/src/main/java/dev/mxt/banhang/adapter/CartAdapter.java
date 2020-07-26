package dev.mxt.banhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dev.mxt.banhang.R;
import dev.mxt.banhang.activity.CartActivity;
import dev.mxt.banhang.activity.MainActivity;
import dev.mxt.banhang.model.ShoppingCart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ShoppingCart> shoppingCartArrayList;

    public CartAdapter(Context context, ArrayList<ShoppingCart> shoppingCartArrayList) {
        this.context = context;
        this.shoppingCartArrayList = shoppingCartArrayList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### Đ");
        String priceFormatted = decimalFormat.format(shoppingCartArrayList.get(position).getPrice());
        Picasso.get().load(
                shoppingCartArrayList.get(position).getImage())
                .placeholder(R.drawable.samsung_galaxy_a10s)
                .into(holder.image);
        holder.name.setText(shoppingCartArrayList.get(position).getName());
        holder.brand.setText(shoppingCartArrayList.get(position).getBrand());
        holder.price.setText(priceFormatted);
        holder.quantity.setText(String.valueOf(shoppingCartArrayList.get(position).getQuantity()));
        if (Integer.parseInt(holder.quantity.getText().toString()) == 1) {
            holder.buttonDecrease.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity.shoppingCartArrayList.remove(position);
                saveData();
                notifyDataSetChanged();
                return  true;
            }
        });
        holder.buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAmount = Integer.parseInt(holder.quantity.getText().toString()) - 1;
                MainActivity.shoppingCartArrayList.get(position).setQuantity(newAmount);
                if (newAmount >= 10){
                    holder.buttonIncrease.setVisibility(View.INVISIBLE);
                    holder.buttonDecrease.setVisibility(View.VISIBLE);
                    holder.quantity.setText(String.valueOf("10"));
                }else if (newAmount <= 0){
                    holder.buttonIncrease.setVisibility(View.VISIBLE);
                    holder.buttonDecrease.setVisibility(View.INVISIBLE);
                    holder.quantity.setText(String.valueOf("1"));
                }else if (newAmount == 1){
                    holder.quantity.setText(String.valueOf(newAmount));
                    CartActivity.calculateTotalPrice();
                    holder.buttonDecrease.setVisibility(View.INVISIBLE);
                    holder.buttonIncrease.setVisibility(View.VISIBLE);
                }
                else {
                    holder.quantity.setText(String.valueOf(newAmount));
                    CartActivity.calculateTotalPrice();
                    holder.buttonDecrease.setVisibility(View.VISIBLE);
                    holder.buttonIncrease.setVisibility(View.VISIBLE);
                }
                saveData();
                notifyDataSetChanged();
            }
        });

        holder.buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAmount = Integer.parseInt(holder.quantity.getText().toString()) + 1;
                MainActivity.shoppingCartArrayList.get(position).setQuantity(newAmount);
                if (newAmount >= 10){
                    holder.buttonIncrease.setVisibility(View.INVISIBLE);
                    holder.buttonDecrease.setVisibility(View.VISIBLE);
                    holder.quantity.setText(String.valueOf("10"));
                }else if (newAmount <= 0){
                    holder.buttonIncrease.setVisibility(View.VISIBLE);
                    holder.buttonDecrease.setVisibility(View.INVISIBLE);
                    holder.quantity.setText(String.valueOf("1"));
                }else if (newAmount == 1){
                    holder.quantity.setText(String.valueOf(newAmount));
                    CartActivity.calculateTotalPrice();
                    holder.buttonDecrease.setVisibility(View.INVISIBLE);
                    holder.buttonIncrease.setVisibility(View.VISIBLE);
                }
                else {
                    holder.quantity.setText(String.valueOf(newAmount));
                    CartActivity.calculateTotalPrice();
                    holder.buttonDecrease.setVisibility(View.VISIBLE);
                    holder.buttonIncrease.setVisibility(View.VISIBLE);
                }
                saveData();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingCartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        private TextView price;
        private TextView brand;
        private TextView quantity;
        private ImageView image;
        private ImageButton buttonDecrease;
        private ImageButton buttonIncrease;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            brand = (TextView) itemView.findViewById(R.id.brand);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            image = (ImageView) itemView.findViewById(R.id.image);
            buttonDecrease = (ImageButton) itemView.findViewById(R.id.button_decrease);
            buttonIncrease = (ImageButton) itemView.findViewById(R.id.button_increase);
        }

    }
    public void removeItem(int position) {
        shoppingCartArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ShoppingCart item, int position) {
        shoppingCartArrayList.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<ShoppingCart> getData() {
        return shoppingCartArrayList;
    }

    private void saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(shoppingCartArrayList);
        MainActivity.sharedPreferences = context.getSharedPreferences("shoppingCartArray", Context.MODE_PRIVATE);
        MainActivity.editor = MainActivity.sharedPreferences.edit();
        MainActivity.editor.putString("cartList", json);
        MainActivity.editor.apply();
    }
}
