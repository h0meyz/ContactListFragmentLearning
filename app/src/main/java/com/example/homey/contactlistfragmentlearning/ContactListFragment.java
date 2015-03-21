package com.example.homey.contactlistfragmentlearning;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Homey on 3/20/2015.
 */
public class ContactListFragment extends ListFragment {

    private Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = new Activity();
        List<String> contactNames = getContactNames();
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, contactNames));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment,container,false );
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String prompt = "clicked item " + getListView().getItemAtPosition(position).toString() + "\n\n";
        prompt += "selected items: \n";
        SparseBooleanArray sparseBooleanArray = getListView().getCheckedItemPositions();
        for(int i=0;i<getListView().getCount();i++){
            if(sparseBooleanArray.get(i)){
                prompt += getListView().getItemAtPosition(i).toString()+"\n";
            }
        }
        Toast.makeText(getActivity(),prompt,Toast.LENGTH_LONG).show();
    }

    public List<String> getContactNames(){

        ContentResolver contentResolver = mActivity.getContentResolver();
        List<String> contactNames = new ArrayList<String>();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,null, null, null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                contactNames.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            }
        }
        return contactNames;
    }
}
