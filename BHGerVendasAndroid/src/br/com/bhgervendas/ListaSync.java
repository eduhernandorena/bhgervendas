package br.com.bhgervendas;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.bhgervendas.bean.Sync;
import java.util.ArrayList;

public class ListaSync extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Sync> listaSyncs = new ArrayList<Sync>();

        if (getIntent().hasExtra("lista")) {

            listaSyncs = (ArrayList<Sync>) getIntent().getExtras()
                    .getSerializable("lista");
        }

        setListAdapter(new ArrayAdapter<Sync>(this, R.layout.list_item,
                listaSyncs));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
