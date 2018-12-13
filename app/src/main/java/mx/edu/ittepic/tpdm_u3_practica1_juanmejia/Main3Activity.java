package mx.edu.ittepic.tpdm_u3_practica1_juanmejia;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    EditText mesa,total;
    Button buscar,pagado;
    ListView lista;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> comandalist;
    ArrayAdapter<String> comandaadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mesa=findViewById(R.id.mesa);
        total=findViewById(R.id.total);
        buscar=findViewById(R.id.buscar);
        pagado=findViewById(R.id.pagado);
        lista=findViewById(R.id.lista);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Comanda");
        comandalist=new ArrayList<>();
        comandaadapter=new ArrayAdapter<>(Main3Activity.this,android.R.layout.simple_list_item_1,comandalist);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarMesa(mesa.getText().toString());
            }
        });
    }

    private void buscarMesa(String mesa) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Comanda comanda=dataSnapshot.getValue(Comanda.class);
                llenarCampos(comanda);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarCampos(Comanda comanda) {
        llenarLista(comanda.getBebidas());
        llenarLista(comanda.getPlatillos());
        total.setText(comanda.getTotal());
    }

    private void llenarLista(ArrayList<String> otraLista) {
        for(int i=0;i<otraLista.size();i++){
            comandalist.add(otraLista.get(i));
        }
        comandalist=new ArrayList<>();
        comandaadapter=new ArrayAdapter<>(Main3Activity.this,android.R.layout.simple_list_item_1,comandalist);


    }
}//class
