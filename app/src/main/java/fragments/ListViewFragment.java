package fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scott.freestyle.R;

import interfaces.ListViewListener;

/**
 * Created by scott on 9/8/15.
 */
public class ListViewFragment extends Fragment {



    public static ListViewFragment newInstance() {
        ListViewFragment frag = new ListViewFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListViewListener) {
            ((ListViewListener)context).listViewUpdate();
        }
    }
}
