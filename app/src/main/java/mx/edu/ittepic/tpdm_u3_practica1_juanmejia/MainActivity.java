package mx.edu.ittepic.tpdm_u3_practica1_juanmejia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lista=findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LlamarActivity(lista.getItemAtPosition(position).toString());
            }
        });

    }

    private void LlamarActivity(String item) {
        switch (item){
            case "Levantar pedido":
                Intent n=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(n);
                break;
            case "Cobrar":
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                Intent b=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(b);
                break;
            case "Exportar CVS":
                break;
            case "Salir":
                MainActivity.this.finish();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.platillos) {
            Intent i=new Intent(MainActivity.this,Main4Activity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.bebidad) {
            Intent i=new Intent(MainActivity.this,Main5Activity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//onOptins
}//class
