package com.example.user.pianocode;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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
    ListView mConversationView;
    private EditText mOutEditText;
    Button mSendButton;

    // Name of the connected device
    String mConnectedDeviceName = null;
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
    public static final int B_SHARP_TONE = 8;
    public static final int D_SHARP_TONE = 9;
    public static final int E_SHARP_TONE = 10;
    public static final int G_SHARP_TONE = 11;
    public static final int A_SHARP_TONE = 12;

    private SoundPool soundPool;
    private ArrayList<Integer> soundIDs = new ArrayList<>();

    //Octave 1 sounds
    private int sound_a; //1
    private int sound_b; //2
    private int sound_c; //3
    private int sound_d; //4
    private int sound_e; //5
    private int sound_f; //6
    private int sound_g; //7
    private int sound_b_b; //8
    private int sound_d_b; //9
    private int sound_e_b; //10
    private int sound_g_b; //11
    private int sound_a_b; //12

    //Octave 2 sounds
    private int sound_a_2; //1
    private int sound_b_2; //2
    private int sound_c_2; //3
    private int sound_d_2; //4
    private int sound_e_2; //5
    private int sound_f_2; //6
    private int sound_g_2; //7
    private int sound_b_b_2; //8
    private int sound_d_b_2; //9
    private int sound_e_b_2; //10
    private int sound_g_b_2; //11
    private int sound_a_b_2; //12

    //Octave 3 sounds
    private int sound_a_3; //1
    private int sound_b_3; //2
    private int sound_c_3; //3
    private int sound_d_3; //4
    private int sound_e_3; //5
    private int sound_f_3; //6
    private int sound_g_3; //7
    private int sound_b_b_3; //8
    private int sound_d_b_3; //9
    private int sound_e_b_3; //10
    private int sound_g_b_3; //11
    private int sound_a_b_3; //12

    //Octave 4 sounds
    private int sound_a_4; //1
    private int sound_b_4; //2
    private int sound_c_4; //3
    private int sound_d_4; //4
    private int sound_e_4; //5
    private int sound_f_4; //6
    private int sound_g_4; //7
    private int sound_b_b_4; //8
    private int sound_d_b_4; //9
    private int sound_e_b_4; //10
    private int sound_g_b_4; //11
    private int sound_a_b_4; //12

    //Octave 5 sounds
    private int sound_a_5; //1
    private int sound_b_5; //2
    private int sound_c_5; //3
    private int sound_d_5; //4
    private int sound_e_5; //5
    private int sound_f_5; //6
    private int sound_g_5; //7
    private int sound_b_b_5; //8
    private int sound_d_b_5; //9
    private int sound_e_b_5; //10
    private int sound_g_b_5; //11
    private int sound_a_b_5; //12

    //Octave 6 sounds
    private int sound_a_6; //1
    private int sound_b_6; //2
    private int sound_c_6; //3
    private int sound_d_6; //4
    private int sound_e_6; //5
    private int sound_f_6; //6
    private int sound_g_6; //7
    private int sound_b_b_6; //8
    private int sound_d_b_6; //9
    private int sound_e_b_6; //10
    private int sound_g_b_6; //11
    private int sound_a_b_6; //12

    //Octave 7 sounds
    private int sound_a_7; //1
    private int sound_b_7; //2
    private int sound_c_7; //3
    private int sound_d_7; //4
    private int sound_e_7; //5
    private int sound_f_7; //6
    private int sound_g_7; //7
    private int sound_b_b_7; //8
    private int sound_d_b_7; //9
    private int sound_e_b_7; //10
    private int sound_g_b_7; //11
    private int sound_a_b_7; //12

    //Octave info
    public String mOctaveNum;
    public static final String OCTAVE_1 = "1";
    public static final String OCTAVE_2 = "2";
    public static final String OCTAVE_3 = "3";
    public static final String OCTAVE_4 = "4";
    public static final String OCTAVE_5 = "5";
    public static final String OCTAVE_6 = "6";
    public static final String OCTAVE_7 = "7";

    //Buttons
    Button A_button;
    Button B_button;
    Button C_button;
    Button D_button;
    Button E_button;
    Button F_button;
    Button G_button;
    Button Asharp_button;
    Button Bsharp_button;
    Button Dsharp_button;
    Button Esharp_button;
    Button Gsharp_button;

    public void initializeSoundPool(){
        soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 0);
        // Piano
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mOctaveNum = prefs.getString("octave_num", "");


        //Loads in 1st octave.
        sound_c = soundPool.load(this, R.raw.c1, 1);
        sound_d_b = soundPool.load(this, R.raw.db1, 1);
        sound_d = soundPool.load(this, R.raw.d1, 1);
        sound_e_b = soundPool.load(this, R.raw.eb1, 1);
        sound_e = soundPool.load(this, R.raw.e1, 1);
        sound_f = soundPool.load(this, R.raw.f1, 1);
        sound_g_b = soundPool.load(this, R.raw.gb1, 1);
        sound_g = soundPool.load(this, R.raw.g1, 1);
        sound_a_b = soundPool.load(this, R.raw.ab1, 1);
        sound_a = soundPool.load(this, R.raw.a1, 1);
        sound_b_b = soundPool.load(this, R.raw.bb1, 1);
        sound_b = soundPool.load(this, R.raw.b1, 1);


        //Loads in 2nd octave.
        sound_c_2 = soundPool.load(this, R.raw.c2, 1);
        sound_d_b_2 = soundPool.load(this, R.raw.db2, 1);
        sound_d_2 = soundPool.load(this, R.raw.d2, 1);
        sound_e_b_2 = soundPool.load(this, R.raw.eb2, 1);
        sound_e_2 = soundPool.load(this, R.raw.e2, 1);
        sound_f_2 = soundPool.load(this, R.raw.f2, 1);
        sound_g_b_2 = soundPool.load(this, R.raw.gb2, 1);
        sound_g_2 = soundPool.load(this, R.raw.g2, 1);
        sound_a_b_2 = soundPool.load(this, R.raw.ab2, 1);
        sound_a_2 = soundPool.load(this, R.raw.a2, 1);
        sound_b_b_2 = soundPool.load(this, R.raw.bb2, 1);
        sound_b_2 = soundPool.load(this, R.raw.b2, 1);


        //Loads in 3rd octave.
        sound_c_3 = soundPool.load(this, R.raw.c3, 1);
        sound_d_b_3 = soundPool.load(this, R.raw.db3, 1);
        sound_d_3 = soundPool.load(this, R.raw.d3, 1);
        sound_e_b_3 = soundPool.load(this, R.raw.eb3, 1);
        sound_e_3 = soundPool.load(this, R.raw.e3, 1);
        sound_f_3 = soundPool.load(this, R.raw.f3, 1);
        sound_g_b_3 = soundPool.load(this, R.raw.gb3, 1);
        sound_g_3 = soundPool.load(this, R.raw.g3, 1);
        sound_a_b_3 = soundPool.load(this, R.raw.ab3, 1);
        sound_a_3 = soundPool.load(this, R.raw.a3, 1);
        sound_b_b_3 = soundPool.load(this, R.raw.bb3, 1);
        sound_b_3 = soundPool.load(this, R.raw.b3, 1);

        //Loads in 4th octave.
        sound_c_4 = soundPool.load(this, R.raw.c4, 1);
        sound_d_b_4 = soundPool.load(this, R.raw.db4, 1);
        sound_d_4 = soundPool.load(this, R.raw.d4, 1);
        sound_e_b_4 = soundPool.load(this, R.raw.eb4, 1);
        sound_e_4 = soundPool.load(this, R.raw.e4, 1);
        sound_f_4 = soundPool.load(this, R.raw.f4, 1);
        sound_g_b_4 = soundPool.load(this, R.raw.gb4, 1);
        sound_g_4 = soundPool.load(this, R.raw.g4, 1);
        sound_a_b_4 = soundPool.load(this, R.raw.ab4, 1);
        sound_a_4 = soundPool.load(this, R.raw.a4, 1);
        sound_b_b_4 = soundPool.load(this, R.raw.bb4, 1);
        sound_b_4 = soundPool.load(this, R.raw.b4, 1);

        //Loads in 5th octave.
        sound_c_5 = soundPool.load(this, R.raw.c5, 1);
        sound_d_b_5 = soundPool.load(this, R.raw.db5, 1);
        sound_d_5 = soundPool.load(this, R.raw.d5, 1);
        sound_e_b_5 = soundPool.load(this, R.raw.eb5, 1);
        sound_e_5 = soundPool.load(this, R.raw.e5, 1);
        sound_f_5 = soundPool.load(this, R.raw.f5, 1);
        sound_g_b_5 = soundPool.load(this, R.raw.gb5, 1);
        sound_g_5 = soundPool.load(this, R.raw.g5, 1);
        sound_a_b_5 = soundPool.load(this, R.raw.ab5, 1);
        sound_a_5 = soundPool.load(this, R.raw.a5, 1);
        sound_b_b_5 = soundPool.load(this, R.raw.bb5, 1);
        sound_b_5 = soundPool.load(this, R.raw.b5, 1);

        //Loads in 6th octave.
        sound_c_6 = soundPool.load(this, R.raw.c6, 1);
        sound_d_b_6 = soundPool.load(this, R.raw.db6, 1);
        sound_d_6 = soundPool.load(this, R.raw.d6, 1);
        sound_e_b_6 = soundPool.load(this, R.raw.eb6, 1);
        sound_e_6 = soundPool.load(this, R.raw.e6, 1);
        sound_f_6 = soundPool.load(this, R.raw.f6, 1);
        sound_g_b_6 = soundPool.load(this, R.raw.gb6, 1);
        sound_g_6 = soundPool.load(this, R.raw.g6, 1);
        sound_a_b_6 = soundPool.load(this, R.raw.ab6, 1);
        sound_a_6 = soundPool.load(this, R.raw.a6, 1);
        sound_b_b_6 = soundPool.load(this, R.raw.bb6, 1);
        sound_b_6 = soundPool.load(this, R.raw.b6, 1);

        //Loads in 7th octave.
        sound_c_7 = soundPool.load(this, R.raw.c7, 1);
        sound_d_b_7 = soundPool.load(this, R.raw.db7, 1);
        sound_d_7 = soundPool.load(this, R.raw.d7, 1);
        sound_e_b_7 = soundPool.load(this, R.raw.eb7, 1);
        sound_e_7 = soundPool.load(this, R.raw.e7, 1);
        sound_f_7 = soundPool.load(this, R.raw.f7, 1);
        sound_g_b_7 = soundPool.load(this, R.raw.gb7, 1);
        sound_g_7 = soundPool.load(this, R.raw.g7, 1);
        sound_a_b_7 = soundPool.load(this, R.raw.ab7, 1);
        sound_a_7 = soundPool.load(this, R.raw.a7, 1);
        sound_b_b_7 = soundPool.load(this, R.raw.bb7, 1);
        sound_b_7 = soundPool.load(this, R.raw.b7, 1);
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
        setContentView(R.layout.activity_piano);

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
                sendMessage("01" + mOctaveNum);
                playSound(1, mOctaveNum);
            }

        });

        B_button = findViewById(R.id.wbtn7);
        B_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("02" + mOctaveNum);
                playSound(2, mOctaveNum);
            }

        });

        C_button = findViewById(R.id.wbtn1);
        C_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("03" + mOctaveNum);
                playSound(3, mOctaveNum);
            }

        });

        D_button = findViewById(R.id.wbtn2);
        D_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("04" + mOctaveNum);
                playSound(4, mOctaveNum);
            }

        });

        E_button = findViewById(R.id.wbtn3);
        E_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("05" + mOctaveNum);
                playSound(5, mOctaveNum);
            }

        });

        F_button = findViewById(R.id.wbtn4);
        F_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("06" + mOctaveNum);
                playSound(6, mOctaveNum);
            }

        });

        G_button = findViewById(R.id.wbtn5);
        G_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("07" + mOctaveNum);
                playSound(7, mOctaveNum);
            }

        });

        Asharp_button = findViewById(R.id.bbtn5);
        Asharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("08" + mOctaveNum);
                playSound(8, mOctaveNum);
            }

        });

        Bsharp_button = findViewById(R.id.bbtn1);
        Bsharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("09" + mOctaveNum);
                playSound(9, mOctaveNum);
            }

        });

        Dsharp_button = findViewById(R.id.bbtn2);
        Dsharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("10" + mOctaveNum);
                playSound(10, mOctaveNum);
            }

        });

        Esharp_button = findViewById(R.id.bbtn3);
        Esharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("11" + mOctaveNum);
                playSound(11, mOctaveNum);
            }

        });

        Gsharp_button = findViewById(R.id.bbtn4);
        Gsharp_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // play tone
                sendMessage("12" + mOctaveNum);
                playSound(12, mOctaveNum);
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
    @SuppressLint("HandlerLeak")
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
                        //Empty
                    }
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    if(D) Log.e(TAG, "Received: "+Integer.parseInt(readMessage));

                    playSound(Integer.parseInt(readMessage.substring(0,2)), readMessage.substring(2,3));
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

    public void playSound(int sound, String octave){
        switch(octave) {
            case OCTAVE_1:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b, 1, 1, 0, 0, 1));
                        break;
                }
                break;
            case OCTAVE_2:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a_2, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b_2, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c_2, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d_2, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e_2, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f_2, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g_2, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b_2, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b_2, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b_2, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b_2, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b_2, 1, 1, 0, 0, 1));
                        break;
                }
                break;
            case OCTAVE_3:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a_3, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b_3, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c_3, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d_3, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e_3, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f_3, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g_3, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b_3, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b_3, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b_3, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b_3, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b_3, 1, 1, 0, 0, 1));
                        break;
                }
                break;
            case OCTAVE_4:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a_4, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b_4, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c_4, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d_4, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e_4, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f_4, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g_4, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b_4, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b_4, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b_4, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b_4, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b_4, 1, 1, 0, 0, 1));
                        break;
                }
                break;
            case OCTAVE_5:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a_5, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b_5, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c_5, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d_5, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e_5, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f_5, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g_5, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b_5, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b_5, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b_5, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b_5, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b_5, 1, 1, 0, 0, 1));
                        break;
                }
                break;
            case OCTAVE_6:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a_6, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b_6, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c_6, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d_6, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e_6, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f_6, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g_6, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b_6, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b_6, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b_6, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b_6, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b_6, 1, 1, 0, 0, 1));
                        break;
                }
                break;
            case OCTAVE_7:
                switch (sound) {
                    case A_TONE:
                        soundIDs.add(soundPool.play(sound_a_7, 1, 1, 0, 0, 1));
                        break;
                    case B_TONE:
                        soundIDs.add(soundPool.play(sound_b_7, 1, 1, 0, 0, 1));
                        break;
                    case C_TONE:
                        soundIDs.add(soundPool.play(sound_c_7, 1, 1, 0, 0, 1));
                        break;
                    case D_TONE:
                        soundIDs.add(soundPool.play(sound_d_7, 1, 1, 0, 0, 1));
                        break;
                    case E_TONE:
                        soundIDs.add(soundPool.play(sound_e_7, 1, 1, 0, 0, 1));
                        break;
                    case F_TONE:
                        soundIDs.add(soundPool.play(sound_f_7, 1, 1, 0, 0, 1));
                        break;
                    case G_TONE:
                        soundIDs.add(soundPool.play(sound_g_7, 1, 1, 0, 0, 1));
                        break;
                    case B_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_b_b_7, 1, 1, 0, 0, 1));
                        break;
                    case D_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_d_b_7, 1, 1, 0, 0, 1));
                        break;
                    case E_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_e_b_7, 1, 1, 0, 0, 1));
                        break;
                    case G_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_g_b_7, 1, 1, 0, 0, 1));
                        break;
                    case A_SHARP_TONE:
                        soundIDs.add(soundPool.play(sound_a_b_7, 1, 1, 0, 0, 1));
                        break;
                }
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
                    // User did not enable Bluetooth or an error occurred
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