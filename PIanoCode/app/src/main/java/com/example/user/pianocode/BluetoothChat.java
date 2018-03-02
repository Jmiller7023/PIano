package com.example.user.pianocode;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothChat extends AppCompatActivity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView mTitle;
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;


    public static final int A_TONE = 1;
    public static final int B_TONE = 2;
    public static final int C_TONE = 3;
    public static final int D_TONE = 4;
    public static final int E_TONE = 5;
    public static final int F_TONE = 6;
    public static final int G_TONE = 7;
    public static final int A_SHARP_TONE = 8;
    public static final int C_SHARP_TONE = 9;
    public static final int D_SHARP_TONE = 10;
    public static final int F_SHARP_TONE = 11;
    public static final int G_SHARP_TONE = 12;

    private SoundPool soundPool;
    private ArrayList<Integer> soundIDs = new ArrayList<>();
    private int A_tone; //1
    private int B_tone; //2
    private int C_tone; //3
    private int D_tone; //4
    private int E_tone; //5
    private int F_tone; //6
    private int G_tone; //7
    private int Asharp_tone; //8
    private int Csharp_tone; //9
    private int Dsharp_tone; //10
    private int Fsharp_tone; //11
    private int Gsharp_tone; //12

    Button A_button;
    Button B_button;
    Button C_button;
    Button D_button;
    Button E_button;
    Button F_button;
    Button G_button;
    Button Asharp_button;
    Button Csharp_button;
    Button Dsharp_button;
    Button Fsharp_button;
    Button Gsharp_button;

    public void initializeSoundPool(){
        soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 0);
        // Piano
        A_tone = soundPool.load(this, R.raw.a3, 1); //1
        B_tone = soundPool.load(this, R.raw.b3, 1); //2
        C_tone = soundPool.load(this, R.raw.c3, 1); //3
        D_tone = soundPool.load(this, R.raw.d3, 1); //4
        E_tone = soundPool.load(this, R.raw.e3, 1); //5
        F_tone = soundPool.load(this, R.raw.f3, 1); //6
        G_tone = soundPool.load(this, R.raw.g3, 1); //7
        Asharp_tone = soundPool.load(this, R.raw.bb3, 1); //8
        Csharp_tone = soundPool.load(this, R.raw.db3, 1); //9
        Dsharp_tone = soundPool.load(this, R.raw.eb3, 1); //10
        Fsharp_tone = soundPool.load(this, R.raw.gb3, 1); //11
        Gsharp_tone = soundPool.load(this, R.raw.ab3, 1); //12
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m_menuInflator = getMenuInflater();
        m_menuInflator.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        initializeSoundPool();
        // Set up the window layout
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_piano);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        Toolbar m_toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(m_toolbar);

        m_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

                //Stop all sounds
                for(int i=0; i < soundIDs.size(); i++){
                    soundPool.stop(soundIDs.get(i));
                }
                soundIDs.clear();
                soundPool.release();

                //make it scroll left
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });


        if(D) Log.e(TAG, "FINISHED SETUP WINDOW LAYOUT");

        // Set up the custom title
        //mTitle = (TextView) findViewById(R.id.title_left_text);
        //mTitle.setText(R.string.app_name);
        //mTitle = (TextView) findViewById(R.id.title_right_text);

        if(D) Log.e(TAG, "FINISHED SETUP CUSTOM TITLE");

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if(D) Log.e(TAG, "FINISHED CHECKING FOR BLUETOOTH ADAPTER");

        A_button = findViewById(R.id.wbtn6);
        A_button.setOnClickListener(new OnClickListener() {
            @ Override
            public void onClick(View view) {
                // play tone
                sendMessage("1");
                soundPool.play(A_tone, 1, 1, 0, 0, 1);
            }

        });

        B_button = findViewById(R.id.wbtn7);
        B_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("2");
                soundPool.play(B_tone, 1, 1, 0, 0, 1);
            }

        });

        C_button = findViewById(R.id.wbtn1);
        C_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("3");
                soundPool.play(C_tone, 1, 1, 0, 0, 1);
            }

        });

        D_button = findViewById(R.id.wbtn2);
        D_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("4");
                soundPool.play(D_tone, 1, 1, 0, 0, 1);
            }

        });

        E_button = findViewById(R.id.wbtn3);
        E_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("5");
                soundPool.play(E_tone, 1, 1, 0, 0, 1);
            }

        });

        F_button = findViewById(R.id.wbtn4);
        F_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("6");
                soundPool.play(F_tone, 1, 1, 0, 0, 1);
            }

        });

        G_button = findViewById(R.id.wbtn5);
        G_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("7");
                soundPool.play(G_tone, 1, 1, 0, 0, 1);
            }

        });

        Asharp_button = findViewById(R.id.bbtn5);
        Asharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("8");
                soundPool.play(Asharp_tone, 1, 1, 0, 0, 1);
            }

        });

        Csharp_button = findViewById(R.id.bbtn1);
        Csharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("9");
                soundPool.play(Csharp_tone, 1, 1, 0, 0, 1);
            }

        });

        Dsharp_button = findViewById(R.id.bbtn2);
        Dsharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("10");
                soundPool.play(Dsharp_tone, 1, 1, 0, 0, 1);
            }

        });

        Fsharp_button = findViewById(R.id.bbtn3);
        Fsharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("11");
                soundPool.play(Fsharp_tone, 1, 1, 0, 0, 1);
            }

        });

        Gsharp_button = findViewById(R.id.bbtn4);
        Gsharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("12");
                soundPool.play(Gsharp_tone, 1, 1, 0, 0, 1);
            }

        });

        if(D) Log.e(TAG, "FINISHED ONCREATE");
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
        if(D) Log.e(TAG, "++ ON START E ++");
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
        if(D) Log.e(TAG, "++ ON RESUME E ++");
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<>(this, R.layout.text);
        mConversationView = findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        mOutEditText = findViewById(R.id.edit_text_out);
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton = findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                TextView view = findViewById(R.id.edit_text_out);
                String message = view.getText().toString();
                sendMessage(message);
            }
        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
        if(D) Log.e(TAG, "setupChat() E");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        sendMessage(message);
                    }
                    if(D) Log.i(TAG, "END onEditorAction");
                    return true;
                }
            };

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            //mTitle.setText(R.string.title_connected_to);
                            //mTitle.append(mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            //mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            //mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    if(D) Log.e(TAG, "Sent: "+Integer.parseInt(writeMessage));
                    try {
                        synchronized(this){
                            wait(20);
                        }
                    }
                    catch(InterruptedException ex){
                    }
                    playSound(Integer.parseInt(writeMessage));
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    if(D) Log.e(TAG, "Received: "+Integer.parseInt(readMessage));

                    playSound(Integer.parseInt(readMessage));
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void playSound(int sound){
        switch(sound){
            case A_TONE:
                soundIDs.add(soundPool.play(A_tone, 1, 1, 0, 0, 1));
                break;
            case B_TONE:
                soundIDs.add(soundPool.play(B_tone, 1, 1, 0, 0, 1));
                break;
            case C_TONE:
                soundIDs.add(soundPool.play(C_tone, 1, 1, 0, 0, 1));
                break;
            case D_TONE:
                soundIDs.add(soundPool.play(D_tone, 1, 1, 0, 0, 1));
                break;
            case E_TONE:
                soundIDs.add(soundPool.play(E_tone, 1, 1, 0, 0, 1));
                break;
            case F_TONE:
                soundIDs.add(soundPool.play(F_tone, 1, 1, 0, 0, 1));
                break;
            case G_TONE:
                soundIDs.add(soundPool.play(G_tone, 1, 1, 0, 0, 1));
                break;
            case A_SHARP_TONE:
                soundIDs.add(soundPool.play(Asharp_tone, 1, 1, 0, 0, 1));
                break;
            case C_SHARP_TONE:
                soundIDs.add(soundPool.play(Csharp_tone, 1, 1, 0, 0, 1));
                break;
            case D_SHARP_TONE:
                soundIDs.add(soundPool.play(Dsharp_tone, 1, 1, 0, 0, 1));
                break;
            case F_SHARP_TONE:
                soundIDs.add(soundPool.play(Fsharp_tone, 1, 1, 0, 0, 1));
                break;
            case G_SHARP_TONE:
                soundIDs.add(soundPool.play(Gsharp_tone, 1, 1, 0, 0, 1));
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BluetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
            case R.id.discoverable:
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
        }
        return false;
    }

}