package de.thm.de.simpleadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ListView listView1;
	private Ergebnisse resultData[];
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        		resultData = new Ergebnisse[]
                {
        				new Ergebnisse("Cloudy", 1),
                        new Ergebnisse("Showers", 2),
                        new Ergebnisse("Snow", 3),
                        new Ergebnisse("Storm", 4),
                        new Ergebnisse("Sunny", 5),
                        new Ergebnisse("Cloudy", 6)
                };
                
                initListView(resultData);
    }
    
    private void initListView(Ergebnisse[] res)
    {
    	ResultAdapter adapter 	= new ResultAdapter(this, R.layout.listview_item_row, res);
        listView1 				= (ListView)findViewById(R.id.lV1);
         
        View header				= (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        
        listView1.addHeaderView(header);
        listView1.setAdapter(adapter);
        
        Button b = (Button) findViewById(R.id.bttnAwnser);
        b.setOnClickListener(new Listener(adapter, getApplicationContext()));
    }
   
}
class Listener implements OnClickListener
{

	ResultAdapter resA = null;
	Context con;
	
	public Listener(ResultAdapter resA, Context con){
		this.resA = resA;
		this.con = con;
	}
	@Override
	public void onClick(View v) {
		
		Toast.makeText(con, "Die richtige Antwort ist: "+resA.getAwnser(), Toast.LENGTH_LONG).show();
		
	}
	
}