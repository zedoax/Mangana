package org.zedoax.mangana.model;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.zedoax.mangana.R;
import org.zedoax.mangana.objects.MangaItem;

import it.sephiroth.android.library.picasso.Picasso;

import static android.content.ContentValues.TAG;

/**
 * Created by Zedoax on 2/2/2017.
 */

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private MangaItem mItem;
    private String[] mDataset;

    private OnClickListener onClickListener;

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        // Create variable holders for view components
        private CardView cLeft;
        private CardView cRight;
        private TextView mLeft;
        private TextView mRight;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            // Initialize the missing components, by finding their id
            mLeft = (TextView) itemView.findViewById(R.id.info_chapter_left_text);
            mRight = (TextView) itemView.findViewById(R.id.info_chapter_right_text);
            cLeft = (CardView) itemView.findViewById(R.id.info_chapter_left);
            cRight = (CardView) itemView.findViewById(R.id.info_chapter_right);

        }

    }

    public static class MangaViewHolder extends RecyclerView.ViewHolder {
        // Create variable holders for view components
        private ImageView mCover;
        private TextView mTitle;
        private TextView mDirectories;
        private TextView mAuthor;
        private TextView mRank;
        private TextView mDescription;

        public MangaViewHolder(View itemView) {
            super(itemView);
            // Initialize the missing components, by finding their id
            mCover = (ImageView) itemView.findViewById(R.id.info_cover);
            mTitle = (TextView) itemView.findViewById(R.id.info_title);
            mDirectories = (TextView) itemView.findViewById(R.id.info_directories);
            mAuthor = (TextView) itemView.findViewById(R.id.info_author);
            mRank = (TextView) itemView.findViewById(R.id.info_rank);
            mDescription = (TextView) itemView.findViewById(R.id.info_description);

        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;

            default:
                return 1;

        }
    }

    public InfoAdapter(MangaItem mangaItem, String[] mDataset) {
        this.mItem = mangaItem;
        this.mDataset = mDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item, parent, false);
                return new MangaViewHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_chapter, parent, false);
                Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.slide_in_top);
                v.startAnimation(animation);
                return new ChapterViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case 0:
                Context context = ((MangaViewHolder) holder).mCover.getContext();
                if (!mItem.getCoverUrl().isEmpty()) {
                    Picasso.with(context).clearCache();
                    Picasso.with(context).load(mItem.getCoverUrl()).into(((MangaViewHolder) holder).mCover);
                } else {
                    Log.e(TAG, "onBindViewHolder: Failed to Load Image Resource");
                }
                // Loads additional data into text fields
                ((MangaViewHolder) holder).mTitle.setText(mItem.getTitle());
                ((MangaViewHolder) holder).mDirectories.setText(mItem.getDirectories());
                ((MangaViewHolder) holder).mAuthor.setText(mItem.getAuthor());
                ((MangaViewHolder) holder).mRank.setText(mItem.getRank());
                ((MangaViewHolder) holder).mDescription.setText(mItem.getDescription());
                break;
            default:

                ((ChapterViewHolder) holder).mLeft.setText(mDataset[(position * 2) - 2]);
                ((ChapterViewHolder) holder).mLeft.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickListener.onClick(mItem.getIndex(), mItem.getChapters()[(position * 2) - 2]);
                            }
                        }
                );
                if(position != getItemCount() - 1 || mDataset.length % 2 == 0) {
                    ((ChapterViewHolder) holder).mRight.setText(mDataset[(position * 2) - 1]);
                    ((ChapterViewHolder) holder).mRight.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onClickListener.onClick(mItem.getIndex(), mItem.getChapters()[(position * 2) - 1]);
                                }
                            }
                    );
                } else {
                    ((ChapterViewHolder) holder).mRight.setText("");
                    ((ChapterViewHolder) holder).cRight.setVisibility(View.INVISIBLE);

                }
                /*
                if (position * 2 == mDataset.length + 1) {
                    ((ChapterViewHolder) holder).cRight.setVisibility(View.INVISIBLE);
                }
                */
        }

    }

    /**
     * Method to use when updating the dataset used by RecyclerView
     * @param dataset the data to replace the old
     */
    public void update(MangaItem mangaItem, String[] dataset) {
        this.mItem = mangaItem;
        mDataset = dataset;

    }

    public String get(int position) {
        return mDataset[position];

    }

    public int getItemCount() {
        return (mDataset.length % 2 == 0) ? mDataset.length / 2 + 1: mDataset.length / 2 + 2;

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

    public interface OnClickListener {
        void onClick(String index, String chapter);

    }

}
