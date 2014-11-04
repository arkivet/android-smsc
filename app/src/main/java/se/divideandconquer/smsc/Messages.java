package se.divideandconquer.smsc;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class Messages extends ActionBarActivity {

    private static final String TAG = "MyActivity";
    public static final String BODY = "body";
    public static final String SERVICE_CENTER = "service_center";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        final ListView listview = (ListView) findViewById(R.id.listView);

        String[] columns = new String[]{
                BODY,
                SERVICE_CENTER
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.textView,
                R.id.textView2,
        };

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        listview.setAdapter(new SimpleCursorAdapter(this, R.layout.message_item, new CursorWrapper(cursor) {

            @Override
            public String getString(int columnIndex) {

                String value = super.getString(columnIndex);
                if (columnIsServiceCenter(columnIndex)) {
                    if (value.equals("+46705008999")) {
                        value += " Telia";
                    } else if (value.equals("+46708000708")) {
                        value += " Telenor";
                    } else if (value.equals("+46707990001") || value.equals("+46707990002") || value.equals("+46707990003")) {
                        value += " Comviq";
                    } else if (value.equals("+46735480000") || value.equals("46735480011")) {
                        value += " Tre";
                    }
                }
                return value;
            }

            private boolean columnIsServiceCenter(int columnIndex) {
                return columnIndex == getColumnIndex(SERVICE_CENTER);
            }


        }, columns, to, 0));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
