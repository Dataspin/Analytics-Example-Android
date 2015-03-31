package io.dataspin.analyticsexample;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import io.rwilinski.tracely.*;

import org.w3c.dom.Text;

import java.util.List;

import io.dataspin.analyticsSDK.*;


public class AnalyticsActivity extends ActionBarActivity implements IDataspinListener {

    private Spinner itemsSpinner;
    private Spinner eventsSpinner;
    private TextView configLabel;
    private TextView statusLabel;
    private int currentItemIndex;
    private int currentEventIndex;
    private TextView apiKeyLabel;
    private TextView clientNameLabel;

    private Button setupButton;
    private Button registerUserButton;
    private Button registerDeviceButton;
    private Button startSessionButton;
    private Button endSessionButton;
    private Button getItemsButton;
    private Button getEventsButton;
    private Button buyButton;
    private Button registerCustomEventButton;
    private Button getBacklogTasksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this.getApplicationContext();
        super.onCreate(savedInstanceState);

        TracelyManager.SetApiKey("33bbfb23d4c55e2f61a9317f4ac1ed99891c8d52");
        TracelyManager.RegisterExceptionHandler(getApplicationContext());

        setContentView(R.layout.activity_analytics);

        new DataspinManager(this);

        apiKeyLabel = (TextView) findViewById(R.id.apiKeyField);
        clientNameLabel = (TextView) findViewById(R.id.clientLabel);
        configLabel = (TextView) findViewById(R.id.configLabel);
        statusLabel = (TextView) findViewById(R.id.statusLabel);

        setupButton = (Button) findViewById(R.id.setupButton);
        setupButton.setEnabled(true);
        setupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Dataspin setup! APIKey: "+apiKeyLabel.getText().toString());
                registerUserButton.setEnabled(true);
                DataspinManager.Instance().SetApiKey(clientNameLabel.getText().toString(), apiKeyLabel.getText().toString(), "1.0", true, context);
                configLabel.setText(clientNameLabel.getText().toString()+", Key:"+apiKeyLabel.getText().toString()+", Version 1.0, Debug mode");
            }
        });


        registerUserButton = (Button) findViewById(R.id.registerUserButton);
        registerUserButton.setEnabled(false);
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Registering user...");
                DataspinManager.Instance().RegisterUser("Jan", "Karol", "zawsze.drugi@watykan.io", null, null);
            }
        });

        registerDeviceButton = (Button) findViewById(R.id.registerDeviceButton);
        registerDeviceButton.setEnabled(false);
        registerDeviceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Registering device...");
                DataspinManager.Instance().RegisterDevice(null);
            }
        });

        startSessionButton = (Button) findViewById(R.id.startSessionButton);
        startSessionButton.setEnabled(false);
        startSessionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Starting session...");
                DataspinManager.Instance().StartSession();
            }
        });

        endSessionButton = (Button) findViewById(R.id.endSessionButton);
        endSessionButton.setEnabled(false);
        endSessionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Ending session...");
                DataspinManager.Instance().EndSession();
            }
        });

        getItemsButton = (Button) findViewById(R.id.getItemsButton);
        getItemsButton.setEnabled(false);
        getItemsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Getting items...");
                DataspinManager.Instance().GetItems();
            }
        });

        getEventsButton = (Button) findViewById(R.id.getEventsButton);
        getEventsButton.setEnabled(false);
        getEventsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Getting events...");
                DataspinManager.Instance().GetEvents();
            }
        });

        buyButton = (Button) findViewById(R.id.buyButton);
        buyButton.setEnabled(false);
        buyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Purchase...");
                DataspinManager.Instance().PurchaseItem(DataspinManager.Instance().AvailableItems.get(currentItemIndex).internal_id, 1, -1);
            }
        });

        registerCustomEventButton = (Button) findViewById(R.id.registerCustomEventButton);
        registerCustomEventButton.setEnabled(false);
        registerCustomEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Registering...");
                DataspinManager.Instance().RegisterCustomEvent(DataspinManager.Instance().AvailableEvents.get(currentEventIndex).id, null);
            }
        });


        //Spinner
        itemsSpinner = (Spinner) findViewById(R.id.itemsSpinner);
        itemsSpinner.setEnabled(false);
        itemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentItemIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        eventsSpinner = (Spinner) findViewById(R.id.eventsSpinner);
        eventsSpinner.setEnabled(false);
        eventsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentEventIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getBacklogTasksButton = (Button) findViewById(R.id.getBacklogTasks);
        getBacklogTasksButton.setEnabled(true);
        getBacklogTasksButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                statusLabel.setText("Getting tasks...");
                DataspinManager.Instance().GetAllTasks();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analytics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnUserRegistered(String s) {
        statusLabel.setText("User registered!");
        registerDeviceButton.setEnabled(true);
    }

    @Override
    public void OnDeviceRegistered() {
        statusLabel.setText("Device Registered!");
        startSessionButton.setEnabled(true);
    }

    @Override
    public void OnSessionStarted() {
        statusLabel.setText("Session started!");
        endSessionButton.setEnabled(true);
        getEventsButton.setEnabled(true);
        getItemsButton.setEnabled(true);
        buyButton.setEnabled(true);
        registerCustomEventButton.setEnabled(true);
    }

    @Override
    public void OnSessionEnded() {
        statusLabel.setText("Session ended!");
        endSessionButton.setEnabled(false);
        getEventsButton.setEnabled(false);
        getItemsButton.setEnabled(false);
        buyButton.setEnabled(false);
        registerCustomEventButton.setEnabled(false);
    }

    @Override
    public void OnItemPurchased(DataspinItem dataspinItem) {
        statusLabel.setText("Item purchased!");
    }

    @Override
    public void OnEventRegistered(DataspinEvent dataspinEvent) {
        statusLabel.setText("Event registered!");
    }

    @Override
    public void OnItemsListReceived(List<DataspinItem> dataspinItems) {
        statusLabel.setText("All OK! Items received!");
        itemsSpinner.setEnabled(true);

        int iterator = 0;
        String[] arraySpinner = new String[dataspinItems.size()];
        for(DataspinItem e : dataspinItems) {
            arraySpinner[iterator] = e.long_name;
            iterator++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        itemsSpinner.setAdapter(adapter);
    }

    @Override
    public void OnEventsListReceived(List<DataspinEvent> dataspinEvents) {
        statusLabel.setText("All OK! Events received!");
        itemsSpinner.setEnabled(true);

        int iterator = 0;
        String[] arraySpinner = new String[dataspinEvents.size()];
        for(DataspinEvent e : dataspinEvents) {
            arraySpinner[iterator] = e.name;
            iterator++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        eventsSpinner.setAdapter(adapter);
    }

    @Override
    public void OnError(DataspinError dataspinError) {
        //statusLabel.setText("Error! See logcat for more details.");
    }
}
