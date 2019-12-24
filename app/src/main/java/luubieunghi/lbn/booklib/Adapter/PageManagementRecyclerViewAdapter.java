package luubieunghi.lbn.booklib.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.BookPageManagement.PageManagementRecyclerViewItem;

public class PageManagementRecyclerViewAdapter extends RecyclerView.Adapter<PageManagementRecyclerViewAdapter.ViewHolder> {
    List<PageManagementRecyclerViewItem> pageList;
    Context context;

    public PageManagementRecyclerViewAdapter(List<PageManagementRecyclerViewItem> pageList, Context context) {
        this.pageList = pageList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View viewItem = layoutInflater.inflate(R.layout.page_management_recycler_view_item, parent, false);
        return new ViewHolder((viewItem));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(pageList.get(position).getText());
        holder.imageView.setImageResource(pageList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View viewItem) {
            super(viewItem);
            textView = (TextView) viewItem.findViewById(R.id.recycler_view_item_textview);
            imageView = (ImageView) viewItem.findViewById(R.id.recycler_view_item_image);
        }
    }
}
