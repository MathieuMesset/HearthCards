package com.example.hearthcards.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hearthcards.R;
import com.example.hearthcards.activities.CardActivity;
import com.example.hearthcards.model.Cards;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private List<Cards> mData;
    RequestOptions option;

    public RecyclerViewAdapter(Context mContext, List<Cards> mData) {
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cards_row_item, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mContext, CardActivity.class);
                i.putExtra("card_name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("card_artist", mData.get(viewHolder.getAdapterPosition()).getArtist());
                i.putExtra("card_flavor", mData.get(viewHolder.getAdapterPosition()).getFlavor());
                i.putExtra("card_id", mData.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("card_classe", mData.get(viewHolder.getAdapterPosition()).getClasse());

                mContext.startActivity(i);

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tv_name.setText(mData.get(i).getName());
        myViewHolder.tv_flavor.setText(mData.get(i).getFlavor());
        myViewHolder.tv_classe.setText(mData.get(i).getClasse());

        Glide.with(mContext).load(getIcon(mData.get(i).getClasse())).into(myViewHolder.icon_classe);
        Glide.with(mContext).load("https://art.hearthstonejson.com/v1/256x/" + mData.get(i).getId() + ".jpg").apply(option).into(myViewHolder.img_thumbnail);


    }

    public int getIcon(String classeName) {
        return mContext.getResources().getIdentifier(classeName.toLowerCase() + "_icon", "drawable", mContext.getPackageName());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_flavor;
        TextView tv_classe;
        ImageView icon_classe;
        ImageView img_thumbnail;
        LinearLayout view_container;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.card_name);
            tv_flavor = itemView.findViewById(R.id.flavor);
            tv_classe = itemView.findViewById(R.id.classe);
            icon_classe = itemView.findViewById(R.id.icon);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }


}
