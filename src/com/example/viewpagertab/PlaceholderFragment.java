package com.example.viewpagertab;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinitescrolllistview.lib.IInfiniteScrollListener;
import com.infinitescrolllistview.lib.InfiniteScrollListView;
import com.infinitescrolllistview.lib.InfiniteScrollOnScrollListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements IInfiniteScrollListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

    InfiniteScrollListView listView;
    MyAdapter adapter;
    InfiniteScrollOnScrollListener scrollListener;
    ListTask listTask;
    
    boolean executing = false;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		System.out.println("onCreateView Called");
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		listView = (InfiniteScrollListView) rootView.findViewById(R.id.list_view);
        scrollListener = new InfiniteScrollOnScrollListener(this);
        listView.setListener(scrollListener);
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            String str = "Index: " + String.valueOf(i);
            items.add(str);
        }
        listView.appendItems(items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                String product = ((TextView)((RelativeLayout) view).findViewById(R.id.text_main)).getText().toString();
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("product", product);
                Toast.makeText(getActivity(), "product  " + product + "  " + position + "  "+  id , Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

		return rootView;
	}
	@Override
	 public void onScrollCalled(int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	 }
	
	@Override
    public void endIsNear() {
        if (!executing) {
            Toast.makeText(getActivity(), "End is near", Toast.LENGTH_SHORT).show();
            executing = true;
            listTask = new ListTask();
            listTask.execute(listView.getRealCount());
        }
    }
	
	private class ListTask extends AsyncTask<Integer, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> items = new ArrayList<String>();
            if (params[0] < 40) {
                for (int i = params[0]; i < (params[0] + 8); i++) {
                    String str = "Index: " + String.valueOf(i);
                    items.add(str);
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            listView.appendItems(result);
            executing = false;
            if (result.size() > 0) {
                Toast.makeText(getActivity(), "Loaded " + String.valueOf(result.size()) + " items", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getActivity(), "No more items to load", Toast.LENGTH_SHORT).show();
            }
        }
    }
}