package e.iantm.recommendationapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/************************************************************
 Author - Ian McManus
 Version - 1.0.0
 Date - 30/04/2019
 Description - Fragment the settings instructions on initial install

 ************************************************************/

public class SettingsInstructionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_settings_instructions, null);

        ImageView button = (ImageView) view.findViewById(R.id.imageView5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .remove(SettingsInstructionsFragment.this).commit();
            }
        });

        return view;
    }//end OnCreateView
}//end class