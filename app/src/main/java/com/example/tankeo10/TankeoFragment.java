package com.example.tankeo10;

import android.app.Activity;
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
import com.example.tankeo10.Entidades.Movimientos;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecargaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TankeoFragment extends Fragment implements View.OnClickListener{

    AdapterMovimiento adapterMovimiento;
    RecyclerView recyclerViewMovimientos;
    ArrayList<Movimientos> movimientos;


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
    private MaterialButton scanBtn;


    public TankeoFragment() {
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
    public static TankeoFragment newInstance(String param1, String param2) {
        TankeoFragment fragment = new TankeoFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tankeo, container, false);
        recyclerViewMovimientos = view.findViewById(R.id.recyclerMov);
        movimientos =  new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nomUser = view.findViewById(R.id.nomUser);
        scanBtn = view.findViewById(R.id.scanBtn);
        valorTankeo = view.findViewById(R.id.valorTankeo);

        scanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              escanear();
            }
        });

        getUserInfo();
        cargarLista();
        mostrarData();
        return view;
    }

    private void escanear() {
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(TankeoFragment.this);
        intentIntegrator.setPrompt("Escanear Codigo");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(getContext(), "Escaner Cancelado", Toast.LENGTH_SHORT).show();
            }else{
                valorTankeo.setText(result.getContents().toString());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void cargarLista() {

       movimientos.add(new Movimientos("01-06-2021", "Tankeo ", "10000"));
       movimientos.add(new Movimientos(" 02-09-2019", "Recarga ", "50000"));
       movimientos.add(new Movimientos("03-06-2021", "Tankeo ", "10000"));
       movimientos.add(new Movimientos(" 04-09-2019", "Recarga ", "50000"));
        movimientos.add(new Movimientos("05-06-2021", "Tankeo ", "10000"));
        movimientos.add(new Movimientos(" 06-09-2019", "Recarga ", "50000"));
        movimientos.add(new Movimientos("07-06-2021", "Tankeo ", "10000"));
        movimientos.add(new Movimientos(" 08-09-2019", "Recarga ", "50000"));
        movimientos.add(new Movimientos("09-06-2021", "Tankeo ", "10000"));
        movimientos.add(new Movimientos(" 10-09-2019", "Recarga ", "50000"));
        movimientos.add(new Movimientos("11-06-2021", "Tankeo ", "10000"));
        movimientos.add(new Movimientos(" 12-09-2019", "Recarga ", "50000"));
        movimientos.add(new Movimientos("13-06-2021", "Tankeo ", "10000"));
        movimientos.add(new Movimientos(" 14-09-2019", "Recarga ", "50000"));
        movimientos.add(new Movimientos("15-06-2021", "Tankeo ", "10000"));
        movimientos.add(new Movimientos(" 16-09-2019", "Recarga ", "50000"));
    }

    public void mostrarData() {
        recyclerViewMovimientos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMovimiento = new AdapterMovimiento(getContext(),movimientos);
        recyclerViewMovimientos.setAdapter(adapterMovimiento);
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