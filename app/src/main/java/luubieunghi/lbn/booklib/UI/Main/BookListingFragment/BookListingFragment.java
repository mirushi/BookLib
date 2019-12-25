package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import luubieunghi.lbn.booklib.Adapter.BookRecyclerViewAdapter;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.CustomAlertDialog.BookLoadingAlertDialog;
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

    //Dialog dùng để chặn người dùng thao tác với màn hình khi sách đang load.
    BookLoadingAlertDialog dialog;

    private BroadcastReceiver mMessageReceiver;

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

        dialog = new BookLoadingAlertDialog(this.getActivity());

        //Khởi tạo mới presenter ở đây.
        presenter = new BookListingPresenter(this);

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LoadDataForRecyclerView();
            }
        };
        //Ta đăng ký receiver để biết khi nào cần phải cập nhật lại danh sách sách.
        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver
                (mMessageReceiver, new IntentFilter("book_list_updated"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_book_listing, container, false);
        SetupRecyclerView();
        return view;
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this.getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void displayMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public class BookRecyclerViewOnItemClickListener implements MenuItem.OnMenuItemClickListener{

        BookListingReadProgressFilter filter;

        public BookRecyclerViewOnItemClickListener(BookListingReadProgressFilter filter){
            this.filter = filter;
        }

        public BookRecyclerViewOnItemClickListener(){
            this.filter = null;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Integer itemPosition = item.getOrder();
            switch (item.getItemId()){
                case BookRecyclerViewAdapter.loadBookDetailsID:{
                    displayMessage(filter.toString() + "Load Book Details Clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.startReadingBookID:{
                    displayMessage(filter.toString() + "Start reading book clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.viewBookmarksID:{
                    displayMessage(filter.toString() + "View Bookmark clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.viewHighlightsID:{
                    displayMessage(filter.toString() + "View highlight clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.markReadID:{
                    displayMessage(filter.toString() + "Mark read clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.deleteBookID:{
                    displayMessage(filter.toString() + "Delete book clicked !" + itemPosition.toString());
                    return true;
                }
            }
            return false;
        }
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

        //Cài context menu listener cho sách mới.
        rcvFreshStartBooks.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                freshBookRecyclerViewAdapter.setMenuItemOnClickListener
                        (new BookRecyclerViewOnItemClickListener(BookListingReadProgressFilter.NEW));
            }
        });

        //Cài context menu listener cho sách đang đọc.
        rcvInProgressBooks.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                inProgressBookRecyclerViewAdapter.setMenuItemOnClickListener
                        (new BookRecyclerViewOnItemClickListener(BookListingReadProgressFilter.READING));
            }
        });

        //Cài context menu listener cho sách đã hoàn thành.
        rcvFinishedBooks.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                finishedBookRecyclerViewAdapter.setMenuItemOnClickListener
                        (new BookRecyclerViewOnItemClickListener(BookListingReadProgressFilter.FINISHED));
            }
        });
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
    public void AddMultipleBookToView(List<Book> books, BookListingReadProgressFilter filter) {
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

    @Override
    public void SetLoadingDialog(boolean show) {
        if (show){
            dialog.showDialog();
        }
        else{
            dialog.hideDialog();
        }
    }
}

