package com.ahmadrosid.dompetku.transaction;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.main.MainContract;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.utils.Calculator;
import com.ahmadrosid.dompetku.utils.CalculatorListener;
import com.ahmadrosid.dompetku.utils.TitlePicker;
import com.ahmadrosid.dompetku.utils.TitlePickerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 * @update by tyangjawi03
 */

public class EditTransaction extends Dialog implements View.OnClickListener, TransactionContract.EditView {

    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_done)
    ImageView imgDone;
    @BindView(R.id.item_name)
    AppCompatEditText itemName;
    @BindView(R.id.item_amount)
    AppCompatEditText itemAmount;
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.calculator)
    Calculator calculator;
    @BindView(R.id.title_picker)
    TitlePicker titlePicker;

    private Transaction transaction;

    private MainContract.PopUpListener popUpListener;

    private TransactionContract.Presenter presenter;

    public EditTransaction(Context context, Transaction transaction, MainContract.PopUpListener listener) {
        super(context);
        popUpListener = listener;
        presenter = new TransactionPresenter(this);
        this.transaction = transaction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_transaction_bottomset);
        ButterKnife.bind(this);

        if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
            title.setText("Pemasukan");
            titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            title.setText("Pengeluaran");
            titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
        }

        itemName.setText(transaction.title);
        itemAmount.setText(transaction.amount+"");

        calculator.setListener(new CalculatorListener() {
            @Override
            public void result(int amount) {
                if (amount > 0) {
                    itemAmount.setText(amount + "");
                }
                calculator.setVisibility(View.GONE);
                itemName.requestFocus();
                titlePicker.setVisibility(View.VISIBLE);
            }
        });

        titlePicker.setListener(titlePickerListener);

        presenter.loadTitles(getTitlesListener);

        itemName.addTextChangedListener(textWatcher);

        titlePicker.setVisibility(View.INVISIBLE);

        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        itemAmount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                itemAmount.requestFocus();
                calculator.setVisibility(View.VISIBLE);
                titlePicker.setVisibility(View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            }
        });

        itemName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                itemName.requestFocus();
                calculator.setVisibility(View.GONE);
                titlePicker.setVisibility(View.VISIBLE);
                imm.showSoftInput(view, 0);
                return true;
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            presenter.loadTitles(charSequence, getTitlesListener);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TitlePickerListener titlePickerListener = new TitlePickerListener() {
        @Override
        public void onClickListener(String title) {
            itemName.setText(title);
        }
    };

    TransactionContract.GetTitlesListener getTitlesListener = new TransactionContract.GetTitlesListener() {
        @Override
        public void success(List<String> data) {
            titlePicker.setTextList(data);
        }

        @Override
        public void failed(String message) {

        }
    };

    @OnClick({R.id.img_close, R.id.img_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                dismiss();
                break;
            case R.id.img_done:
                process();
                break;
        }
    }

    private void process() {
        if (itemName.getText().toString().isEmpty()) {
            popUpListener.failed("Please input title.");
        } else if (itemAmount.getText().toString().isEmpty()) {
            popUpListener.failed("Please input amount.");
        } else {

            presenter.updateTransaction(
                    transaction.getId(),
                    itemName.getText().toString(),
                    Integer.parseInt(itemAmount.getText().toString()),
                    transaction.type
            );

        }
    }

    @Override
    public void showData(Transaction transaction) {

        popUpListener.success();

        dismiss();
    }

    @Override
    public void showError(String message) {
        popUpListener.failed(message);
    }

}
