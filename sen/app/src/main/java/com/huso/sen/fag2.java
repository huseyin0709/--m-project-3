package com.huso.sen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class fag2 extends Fragment {
    EditText grubunadi;
    Button grupkurbutton;
    SQLiteDatabase database;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fag2,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database=this.getActivity().openOrCreateDatabase("Arts", Context.MODE_PRIVATE,null);



        Bundle bnd=this.getArguments();
        String dt = bnd.getString("info");


        if (dt.matches("old")) {
            int artId = bnd.getInt("artId", 1);
            grupkurbutton.setVisibility(View.INVISIBLE);
            try {
                Cursor cursor = database.rawQuery("SELECT*FROM arts WHERE id=?", new String[]{String.valueOf(artId)});

                int artnameIx = cursor.getColumnIndex("artname");
                while (cursor.moveToNext()) {
                    grubunadi.setText(cursor.getString(artnameIx));

                }
                cursor.close();
            } catch (Exception e) {

            }

        }




        grubunadi=view.findViewById(R.id.nametext);
        grupkurbutton=view.findViewById(R.id.button);
        grupkurbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String artname=grubunadi.getText().toString();//bu ve bunun altindakilerde sitring degere ceviririz

                try {
                    database=getActivity().openOrCreateDatabase("Arts",Context.MODE_PRIVATE,null );//bu ve altaki satır veritabanı kayıt icindir
                    database.execSQL("CREATE TABLE IF NOT EXISTS arts(id INTEGER PRIMARY KEY,artname VARCHAR)");
                    String sqlstring="INSERT INTO arts (artname) VALUES (?)";
                    SQLiteStatement sqLiteStatement=database.compileStatement(sqlstring);
                    sqLiteStatement.bindString(1,artname);//buna ozel indexler 1 den baslar.Her bir soru isaret degerini belirliyoruz
                    sqLiteStatement.execute();
                }catch (Exception e){


                }
                NavDirections actions=fag2Directions.actionFag2ToFag1();
                Navigation.findNavController(v).navigate(actions);
            }
        });

    }
}
