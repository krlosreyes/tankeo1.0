package com.example.tankeo10;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankeo10.Adaptadores.AdapterMovimiento;
import com.example.tankeo10.Adaptadores.AdapterRecarga;
import com.example.tankeo10.Entidades.Movimientos;
import com.example.tankeo10.Entidades.Recargas;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecargaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecargaFragment extends Fragment implements View.OnClickListener{

    AdapterRecarga adapterRecarga;
    RecyclerView recyclerViewRecargas;
    ArrayList<Recargas> recargas;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView nomUser, valorTankeo;
    private MaterialButton btnRecarga;


    public RecargaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecargaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecargaFragment newInstance(String param1, String param2) {
        RecargaFragment fragment = new RecargaFragment();
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


            mAuth = FirebaseAuth.getInstance();
            mDatabase= FirebaseDatabase.getInstance().getReference();
            nomUser = (TextView) nomUser.findViewById(R.id.nomUser);
            btnRecarga = (MaterialButton) btnRecarga.findViewById(R.id.btnRecarga);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recarga, container, false);
        recyclerViewRecargas = view.findViewById(R.id.recyclerRec);
        recargas =  new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nomUser = view.findViewById(R.id.nomUser);
        valorTankeo = view.findViewById(R.id.valorTankeo);
        btnRecarga = view.findViewById(R.id.btnRecarga);


        btnRecarga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recargar();
            }
        });

        getUserInfo();
        cargarLista();
        mostrarData();
        return view;
    }

    private void recargar() {


    }


    public void cargarLista() {

        recargas.add(new Recargas("01-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas(" 02-09-2019", "Recarga ", "50000"));
        recargas.add(new Recargas("03-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas(" 04-09-2019", "Recarga ", "50000"));
        recargas.add(new Recargas("05-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas(" 06-09-2019", "Recarga ", "50000"));
        recargas.add(new Recargas("07-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas("01-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas(" 02-09-2019", "Recarga ", "50000"));
        recargas.add(new Recargas("03-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas(" 04-09-2019", "Recarga ", "50000"));
        recargas.add(new Recargas("05-06-2021", "Tankeo ", "10000"));
        recargas.add(new Recargas(" 06-09-2019", "Recarga ", "50000"));
        recargas.add(new Recargas("07-06-2021", "Tankeo ", "10000"));

    }

    public void mostrarData() {
        recyclerViewRecargas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecarga = new AdapterRecarga(getContext(),recargas);
        recyclerViewRecargas.setAdapter(adapterRecarga);
    }

    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String apellido = dataSnapshot.child("apellido").getValue().toString();

                    nomUser.setText(nombre+" "+ apellido);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {



    }
}