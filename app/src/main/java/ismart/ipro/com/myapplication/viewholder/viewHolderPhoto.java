package ismart.ipro.com.myapplication.viewholder;

/**
 * Created by ELMTRIX on 4/4/2559.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ismart.ipro.com.myapplication.R;


public class viewHolderPhoto extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView ivExample,thumb;
    private LinearLayout ln_comment;
    private ImageView profile_avatar;
    private TextView titleShow,profileNname, timePost;
    private TextView sub_group;
    private OnItemClickListener mItemClickListener;
    private OnItemClickListener mItemClickListenerview;

    public viewHolderPhoto(View v) {
        super(v);
        ivExample = (ImageView) v.findViewById(R.id.ic_type);
        timePost = (TextView) v.findViewById(R.id.ago);
        thumb = (ImageView) v.findViewById(R.id.ic_type);
        profile_avatar = (ImageView) v.findViewById(R.id.profile_avatar);
        titleShow = (TextView)v.findViewById(R.id.profile_name);
        profileNname = (TextView) v.findViewById(R.id.profile_name);

        profile_avatar.setOnClickListener(this);
        thumb.setOnClickListener(this);
        titleShow.setOnClickListener(this);
    }

    public TextView getProfile_name() {
        return profileNname;
    }

    public void setProfile_name(TextView label2) {
        this.profileNname = label2;
    }

    public TextView getTimePost() {
        return timePost;
    }

    public void setTimePost(TextView timePost) {
        this.timePost = timePost;
    }

    public TextView getTitleShow() {
        return titleShow;
    }

    public void setTitleShow(TextView textShow) {
        this.titleShow = textShow;
    }

    public ImageView getImageView() {
        return ivExample;
    }

    public void setImageView(ImageView ivExample) {
        this.ivExample = ivExample;
    }

    public ImageView getIvExample() {
        return ivExample;
    }

    public void setIvExample(ImageView ivExample) {
        this.ivExample = ivExample;
    }

    public ImageView getThumb() {
        return thumb;
    }

    public void setThumb(ImageView thumb) {
        this.thumb = thumb;
    }

    public LinearLayout getLn_comment() {
        return ln_comment;
    }

    public void setLn_comment(LinearLayout ln_comment) {
        this.ln_comment = ln_comment;
    }

    public ImageView getProfile_avatar() {
        return profile_avatar;
    }

    public void setProfile_avatar(ImageView profile_avatar) {
        this.profile_avatar = profile_avatar;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListenerView {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListenerView(final OnItemClickListener mItemClickListener) {
        this.mItemClickListenerview = mItemClickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_avatar:
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition());
                }
                break;
            case R.id.ic_type:
                if (mItemClickListenerview != null) {
                    mItemClickListenerview.onItemClick(v, getPosition());
                }
                break;
            case R.id.profile_name:
                if (mItemClickListenerview != null) {
                    mItemClickListenerview.onItemClick(v, getPosition());
                }
                break;
        }
    }

    public TextView getSub_group() {
        return sub_group;
    }

    public void setSub_group(TextView sub_group) {
        this.sub_group = sub_group;
    }
}