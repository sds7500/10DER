package com.somya.appdevproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link provide_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class provide_fragment extends Fragment {
    Button active;

    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    String Area_of_intrest;
    String UserId=fAuth.getCurrentUser().getUid();
    DocumentReference df=fstore.collection("users").document(UserId);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public provide_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment provide_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static provide_fragment newInstance(String param1, String param2) {
        provide_fragment fragment = new provide_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_provide_fragment, container, false);
       active=v.findViewById(R.id.activebtn);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Area_of_intrest=documentSnapshot.getString("Area of Intrest");
            }
        });
        active.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(Area_of_intrest.equals("Clothing"))
                    startActivity(new Intent(getContext(), providerActive_Activity.class));
                    else if(Area_of_intrest.equals("Furniture"))
                    startActivity(new Intent(getContext(),furnitureActive_Activity.class));

                }
        });


       return v;
    }
}