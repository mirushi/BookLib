package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import android.app.Presentation;
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

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.BookRecyclerViewAdapter;
import luubieunghi.lbn.booklib.Model.Book;
import luubieunghi.lbn.booklib.UI.Main.BookFilterType;

public class BookListingFragment extends Fragment implements BookListingContract.BookListingMVPView {

    //Giữ presenter để hiện thực cấu trúc MVP.
    BookListingContract.BookListingMVPPresenter presenter;

    LinearLayoutManager freshStartBooksLayoutManager;
    LinearLayoutManager inProgressBooksLayoutManager;
    LinearLayoutManager finishedBooksLayoutManager;

    BookFilterType filter;
    RecyclerView rcvFreshStartBooks;
    RecyclerView rcvInProgressBooks;
    RecyclerView rcvFinishedBooks;

    //Danh sách các adapter của RecyclerView.
    BookRecyclerViewAdapter freshBookRecyclerViewAdapter;
    BookRecyclerViewAdapter inProgressBookRecyclerViewAdapter;
    BookRecyclerViewAdapter finishedBookRecyclerViewAdapter;

    View view;

    //ArrayList dùng để lưu trữ sách (phân theo tiến độ đọc).
    ArrayList<Book> freshStartBooks = new ArrayList<>();
    ArrayList<Book> inProgressBooks = new ArrayList<>();
    ArrayList<Book> finishedBooks = new ArrayList<>();

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

        //Khởi tạo mới presenter ở đây.
        presenter = new BookListingPresenter(this);
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
        //Tạo recyclerView cho từng danh sách.
        rcvFreshStartBooks = (RecyclerView)view.findViewById(R.id.fragment_book_listing_rcv_new_book);
        rcvInProgressBooks = (RecyclerView)view.findViewById(R.id.fragment_book_listing_rcv_reading_book);
        rcvFinishedBooks = (RecyclerView)view.findViewById(R.id.fragment_book_listing_rcv_finished_book);

        //Tạo adapter cho từng danh sách.
        freshBookRecyclerViewAdapter = new BookRecyclerViewAdapter(freshStartBooks);
        inProgressBookRecyclerViewAdapter = new BookRecyclerViewAdapter(inProgressBooks);
        finishedBookRecyclerViewAdapter = new BookRecyclerViewAdapter(finishedBooks);

        //Tạo

        //Set layout manager để hiển thị theo hàng ngang.
        freshStartBooksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        inProgressBooksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        finishedBooksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvFreshStartBooks.setLayoutManager(freshStartBooksLayoutManager);
        rcvInProgressBooks.setLayoutManager(inProgressBooksLayoutManager);
        rcvFinishedBooks.setLayoutManager(finishedBooksLayoutManager);

        //Load data lên adapter của RecyclerView.
        LoadDataForRecyclerView();

        //Set adapter để hiển thị dữ liệu lên RecyclerView.
        rcvFreshStartBooks.setAdapter(freshBookRecyclerViewAdapter);
        rcvInProgressBooks.setAdapter(inProgressBookRecyclerViewAdapter);
        rcvFinishedBooks.setAdapter(finishedBookRecyclerViewAdapter);
    }

    private void LoadDataForRecyclerView()
    {
        //Load dữ liệu cho danh sách sách mới.
        presenter.LoadBookList(BookListingReadProgressFilter.NEW);

        //Load dữ liệu cho danh sách sách đang đọc.
        presenter.LoadBookList(BookListingReadProgressFilter.READING);

        //Load dữ liệu cho danh sách sách đã đọc xong.
        presenter.LoadBookList(BookListingReadProgressFilter.FINISHED);
    }

    @Override
    public void AddOneBookToView(Book book, BookListingReadProgressFilter filter) {
        if (filter == BookListingReadProgressFilter.NEW){
            freshStartBooks.add(book);
            freshBookRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if (filter == BookListingReadProgressFilter.READING){
            inProgressBooks.add(book);
            inProgressBookRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if (filter == BookListingReadProgressFilter.FINISHED){
            finishedBooks.add(book);
            finishedBookRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void AddMultipleBookToView(ArrayList<Book> books, BookListingReadProgressFilter filter) {
        if (filter == BookListingReadProgressFilter.NEW){
            freshStartBooks.addAll(books);
            freshBookRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if (filter == BookListingReadProgressFilter.READING){
            inProgressBooks.addAll(books);
            inProgressBookRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if (filter == BookListingReadProgressFilter.FINISHED){
            finishedBooks.addAll(books);
            finishedBookRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}

