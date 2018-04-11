package com.ahmadrosid.dompetku.utils.models;

/**
 * Created by staf on 04-Oct-17.
 */

public interface TransactionListener {
    void success();
    void failed(String message);
}