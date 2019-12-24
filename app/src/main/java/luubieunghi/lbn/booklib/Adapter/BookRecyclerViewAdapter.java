package luubieunghi.lbn.booklib.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.ReadBook.ReadBookActivity;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookRecyclerViewHolder> {

    private List<Book> bookList;
    private Context context;

    //Lưu lại danh sách MenuItem để setOnClickListener cho nó.
    ArrayList<MenuItem> menuItems = new ArrayList<>();

    //Những hằng số để phân biệt các item trong context menu.
    public static final int startReadingBookID = 1123;
    public static final int loadBookDetailsID = 1124;
    public static final int viewBookmarksID = 1125;
    public static final int viewHighlightsID = 1126;
    public static final int markReadID = 1127;
    public static final int deleteBookID = 1128;

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

    public void setMenuItemOnClickListener(MenuItem.OnMenuItemClickListener listener){
        for (int i=0;i<menuItems.size();++i){
            MenuItem item = menuItems.get(i);
            item.setOnMenuItemClickListener(listener);
        }
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
            menuItems.add(menu.add(this.getAdapterPosition(), startReadingBookID, getAdapterPosition(), "Bắt đầu đọc sách"));
            menuItems.add(menu.add(this.getAdapterPosition(), loadBookDetailsID, getAdapterPosition(), "Xem thông tin sách"));
            menuItems.add(menu.add(this.getAdapterPosition(), viewBookmarksID, getAdapterPosition(), "Hiển thị dấu trang"));
            menuItems.add(menu.add(this.getAdapterPosition(), viewHighlightsID, getAdapterPosition(), "Hiển thị highlight"));
            menuItems.add(menu.add(this.getAdapterPosition(), markReadID, getAdapterPosition(), "Đánh dấu là đã đọc"));
            menuItems.add(menu.add(this.getAdapterPosition(), deleteBookID, getAdapterPosition(), "Xoá sách"));
        }
    }


}
