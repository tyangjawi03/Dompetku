package com.ahmadrosid.dompetku.transaction;

import com.ahmadrosid.dompetku.models.Transaction;

import java.util.List;

/**
 * Created by staf on 03-Oct-17.
 */

public interface TransactionContract {

    interface GetTitlesListener {
        void success(List<String> data);
        void failed(String message);
    }

    interface AddTransactionListener {
        void success(Transaction transaction);
        void failed(String message);
    }

    interface EditTransactionListener {
        void success(Transaction transaction);
        void failed(String message);
    }
    interface EditView {
        void showData(Transaction transaction);
        void showError(String message);
    }

    interface DeleteTransactionListener {
        void success();
        void failed(String message);
    }

    interface Presenter {
        void loadTitles(GetTitlesListener titleListener);
        void loadTitles(CharSequence search, GetTitlesListener titleListener);
        void loadTransaction(long id);
        void createTransaction(String title, int amount, Transaction.TransactionType type);
        void updateTransaction(long id, String title, int amount, Transaction.TransactionType type);
        void deleteTransaction(long id);
    }

}
