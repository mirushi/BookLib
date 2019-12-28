package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import luubieunghi.lbn.booklib.Adapter.BookRecyclerViewAdapter;
import luubieunghi.lbn.booklib.BookLib;
import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.AddNewBook.AddNewBookActivity;
import luubieunghi.lbn.booklib.UI.CustomAlertDialog.BookLoadingAlertDialog;
import luubieunghi.lbn.booklib.UI.Main.BookFilterType;
import luubieunghi.lbn.booklib.UI.ReadBook.new_UI.HighlightManagementActivity;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

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

    //Biến lưu giữ layout dùng để refresh.
    SwipeRefreshLayout swipeRefreshLayout;

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
        SetupSwipeToReloadLayout(view);
        return view;
    }

    private void SetupSwipeToReloadLayout(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_book_listing_pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadDataForRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
            RecyclerView selectedRecyclerView;
            BookRecyclerViewAdapter selectedRecyclerViewAdapter;
            ArrayList<Book> selectedBookList;
            Book selectedBook;
            if (filter == BookListingReadProgressFilter.NEW){
                selectedRecyclerView = rcvFreshStartBooks;
                selectedRecyclerViewAdapter = freshBookRecyclerViewAdapter;
                selectedBookList = freshStartBooks;
            }
            else if (filter == BookListingReadProgressFilter.READING){
                selectedRecyclerView = rcvInProgressBooks;
                selectedRecyclerViewAdapter = inProgressBookRecyclerViewAdapter;
                selectedBookList = inProgressBooks;
            }
            else {
                selectedRecyclerView = rcvFinishedBooks;
                selectedRecyclerViewAdapter = finishedBookRecyclerViewAdapter;
                selectedBookList = finishedBooks;
            }
            selectedBook = selectedBookList.get(itemPosition);

            switch (item.getItemId()){
                case BookRecyclerViewAdapter.loadBookDetailsID:{
                    Intent intent = new Intent(BookListingFragment.this.getActivity(), AddNewBookActivity.class);
                    intent.putExtra("EXISTING_BOOK", selectedBook);
                    startActivity(intent);

                    displayMessage(filter.toString() + "Load Book Details Clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.startReadingBookID:{
                    selectedRecyclerView.findViewHolderForAdapterPosition(itemPosition).itemView.performClick();
                    displayMessage(filter.toString() + "Start reading book clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.viewBookmarksID:{
                    displayMessage(filter.toString() + "View Bookmark clicked !" + itemPosition.toString());
                    return true;
                }
                case BookRecyclerViewAdapter.viewHighlightsID:{
                    displayMessage(filter.toString() + "View highlight clicked !" + itemPosition.toString());
                    if (selectedBook.getBTypeID() == BookDatabase.getInstance(BookLib.getAppContext()).getEbookId()){
                        Intent intent = new Intent(BookListingFragment.this.getActivity(), HighlightManagementActivity.class);
                        intent.putExtra("book", selectedBook);
                        BookListingFragment.this.startActivity(intent);
                    }
                    else
                        displayMessage("Xin lỗi. Tính năng này chỉ hỗ trợ đối với ebook");
                    return true;
                }
                case BookRecyclerViewAdapter.markReadID:{
                    if (filter == BookListingReadProgressFilter.FINISHED){
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                BookDatabase.getInstance(BookListingFragment.this.getActivity()).BookDAO().markBookUnread(selectedBook.getBookID());
                                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadDataForRecyclerView();
                                    }
                                });
                            }
                        });
                        break;
                    }
                    displayMessage(filter.toString() + "Mark read clicked !" + itemPosition.toString());
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            BookDatabase.getInstance(BookListingFragment.this.getActivity()).BookDAO().markBookRead(selectedBook.getBookID());
                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    LoadDataForRecyclerView();
                                }
                            });
                        }
                    });
                    return true;
                }
                case BookRecyclerViewAdapter.deleteBookID:{

                    AlertDialog.Builder builder = new AlertDialog.Builder(BookListingFragment.this.getActivity());
                    builder.setTitle("Xoá sách !!!")
                            .setMessage("Bạn có chắc muốn xoá \"" + selectedBook.getBookTitle() + "\"" + " ? ")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SetLoadingDialog(true);
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            BookDatabase.getInstance(BookListingFragment.this.getActivity()).BookDAO().deleteBook(selectedBook);
                                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //Refresh lại danh sách sách sau khi xoá.
                                                    LoadDataForRecyclerView();
                                                    SetLoadingDialog(false);
                                                }
                                            });
                                        }
                                    });
                                }
                            })
                            .setIcon(android.R.drawable.ic_delete)
                            .show();

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
        //Xét xem filter đang ở chế độ nào.
        //Load dữ liệu cho danh sách sách mới.
        presenter.LoadBookList(BookListingReadProgressFilter.NEW, filter);

        //Load dữ liệu cho danh sách sách đang đọc.
        presenter.LoadBookList(BookListingReadProgressFilter.READING, filter);

        //Load dữ liệu cho danh sách sách đã đọc xong.
        presenter.LoadBookList(BookListingReadProgressFilter.FINISHED, filter);
    }

    @Override
    public void AddOneBookToView(Book book, BookListingReadProgressFilter filter) {
        if (filter == BookListingReadProgressFilter.NEW){
            freshStartBooks.add(book);
            freshBookRecyclerViewAdapter.notifyDataSetChangedAndSetFullList();
        }
        else if (filter == BookListingReadProgressFilter.READING){
            inProgressBooks.add(book);
            inProgressBookRecyclerViewAdapter.notifyDataSetChangedAndSetFullList();
        }
        else if (filter == BookListingReadProgressFilter.FINISHED){
            finishedBooks.add(book);
            finishedBookRecyclerViewAdapter.notifyDataSetChangedAndSetFullList();
        }
    }

    @Override
    public void AddMultipleBookToView(List<Book> books, BookListingReadProgressFilter filter) {
        if (filter == BookListingReadProgressFilter.NEW){
            freshStartBooks.addAll(books);
            freshBookRecyclerViewAdapter.notifyDataSetChangedAndSetFullList();
        }
        else if (filter == BookListingReadProgressFilter.READING){
            inProgressBooks.addAll(books);
            inProgressBookRecyclerViewAdapter.notifyDataSetChangedAndSetFullList();
        }
        else if (filter == BookListingReadProgressFilter.FINISHED){
            finishedBooks.addAll(books);
            finishedBookRecyclerViewAdapter.notifyDataSetChangedAndSetFullList();
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

    public BookRecyclerViewAdapter getFreshBookRecyclerViewAdapter() {
        return freshBookRecyclerViewAdapter;
    }

    public BookRecyclerViewAdapter getInProgressBookRecyclerViewAdapter() {
        return inProgressBookRecyclerViewAdapter;
    }

    public BookRecyclerViewAdapter getFinishedBookRecyclerViewAdapter() {
        return finishedBookRecyclerViewAdapter;
    }
}

