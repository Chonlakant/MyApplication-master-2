package ismart.ipro.com.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ismart.ipro.com.myapplication.R;
import ismart.ipro.com.myapplication.model.Post;

/**
 * Created by ELMTRIX on 5/4/2559.
 */
public class TrainingDetailAdapter extends BaseAdapter implements AdapterView.OnClickListener {

    private Context context;
    ArrayList<Post.PostEntity> dataTrain = new ArrayList<>();
    public TrainingDetailAdapter(Context context, ArrayList<Post.PostEntity> dataTrain) {
        this.context = context;
        this.dataTrain = dataTrain;
    }

    @Override
    public int getCount() {
        return dataTrain.size();
    }

    @Override
    public Object getItem(int position) {
        return dataTrain.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = null;

        if (convertView == null) {

            LayoutInflater mInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_detailstrain, parent, false);

            mViewHolder = new ViewHolder(convertView);

            Post.PostEntity item = dataTrain.get(position);

            Log.e("title", item.getTitle() + "");

            mViewHolder.title.setText(item.getTitle());



        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
    public class ViewHolder {


        TextView title;

        public ViewHolder(View row) {
            title = (TextView) row.findViewById(R.id.title_detailtraining);
        }
    }
}
