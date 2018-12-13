package mx.edu.ittepic.tpdm_u3_practica1_juanmejia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    EditText numesa;
    Spinner spinner,spinnerbebida;
    EditText cantidad,cantidadbebida;
    Button agregar,guardar,agregarbebida;
    ListView lista;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference refplatillos;
    DatabaseReference refbebidas;
    ArrayList<String> bebidas;
    ArrayList<String>platillos;
    ArrayList<String>bebidasorder;
    ArrayList<String>platillosorder;
    ArrayAdapter<String> adapterplatillo;
    ArrayAdapter<String>adapterbebida;
    ArrayList<String>comanda;
    ArrayAdapter<String>adaptercomanda;
    int totalpagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        numesa=findViewById(R.id.numesa);
        spinner=findViewById(R.id.spin);
        cantidad=findViewById(R.id.cantidad);
        agregar=findViewById(R.id.agregar);
        guardar=findViewById(R.id.guardar);
        agregarbebida=findViewById(R.id.agregarbebida);
        cantidadbebida=findViewById(R.id.cantidadbebida);
        spinnerbebida=findViewById(R.id.spinbebida);
        lista=findViewById(R.id.lista);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Comanda");
        refplatillos = database.getReference("Platillos");
        refbebidas = database.getReference("Bebidas");
        bebidasorder=new ArrayList<>();
        platillosorder=new ArrayList<>();
        bebidas=new ArrayList<>();
        totalpagar=0;
        adapterbebida=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,bebidas);
        spinnerbebida.setAdapter(adapterbebida);
        platillos=new ArrayList<>();
        adapterplatillo=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,platillos);
        spinner.setAdapter(adapterplatillo);
        comanda=new ArrayList<>();
        adaptercomanda=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,comanda);
        lista.setAdapter(adaptercomanda);

        database.getReference().child("Bebidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bebidas.removeAll(bebidas);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Bebida bebida=snapshot.getValue(Bebida.class);
                    bebidas.add(bebida.getNombre()+" - "+bebida.getPrecio());
                    adapterbebida=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,bebidas);
                    spinnerbebida.setAdapter(adapterbebida);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.getReference().child("Platillos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                platillos.removeAll(platillos);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Platillo platillo=snapshot.getValue(Platillo.class);
                    platillos.add(platillo.getNombre()+" - "+platillo.getPrecio());
                    adapterplatillo=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,platillos);
                    spinner.setAdapter(adapterplatillo);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarplatillo(spinner.getSelectedItem().toString(),
                        Integer.parseInt(cantidad.getText().toString()));
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTotal();
            }
        });

        agregarbebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarbebidaa(spinnerbebida.getSelectedItem().toString(),
                        Integer.parseInt(cantidadbebida.getText().toString()));
            }
        });
    }

    private void guardarTotal() {
        myRef.child(numesa.getText().toString()).setValue(null);
        myRef.child(numesa.getText().toString()).setValue(new Comanda(bebidasorder,platillosorder,totalpagar));
        this.finish();
    }


    private void agregarbebidaa(String bebida, int cantidad) {
        comanda.add(bebida+" - "+cantidad);
        totalpagar+=Integer.parseInt(bebida.split(" - ")[1])*cantidad;
        bebidasorder.add(bebida+" - "+cantidad);
        adaptercomanda=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,comanda);
        lista.setAdapter(adaptercomanda);
    }

    private void agregarplatillo(String platillo, int cantidad) {
        comanda.add(platillo+" - "+cantidad);
        totalpagar+=Integer.parseInt(platillo.split(" - ")[1])*cantidad;
        platillosorder.add(platillo+" - "+cantidad);
        adaptercomanda=new ArrayAdapter<>(Main2Activity.this,android.R.layout.simple_list_item_1,comanda);
        lista.setAdapter(adaptercomanda);
    }
}
