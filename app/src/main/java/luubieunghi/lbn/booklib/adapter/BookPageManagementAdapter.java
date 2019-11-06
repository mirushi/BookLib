package luubieunghi.lbn.booklib.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Activity.OpenAlbum;
import luubieunghi.lbn.booklib.Activity.ReadBookActivity;
import luubieunghi.lbn.booklib.R;

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
