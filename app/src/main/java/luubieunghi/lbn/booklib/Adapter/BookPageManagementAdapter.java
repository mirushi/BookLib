package luubieunghi.lbn.booklib.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.OpenAlbum.OpenAlbum;

public class BookPageManagementAdapter extends RecyclerView.Adapter<BookPageManagementAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> contents;
    public BookPageManagementAdapter() {
    }

    public BookPageManagementAdapter(Context context, ArrayList<String> contents) {
        this.context = context;
        this.contents = contents;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_page_book_page_management,parent, false);
        final MyViewHolder holder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OpenAlbum.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_content.setText(contents.get(position));
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_content=itemView.findViewById(R.id.txt_content_book_page_management);
        }
    }
}
