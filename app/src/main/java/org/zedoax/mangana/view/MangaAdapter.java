package org.zedoax.mangana.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.zedoax.mangana.R;
import org.zedoax.mangana.objects.MangaItem;

import it.sephiroth.android.library.picasso.Picasso;

import static android.content.ContentValues.TAG;

/**************************************************************
 * Zedoax - Adapter for Use on Manga List Pages               *
 * @author Elijah Bendinsky                                   *
 **************************************************************/

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolder> {
    private MangaItem[] mDataset;

    private OnClickListener onClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Create variable holders for view components
        private ImageView mCover;
        private TextView mTitle;
        private TextView mDirectories;
        private TextView mAuthor;
        private TextView mRank;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize the missing components, by finding their id
            mCover = (ImageView) itemView.findViewById(R.id.manga_cover);
            mTitle = (TextView) itemView.findViewById(R.id.manga_title);
            mDirectories = (TextView) itemView.findViewById(R.id.manga_directories);
            mAuthor = (TextView) itemView.findViewById(R.id.manga_author);
            mRank = (TextView) itemView.findViewById(R.id.manga_rank);

        }

    }

    public MangaAdapter(MangaItem[] mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Grab the context, then load the image into a view
        final Context context = holder.mCover.getContext();
        if(!mDataset[position].getCoverUrl().isEmpty()) {
            Picasso.with(context).clearCache();
            Picasso.with(context).load(mDataset[position].getCoverUrl()).error(R.drawable.error_drawable).into(holder.mCover);
        } else {
            Log.e(TAG, "onBindViewHolder: Failed to Load Image Resource");
        }
        // Loads additional data into text fields
        holder.mTitle.setText(mDataset[position].getTitle());
        holder.mDirectories.setText(mDataset[position].getDirectories());
        holder.mAuthor.setText(mDataset[position].getAuthor());
        holder.mRank.setText(mDataset[position].getRank());
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(mDataset[holder.getAdapterPosition()]);
                    }
                }

        );

    }

    /**
     * Method to use when updating the dataset used by RecyclerView
     * @param dataset the data to replace the old
     */
    public void update(MangaItem[] dataset) {
        mDataset = dataset;

    }

    public MangaItem get(int position) {
        return mDataset[position];

    }

    public int getItemCount() {
        return mDataset.length;

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

    public interface OnClickListener {
        void onClick(MangaItem item);

    }

}
