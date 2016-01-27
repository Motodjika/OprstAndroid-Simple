package com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery;

import android.os.AsyncTask;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.DataService.SiteConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavlo on 10/23/2015.
 */
public class VideoCommentAdapter<I> extends ArrayAdapter<I> {
    private static final String TAG = "VideoCommentAdapter";

    public VideoCommentAdapter(BaseContainerFragment fragment,ArrayList<I> items) {
        super(fragment.getContext(),R.layout.activity_comment_video,items);
        picasso=new Picasso.Builder(fragment.getContext()).build();
        mFragment=fragment;
        mItems=items;
    }
    protected List<I> mItems;
    protected BaseContainerFragment mFragment=null;

    protected Picasso picasso=null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertView(parent);
        }
        convertView = bindView(convertView,position);
        return convertView;
    }

    private ViewHolder vh=null;

    public View bindView(View convertView, final int position) {
        vh = (ViewHolder) convertView.getTag(R.layout.fragment_video_comments);
        if(vh!=null) {
            final VideoCommentItem item =
                    (VideoCommentItem) getItem(position);
            vh.commentTextView.setText(item.getComment());
            vh.down_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FetchItemsTask().execute(item.getUrlDown(), String.valueOf(position));
                }
            });
            vh.ratingTextView.setText(item.getRating());
            vh.up_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FetchItemsTask().execute(item.getUrlUp(), String.valueOf(position));
                }
            });
        }
        return convertView;
    }
    //получение комментариев в фоновом потоке
    private class FetchItemsTask extends AsyncTask<String,Void,List<I>> {
        @Override
        protected List<I> doInBackground(String... params) {
            SiteConnector connector=new SiteConnector();
            String numberVotes=connector.vote(params[0]);
            int pos =0;
            try {
                pos = Integer.valueOf(params[1]);
            }catch(Exception ex){
                return null;
            }
            if(numberVotes!=null) {
                VideoCommentItem item = (VideoCommentItem) getItem(pos);
                if(!item.getRating().equals(numberVotes)) {
                    int index= mItems.indexOf(item);
                    mItems.remove(index);
                    item.setRating(numberVotes);
                    mItems.add(index, (I) item);
                    return mItems;
                }else{
                    Message mes=new Message();
                    mes.arg1=R.string.error_rating_message;
                    mFragment.getCallbacks().getErrorHandler().handleMessage(mes);
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<I> items){
            if(items!=null) {
                notifyDataSetChanged();
            }
        }
    }

    protected View getConvertView(ViewGroup parent) {
        View convertView=mFragment.getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_video_comments, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.commentTextView=(TextView)convertView
                .findViewById(R.id.comment_text);
        vh.down_btn=(Button)convertView
                .findViewById(R.id.comment_down_btn);
        vh.ratingTextView=(TextView)convertView
                .findViewById(R.id.comment_rating_text);
        vh.up_btn=(Button)convertView
                .findViewById(R.id.comment_up_btn);
        convertView.setTag(R.layout.fragment_video_comments,vh);
        return convertView;

    }
    class ViewHolder {
        TextView commentTextView;
        Button down_btn;
        TextView ratingTextView;
        Button up_btn;
    }
}
