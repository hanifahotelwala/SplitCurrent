package com.example.android.splitfeatures.workoutsplit;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.android.splitfeatures.R;


import java.util.ArrayList;

public class CustomAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private ArrayList<Parent> mParent;

    public CustomAdapter(Context context, ArrayList<Parent> parent){
        mParent = parent;
        inflater = LayoutInflater.from(context);
    }

    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return mParent.get(i).getArrayChildren().size();
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getTitle();
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).getArrayChildren().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
           view = inflater.inflate(R.layout.list_item_parent, viewGroup,false);
        }

        TextView textView = view.findViewById(R.id.textViewParent);
        //"i" is the position of the parent/group in the list
        textView.setText(getGroup(i).toString());

        //return the entire view
        return view;
    }

    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_child, viewGroup,false);
        }

        TextView textView = view.findViewById(R.id.textViewChild);
        //"i" is the position of the parent/group in the list and
        //"i1" is the position of the child
        textView.setText(mParent.get(i).getArrayChildren().get(i1));

        //return the entire view
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }

}
