package ismart.ipro.com.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ismart.ipro.com.myapplication.R;
import ismart.ipro.com.myapplication.model.Post;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ContactViewHolder> {

    private ArrayList<Post> contactList = new ArrayList<>();
    Context context;
    private static OnItemClickListener mItemClickListener;
    private static OnItemClickListener mItemClickListenerShare;
    private static OnItemClickListener mItemClickListenerRead;
    public MyRecyclerAdapter(Context context, ArrayList<Post> contactList) {
        this.context =context;
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
       // listMain ci = contactList.get(i);
        Post item =  contactList.get(i);
        contactViewHolder.title_tv.setText(item.getPost().get(i).getTitle());
        contactViewHolder.textView11.setText(item.getPost().get(i).getCount());
        Picasso.with(context)
                .load(item.getPost().get(i).getFile_img())
                .fit().centerCrop()
                .into(contactViewHolder.image_detail);

        Picasso.with(context)
                .load(R.drawable.ipro)
                .fit()
                .into(contactViewHolder.profile_avatar);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_feed_photo, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title_tv,textView11;
        ImageView image_detail,profile_avatar,imgView;
        Button btn_shares,btn_read;
        TextView profile;

        public ContactViewHolder(View v) {
            super(v);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            image_detail = (ImageView) v.findViewById(R.id.image_detail);
            profile_avatar = (ImageView) v.findViewById(R.id.profile_avatar);
            textView11 = (TextView) v.findViewById(R.id.textView11);
            btn_shares = (Button) v.findViewById(R.id.btn_shares);
            btn_read = (Button) v.findViewById(R.id.btn_read);
            imgView = (ImageView)v.findViewById(R.id.ic_type);
            image_detail.setOnClickListener(this);
            btn_shares.setOnClickListener(this);
            btn_read.setOnClickListener(this);
            imgView.setOnClickListener(this);

        }
Context con;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_detail:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                break;
                case R.id.btn_shares:
                    if (mItemClickListenerShare != null) {
                        mItemClickListenerShare.onItemClick(v, getPosition());
                    }
                    break;
                case R.id.btn_read:
                    if (mItemClickListenerRead != null) {
                        mItemClickListenerRead.onItemClick(v, getPosition());
                    }
                    break;
//                case R.id.ic_type:
////                    if (mItemClickListenerShare != null) {
//                        Toast.makeText(con, "IMAGE", Toast.LENGTH_SHORT).show();
////                    }
//                    break;
//                case R.id.profile_name:
//                    if (mItemClickListenerShare != null) {
////                        mItemClickListenerShare.onItemClick(v, getPosition());
//                    }
//                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListenerShare {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListenerShare(final OnItemClickListener mItemClickListener) {
        this.mItemClickListenerShare = mItemClickListener;
    }
    public interface OnItemClickListenerRead {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListenerRead(final OnItemClickListener mItemClickListener) {
        this.mItemClickListenerRead = mItemClickListener;
    }


}