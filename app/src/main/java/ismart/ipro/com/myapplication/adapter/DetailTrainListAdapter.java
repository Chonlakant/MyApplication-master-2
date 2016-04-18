package ismart.ipro.com.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ismart.ipro.com.myapplication.model.Post;
import ismart.ipro.com.myapplication.model.TrainDetail;

/**
 * Created by ELMTRIX on 5/4/2559.
 */
public class DetailTrainListAdapter extends BaseAdapter {

    private List<Post> trainDetails;
    Context context;
    ArrayList<Post.PostEntity> list = new ArrayList<>();
    public static MyRecyclerAdapter.OnItemClickListener mItemClickListener;
    public DetailTrainListAdapter(Context context, ArrayList<Post.PostEntity> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
