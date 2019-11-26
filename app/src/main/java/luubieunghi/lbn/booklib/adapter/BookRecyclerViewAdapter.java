package luubieunghi.lbn.booklib.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import luubieunghi.lbn.booklib.Activity.MainActivity;
import luubieunghi.lbn.booklib.Activity.ReadBookActivity;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.model.Book;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookRecyclerViewHolder> {

    private List<Book> bookList;
    private Context context;

    //Những hằng số để phân biệt các item trong context menu.
    private final int startReadingBookID = 1123;
    private final int loadBookDetailsID = 1124;
    private final int viewBookmarksID = 1125;
    private final int viewHighlightsID = 1126;
    private final int markReadID = 1127;
    private final int deleteBookID = 1128;

    public BookRecyclerViewAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_book, parent, false);

        final BookRecyclerViewHolder holder = new BookRecyclerViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = holder.getAdapterPosition();
                Toast.makeText(context, "Clicked book number " + position.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ReadBookActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
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

    public class BookRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView bookImage;
        TextView bookTitle;

        public BookRecyclerViewHolder(View view) {
            super(view);
            bookImage = (ImageView) view.findViewById(R.id.item_book_iv_book_image);
            bookTitle = (TextView) view.findViewById(R.id.item_book_txtView_book_name);
            //Set để hiển thị context menu cho recycler view.
            view.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.add(this.getAdapterPosition(), startReadingBookID, 0, "Bắt đầu đọc sách");
            menu.add(this.getAdapterPosition(), loadBookDetailsID, 1, "Xem thông tin sách");
            menu.add(this.getAdapterPosition(), viewBookmarksID, 2, "Hiển thị dấu trang");
            menu.add(this.getAdapterPosition(), viewHighlightsID, 3, "Hiển thị highlight");
            menu.add(this.getAdapterPosition(), markReadID, 4, "Đánh dấu là đã đọc");
            menu.add(this.getAdapterPosition(), deleteBookID, 5, "Xoá sách");
        }
    }


}
