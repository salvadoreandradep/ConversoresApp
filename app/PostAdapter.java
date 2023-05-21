import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> postsList;



    public PostAdapter(Context context, List<String> postsList) {
        super(context, 0, postsList);
        this.context = context;
        this.postsList = postsList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);


        ViewHolder viewHolder;

        if (convertView == null) {



        }



    }

    private static class ViewHolder {
        TextView postTextView;
    }
}
