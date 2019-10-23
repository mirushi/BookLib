package luubieunghi.lbn.booklib.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import luubieunghi.lbn.booklib.BookFilterType;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BookRecyclerViewAdapter;
import luubieunghi.lbn.booklib.model.Book;

public class BookListingFragment extends Fragment {

    LinearLayoutManager freshStartBooksLayoutManager;
    LinearLayoutManager inProgressBooksLayoutManager;
    LinearLayoutManager finishedBooksLayoutManager;

    BookFilterType filter;
    RecyclerView freshStartBooks;
    RecyclerView inProgressBooks;
    RecyclerView finishedBooks;

    BookRecyclerViewAdapter bookRecyclerViewAdapter;
    View view;

    public static BookListingFragment newInstance(BookFilterType filter)
    {
        BookListingFragment fragment = new BookListingFragment();
        Bundle args = new Bundle();
        //Gửi thông điệp qua cho constructor để biết kiểu filter là gì.
        args.putInt("filterValue",filter.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Đọc thông điệp được nhận từ Bundle để biết kiểu filter là gì.
        int filterValue = getArguments().getInt("filterValue", 0);
        filter = BookFilterType.getFilterType(filterValue);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_book_listing, container, false);
        SetupRecyclerView();
        return view;
    }

    private void SetupRecyclerView()
    {
        freshStartBooksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        inProgressBooksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        finishedBooksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        freshStartBooks = (RecyclerView)view.findViewById(R.id.fragment_book_listing_rcv_new_book);
        inProgressBooks = (RecyclerView)view.findViewById(R.id.fragment_book_listing_rcv_reading_book);
        finishedBooks = (RecyclerView)view.findViewById(R.id.fragment_book_listing_rcv_finished_book);

        ArrayList<Book> bookList = new ArrayList<>();

        bookList.add(new Book(getContext(),null, "Book number 1"));
        bookList.add(new Book(getContext(),null, "Book number 2"));
        bookList.add(new Book(getContext(),null, "Book number 3"));
        bookList.add(new Book(getContext(),null, "Book number 4"));
        bookList.add(new Book(getContext(),null, "Book number 5"));

        bookRecyclerViewAdapter = new BookRecyclerViewAdapter(bookList);

        //Set layout manager để hiển thị theo hàng ngang.
        freshStartBooks.setLayoutManager(freshStartBooksLayoutManager);
        inProgressBooks.setLayoutManager(inProgressBooksLayoutManager);
        finishedBooks.setLayoutManager(finishedBooksLayoutManager);

        //Set adapter để hiển thị dữ liệu lên RecyclerView.
        freshStartBooks.setAdapter(bookRecyclerViewAdapter);
        inProgressBooks.setAdapter(bookRecyclerViewAdapter);
        finishedBooks.setAdapter(bookRecyclerViewAdapter);
    }
}

