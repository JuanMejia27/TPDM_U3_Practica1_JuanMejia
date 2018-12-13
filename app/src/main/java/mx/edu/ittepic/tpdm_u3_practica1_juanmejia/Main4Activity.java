package mx.edu.ittepic.tpdm_u3_practica1_juanmejia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {
    EditText nombre,precio;
    Button agregar,cancelar;
    ListView lista;
    Button editar,borrar;
    int indice;
    List<String> platillos;
    ArrayAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        nombre=findViewById(R.id.nombre);
        precio=findViewById(R.id.precio);
        agregar=findViewById(R.id.agregar);
        cancelar=findViewById(R.id.cancelar);
        lista=findViewById(R.id.lista);
        editar=findViewById(R.id.editar);
        borrar=findViewById(R.id.borrar);
        indice=-1;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Platillos");


        platillos=new ArrayList<>();
        adapter=new ArrayAdapter<>(Main4Activity.this,android.R.layout.simple_list_item_1,platillos);
        lista.setAdapter(adapter);

        database.getReference().child("Platillos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                platillos.removeAll(platillos);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Platillo platillo=snapshot.getValue(Platillo.class);
                    platillos.add(platillo.getNombre()+" - "+platillo.getPrecio());
                    adapter=new ArrayAdapter<>(Main4Activity.this,android.R.layout.simple_list_item_1,platillos);
                    lista.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indice=position;
                nombre.setText(lista.getItemAtPosition(indice).toString().split(" - ")[0]);
                precio.setText(lista.getItemAtPosition(indice).toString().split(" - ")[1]);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main4Activity.this.finish();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevo(nombre.getText().toString(),Integer.parseInt(precio.getText().toString()));
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarElemento(indice);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarElemento(indice);
            }
        });
    }

    private void borrarElemento(int indice) {
        if(indice==-1){
            Toast.makeText(this,"Seleccione un elemento",Toast.LENGTH_SHORT).show();
        }else{
            List<String> temp=platillos;
            myRef.setValue("null");
            for (int i=0;i<temp.size();i++){
                if(indice!=i){
                    myRef.push().setValue(new Platillo(temp.get(i).split(" - ")
                            [0],Integer.parseInt(temp.get(i).split(" - ")[1])));
                }
            }
        }
        this.indice=-1;
    }

    private void editarElemento(int indice) {
        if(indice==-1){
            Toast.makeText(this,"Seleccione un elemento",Toast.LENGTH_SHORT).show();
        }else{
            List<String> temp=platillos;
            myRef.setValue("null");
            for (int i=0;i<temp.size();i++){
                if(indice!=i){
                    myRef.push().setValue(new Platillo(temp.get(i).split(" - ")
                            [0],Integer.parseInt(temp.get(i).split(" - ")[1])));
                }else{
                    myRef.push().setValue(new Platillo(nombre.getText().toString(),Integer.parseInt(precio.getText().toString())));
                }
            }
        }
        this.indice=-1;
    }

    private void agregarNuevo(String nombre, int precio) {
        Platillo platillo=new Platillo(nombre,precio);
        myRef.push().setValue(platillo);
    }
}
