package korotkov.ru.rssreadere;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 26.03.2018.
 */

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<News> mDataset;
    private Context mContext;

    public MainAdapter(ArrayList<News> dataset, Context context){

        mDataset = dataset;
        mContext = context;

    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder,final int position) {

        holder.mTitle.setText(mDataset.get(position).getTitle());

        holder.mPubDate.setText(mDataset.get(position).getPubDate());

        //loadingImage

        /*Picasso.with(mContext)
                .load(mDataset.get(position).getImage())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(holder.mImageView);*/
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public TextView mPubDate;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.titleN);
            mPubDate = itemView.findViewById(R.id.pubDate);
            mImageView = itemView.findViewById(R.id.imageN);
        }
    }
}
