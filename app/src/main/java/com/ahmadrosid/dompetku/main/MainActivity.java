package com.ahmadrosid.dompetku.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.detail.DetailActionListener;
import com.ahmadrosid.dompetku.detail.DetailTransaction;
import com.ahmadrosid.dompetku.detail.DetailTransactionActivity;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.transaction.EditTransactionActivity;
import com.ahmadrosid.dompetku.transaction.NewTransaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.ballance)
    TextView ballanceTextView;
    @BindView(R.id.expend)
    TextView expendTextView;
    @BindView(R.id.list_wallet)
    ListView listWallet;
    @BindView(R.id.fab_pemasukan)
    FloatingActionButton fabPemasukan;
    @BindView(R.id.fab_pengeluaran)
    FloatingActionButton fabPengeluaran;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadData();
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
//                DetailTransactionActivity.start(MainActivity.this, transactions.getId());
            DetailTransaction detailTransaction = new DetailTransaction(MainActivity.this, transactions, new DetailActionListener() {
                @Override
                public void onEditClick() {
                    EditTransactionActivity.start(MainActivity.this, transactions.getId());
                }

                @Override
                public void onDeleteClick() {
                    delete(transactions);
                }
            });

            detailTransaction.show();
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
                            DetailTransactionActivity.start(MainActivity.this, transactions.getId());
                            break;
                        case 1:
                            EditTransactionActivity.start(MainActivity.this, transactions.getId());
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

    @Override
    public void showListTransaksi(List<Transaction> transactions) {
        MainAdapter adapter = new MainAdapter(this, transactions, listViewListener);

        listWallet.setAdapter(adapter);
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

    @OnClick({R.id.fab_pemasukan, R.id.fab_pengeluaran})
    public void onViewClicked(View view) {
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
}
