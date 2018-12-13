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

public class Main5Activity extends AppCompatActivity {
    EditText nombrebebida,preciobebida;
    Button agregarbebida,cancelarbebida;
    ListView lista;
    Button editarbebida,borrarbebida;
    int indice;
    List<String>bebidas;
    ArrayAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        nombrebebida=findViewById(R.id.nombrebebida);
        preciobebida=findViewById(R.id.preciobebida);
        agregarbebida=findViewById(R.id.agregarbebida);
        cancelarbebida=findViewById(R.id.cancelarbebida);
        lista=findViewById(R.id.lista);
        editarbebida=findViewById(R.id.editarbebida);
        borrarbebida=findViewById(R.id.borrarbebida);
        indice=-1;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bebidas");

        bebidas=new ArrayList<>();
        adapter=new ArrayAdapter<>(Main5Activity.this,android.R.layout.simple_list_item_1,bebidas);
        lista.setAdapter(adapter);

        database.getReference().child("Bebidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bebidas.removeAll(bebidas);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Bebida bebida=snapshot.getValue(Bebida.class);
                    bebidas.add(bebida.getNombre()+" - "+bebida.getPrecio());
                    adapter=new ArrayAdapter<>(Main5Activity.this,android.R.layout.simple_list_item_1,bebidas);
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
                nombrebebida.setText(lista.getItemAtPosition(indice).toString().split(" - ")[0]);
                preciobebida.setText(lista.getItemAtPosition(indice).toString().split(" - ")[1]);
            }
        });

        cancelarbebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main5Activity.this.finish();
            }
        });

        agregarbebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevo(nombrebebida.getText().toString(),Integer.parseInt(preciobebida.getText().toString()));
            }
        });

        editarbebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarElemento(indice);
            }
        });

        borrarbebida.setOnClickListener(new View.OnClickListener() {
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
            List<String> temp=bebidas;
            myRef.setValue("null");
            for (int i=0;i<temp.size();i++){
                if(indice!=i){
                    myRef.push().setValue(new Bebida(temp.get(i).split(" - ")[0],Integer.parseInt(temp.get(i).split(" - ")[1])));
                }
            }
        }
        this.indice=-1;
    }

    private void editarElemento(int indice) {
        if(indice==-1){
            Toast.makeText(this,"Seleccione un elemento",Toast.LENGTH_SHORT).show();
        }else{
            List<String> temp=bebidas;
            myRef.setValue("null");
            for (int i=0;i<temp.size();i++){
                if(indice!=i){
                    myRef.push().setValue(new Bebida(temp.get(i).split(" - ")[0],Integer.parseInt(temp.get(i).split(" - ")[1])));
                }else{
                    myRef.push().setValue(new Bebida(nombrebebida.getText().toString(),Integer.parseInt(preciobebida.getText().toString())));
                }
            }
        }
        this.indice=-1;
    }

    private void agregarNuevo(String nombre, int precio) {
        Bebida platillo=new Bebida(nombre,precio);
        myRef.push().setValue(platillo);
    }
}
