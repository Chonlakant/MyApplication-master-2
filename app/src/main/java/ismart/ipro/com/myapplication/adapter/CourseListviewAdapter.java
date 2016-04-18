package ismart.ipro.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ismart.ipro.com.myapplication.R;
import ismart.ipro.com.myapplication.model.Course;


public class CourseListviewAdapter extends BaseAdapter implements AdapterView.OnClickListener {

    private Context context;
    ArrayList<Course> dataCourse = new ArrayList<Course>();
    public CourseListviewAdapter(Context context, ArrayList<Course> dataCourse) {
        this.context = context;
        this.dataCourse = dataCourse;
    }

    @Override
    public int getCount() {
        return dataCourse.size();
    }

    @Override
    public Object getItem(int position) {
        return dataCourse.get(position);
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

            convertView = mInflater.inflate(R.layout.item_course, parent, false);

            mViewHolder = new ViewHolder(convertView);

            Course item = dataCourse.get(position);


            mViewHolder.title.setText(item.getPost().get(position).getTitle());

           // mViewHolder.imageView.setBackgroundResource(res[position]);
            Picasso.with(context)
                    .load(item.getPost().get(position).getImage())
                    .into(mViewHolder.imageView);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    @Override
    public void onClick(View view) {

    }

    public class ViewHolder {


        TextView title;
        ImageView imageView;

        public ViewHolder(View row) {
            title = (TextView) row.findViewById(R.id.title_tv);
            imageView = (ImageView) row.findViewById(R.id.imageView);
        }
    }



}

