package com.huso.sen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.ArrayList;

public class fag1 extends Fragment {
    ListView listView;
    ArrayList<String> namearray;
    ArrayList<Integer> idarray;
    ArrayAdapter arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fag1,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.listview);
        namearray=new ArrayList<String>();
        idarray=new ArrayList<Integer>();
        arrayAdapter=new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,namearray);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavDirections actions=fag1Directions.actionFag1ToFag2();
                Bundle bundle=new Bundle();
                bundle.putInt("artId",idarray.get(position));
                bundle.putString("info","old");
                fag2 grup=new fag2();
                grup.setArguments(bundle);
                Navigation.findNavController(view).navigate(actions);
            }
        });
        getdata();
    }

    public void getdata(){
        try {
            SQLiteDatabase database=this.getActivity().openOrCreateDatabase("Arts", Context.MODE_PRIVATE,null);

            Cursor cursor=database.rawQuery("SELECT*FROM arts",null);
            int nameIx=cursor.getColumnIndex("artname");
            int idIx=cursor.getColumnIndex("id");
            while (cursor.moveToNext()){
                namearray.add(cursor.getString(nameIx));
                idarray.add(cursor.getInt(idIx));
            }
            arrayAdapter.notifyDataSetChanged();//yeni veri ekledim bir zahmet bunu göster
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
