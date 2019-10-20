package luubieunghi.lbn.booklib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.model.Book;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookRecyclerViewHolder> {

    private List<Book> bookList;

    public BookRecyclerViewAdapter(List<Book> bookList)
    {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new BookRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRecyclerViewHolder holder, int position) {
        holder.bookImage.setImageBitmap(bookList.get(position).getBookImage());
        holder.bookTitle.setText(bookList.get(position).getBookTitle());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookRecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView bookImage;
        TextView bookTitle;

        public BookRecyclerViewHolder(View view)
        {
            super(view);
            bookImage = (ImageView)view.findViewById(R.id.item_book_iv_book_image);
            bookTitle = (TextView)view.findViewById(R.id.item_book_txtView_book_name);
        }
    }
}
