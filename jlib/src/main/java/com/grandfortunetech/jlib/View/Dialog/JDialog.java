package com.grandfortunetech.jlib.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by jeff.hsu on 2016/9/23.
 */
public class JDialog {
    private AlertDialog.Builder mBuilder;
    private boolean mBlnCanceledOnTouchOutside = true;

    private onButtonListener mButtonListener;

    public interface onButtonListener {
        void onYesClick();

        void onNoClick();
    }

    public JDialog setOnButtonListener(onButtonListener l) {
        mButtonListener = l;
        return this;
    }

    private onItemListener mItemListener;

    public interface onItemListener {
        void onItemClick(int which, boolean isChecked);
    }

    public JDialog setOnItemListener(onItemListener l) {
        mItemListener = l;
        return this;
    }

    public JDialog(Context _context) {
        mBuilder = new AlertDialog.Builder(_context);
    }

    public JDialog setCanceledOnTouchOutside(boolean cancel)
    {
        mBlnCanceledOnTouchOutside = cancel;
        return this;
    }

    public JDialog setIcon(Drawable icon) {
        mBuilder.setIcon(icon);
        return this;
    }

    public JDialog setTitle(String _title) {
        mBuilder.setTitle(_title);
        return this;
    }

    public JDialog setMessage(String _message) {
        mBuilder.setMessage(_message);
        return this;
    }

    public JDialog setYes(String _str) {
        mBuilder.setPositiveButton(_str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (mButtonListener != null)
                    mButtonListener.onYesClick();
            }
        });
        return this;
    }

    public JDialog setNo(String _str) {
        mBuilder.setNegativeButton(_str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (mButtonListener != null)
                    mButtonListener.onNoClick();
            }
        });
        return this;
    }

    public JDialog setView(View _view)
    {
        mBuilder.setView(_view);
        return this;
    }

    public JDialog setItems(CharSequence[] items)
    {
        mBuilder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (mItemListener != null)
                    mItemListener.onItemClick(item, true);
            }
        });
        return this;
    }

    public JDialog setSingleChoiceItems(CharSequence[] items, int checkedItem)
    {
        mBuilder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (mItemListener != null)
                    mItemListener.onItemClick(item, true);
            }
        });
        return this;
    }

    public JDialog setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems)
    {
        mBuilder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (mItemListener != null)
                    mItemListener.onItemClick(which, isChecked);
            }
        });
        return this;
    }

    public Dialog create()
    {
        Dialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(mBlnCanceledOnTouchOutside);
        return dialog;
    }

}

