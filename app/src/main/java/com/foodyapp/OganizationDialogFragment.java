package com.foodyapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OganizationDialogFragment extends DialogFragment {

    private Activity activity;

    organizationsAlertDialogFragmentListener mListener;;

    // Override the Fragment.onAttach() method to instantiate the MyAlertDialogFragmentListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the MyAlertDialogFragmentListener so we can send events to the host
            mListener = (organizationsAlertDialogFragmentListener) activity;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_organization_fragment, null);





String[] orgList={"Latet ", "Yad Eliezer", "Lasova", "Pithon Lev", };
        ListView listOrg=(ListView) v.findViewById(R.id.listOrg);
        ArrayAdapter<String> list= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,orgList);
        listOrg.setAdapter(list);
        listOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    Toast.makeText(activity, "Your choice: "+ orgList[position], Toast.LENGTH_SHORT).show();
                   getDialog().dismiss();
                }else if (position==1){
                    Toast.makeText(activity, "Your choice: "+ orgList[position], Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else if (position==2){
                    Toast.makeText(activity, "Your choice: "+ orgList[position], Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else if (position==3){
                    Toast.makeText(activity, "Your choice: "+ orgList[position], Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }
            }
        });


        return v;

    }


}
