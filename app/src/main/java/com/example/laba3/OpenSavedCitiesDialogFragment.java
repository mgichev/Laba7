package com.example.laba3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class OpenSavedCitiesDialogFragment extends DialogFragment {

    TextView savedCapitalText;
    int id = 0;
    String name = "";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(R.layout.open_saved_city_dialog_fragment)
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                        SQLiteDatabase db = databaseHelper.getReadableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(DatabaseHelper.COLUMN_SAVED_CITY_NAME, name);
                        cv.put(DatabaseHelper.COLUMN_CURRENT_USER_ID, User.getCurrentUserID());
                        Cursor cursor = db.rawQuery("SELECT * FROM "
                                + DatabaseHelper.TABLE_SAVED_CITIES
                                + " WHERE " + DatabaseHelper.COLUMN_SAVED_CITY_NAME +  " like '%" + name + "%'", null);
                        boolean tryInsert = true;
                        while (cursor.moveToNext()) {
                            if (cursor.getString(1).equals(name) && cursor.getInt(2) == User.getCurrentUserID())
                                tryInsert = false;
                        }
                        cursor.close();
                        if (tryInsert) {
                            db.insert(DatabaseHelper.TABLE_SAVED_CITIES, null, cv);
                            Toast.makeText(getDialog().getContext(), name + " - город добавлен", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getDialog().getContext(), name + " - город уже добавлен", Toast.LENGTH_LONG).show();
                        }
                        db.close();

                    }
                })
                .setNegativeButton("Отмена", null);


        return builder.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        Context context = dialog.getContext();
        String capital = getArguments().getString("capital");
        name = capital;
        id = getArguments().getInt("id");
        savedCapitalText = dialog.findViewById(R.id.saved_capital_text);
        savedCapitalText.setText(capital);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}