package com.ahmadrosid.dompetku.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.detail.DetailActionListener;
import com.ahmadrosid.dompetku.detail.DetailTransaction;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.report.ReportActivity;
import com.ahmadrosid.dompetku.transaction.EditTransaction;
import com.ahmadrosid.dompetku.transaction.EditTransactionActivity;
import com.ahmadrosid.dompetku.transaction.NewTransaction;
import com.crashlytics.android.Crashlytics;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.ballance)
    TextView ballanceTextView;
    @BindView(R.id.expend)
    TextView expendTextView;
    @BindView(R.id.list_wallet)
    RecyclerView listWallet;
    @BindView(R.id.fab_pemasukan)
    FloatingActionButton fabPemasukan;
    @BindView(R.id.fab_pengeluaran)
    FloatingActionButton fabPengeluaran;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;

    private MainContract.Presenter presenter;
    private FirebaseAdapter firebaseAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        presenter = new MainPresenter(this);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("transaction")
                .limitToFirst(50);

        FirebaseRecyclerOptions<Transaction> options = new FirebaseRecyclerOptions.Builder<Transaction>()
                        .setQuery(query, Transaction.class)
                        .build();

        firebaseAdapter = new FirebaseAdapter(options);

        listWallet.setLayoutManager(new LinearLayoutManager(this));
        listWallet.setAdapter(firebaseAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_report:
                ReportActivity.start(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showBalance(int ballance, int expend) {
        ballanceTextView.setText("+ " + CurrencyHelper.format(ballance));
        expendTextView.setText("- " + CurrencyHelper.format(expend));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    MainContract.ListViewListener listViewListener = new MainContract.ListViewListener() {
        @Override
        public void onClickListener(final Transaction transactions) {
            showDetail(transactions);
        }

        @Override
        public void onLongClickListener(final Transaction transactions) {
            CharSequence[] menuItems = new CharSequence[]{"Detail", "Edit", "Delete"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(transactions.title);
            builder.setItems(menuItems, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            showDetail(transactions);
                            break;
                        case 1:
//                            EditTransactionActivity.start(MainActivity.this, transactions.getId());
                            edit(transactions);
                            break;
                        case 2:
                            delete(transactions);
                            break;
                    }
                }
            });

            builder.show();
        }
    };

    private void showDetail(final Transaction transaction) {
        DetailTransaction detailTransaction = new DetailTransaction(MainActivity.this, transaction, new DetailActionListener() {
            @Override
            public void onEditClick() {
//                EditTransactionActivity.start(MainActivity.this, transaction.getId());
                edit(transaction);
            }

            @Override
            public void onDeleteClick() {
                delete(transaction);
            }
        });

        detailTransaction.show();
    }

    @Override
    public void showListTransaksi(List<Transaction> transactions) {
        MainAdapter adapter = new MainAdapter(this, transactions, listViewListener);

//        listWallet.setAdapter(adapter);
    }

    private void delete(final Transaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle("Delete record")
                .setMessage(transaction.title + "\n" + CurrencyHelper.format(transaction.amount))
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.deleteTransaksi(transaction);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    MainContract.PopUpListener popUpListener = new MainContract.PopUpListener() {

        @Override
        public void success() {
            presenter.loadData();
        }

        @Override
        public void failed(String message) {
            showError(message);
        }
    };

    @OnClick({R.id.fab_pemasukan, R.id.fab_pengeluaran})
    public void onViewClicked(View view) {

        Transaction.TransactionType type;

        switch (view.getId()) {
            default:
            case R.id.fab_pemasukan:
                type = Transaction.TransactionType.PEMASUKAN;
                break;
            case R.id.fab_pengeluaran:
                type = Transaction.TransactionType.PENGELUARAN;
                break;
        }

        NewTransaction modalBottomSheet = new NewTransaction(this, type, popUpListener);
        modalBottomSheet.show();

    }

    public void edit(Transaction transaction) {
        EditTransaction editTransaction = new EditTransaction(this, transaction, popUpListener);
        editTransaction.show();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

}
