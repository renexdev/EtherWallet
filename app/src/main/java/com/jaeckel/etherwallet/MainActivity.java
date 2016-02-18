package com.jaeckel.etherwallet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.ethereum.go_ethereum.cmd.Geth;
import com.jaeckel.geth.EthereumJsonRpc;
import com.jaeckel.geth.json.EthAccountsResult;
import com.jaeckel.geth.json.EthSyncingResponse;
import com.novoda.notils.logger.simple.Log;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    private GethConnector gethConnector;
    private GethService gethService;
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            Log.d("onServiceConnected()");
            GethService.MyBinder b = (GethService.MyBinder) binder;
            gethService = b.getService();
//            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
//                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            gethService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChainDataDir();
//        gethConnector = new GethConnector();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Log.d("startService()");

//        startService(new Intent(this, GethService.class));
//        startJSONReporting();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gethService.ethAccounts(new EthereumJsonRpc.Callback<EthAccountsResult>() {
                    @Override
                    public void onResult(EthAccountsResult ethAccountsResult) {
                        Log.d("onResult(): " + ethAccountsResult);
                    }

                    @Override
                    public void onError(JSONRPC2Error error) {
                        Log.d("onError(): " + error);

                    }
                });

                gethService.ethSyncing(new EthereumJsonRpc.Callback<EthSyncingResponse>() {
                    @Override
                    public void onResult(EthSyncingResponse ethAccountsResult) {
                        Log.d("onResult(): " + ethAccountsResult);
                    }

                    @Override
                    public void onError(JSONRPC2Error error) {
                        Log.d("onError(): " + error);

                    }
                });
                //TODO: create account
//                Geth.doAccountNew(getChainDataDir(), "password");
//        Geth.run("--datadir=" + getChainDataDir() + " account list");
                //Geth.run("account list");

                //Log.d("GETHW", "netPeerCount()");
                //startJSONReporting();

                Snackbar.make(view, "SimpleCall...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @NonNull
    private String getChainDataDir() {
        Log.d("absolutePath: " + getFilesDir().getAbsolutePath());
        return getFilesDir().getAbsolutePath();
    }

//    private void startJSONReporting() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        System.out.println("GethConnector.netPeerCount()");
//                        gethConnector.netPeerCount(new EthereumJsonRpc.Callback<NetPeerCountResponse>() {
//                            @Override
//                            public void onResult(NetPeerCountResponse netPeerCountResponse) {
//                                Log.d("ETH", "netPeerCountResponse: " + netPeerCountResponse);
//                            }
//
//                            @Override
//                            public void onError(JSONRPC2Error error) {
//                                Log.d("ETH", "error: " + error);
//                            }
//                        });
////                        System.out.println("GethConnector.ethSyncing()");
////                        GethConnector.ethSyncing();
////                        System.out.println("GethConnector.ethAccounts()");
////                        GethConnector.ethAccounts();
//                        SystemClock.sleep(5000);
//                    } catch (IOException e) {
//                        Log.e("ETHW", "FAILURE: ", e);
//                        SystemClock.sleep(5000);
//                        //e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }

//    private void initGeth() {
//        Log.d("GETHW", "initGeth()...");
//
//        new Thread(new Runnable() {
//            public void run() {
//
//                Log.d("GETHW", "absolutePath: " + getChainDataDir()); //data/data/org.ethereum.droidwallet/files
//                int foo = Geth.run("--ipcdisable --rpc --rpccorsdomain=* --fast --datadir=" + getChainDataDir());
//                //Never reached
//            }
//        }).start();
//
//        SystemClock.sleep(1000);
//        System.out.println("Geth.doUnlockAccount()... right password");
//
//        Geth.doUnlockAccount(getChainDataDir(), "0x5d62714ddded8425414d9665cb63a3a1ebf9f860", "password", "1000ms");
//        System.out.println("Geth.doUnlockAccount()... wrong password");
//        Geth.doUnlockAccount(getChainDataDir(), "0x5d62714ddded8425414d9665cb63a3a1ebf9f860", "wrong", "1000ms");
//        System.out.println("...done.");
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_create_account) {
            Geth.doAccountNew(getChainDataDir(), "password");
            Snackbar.make(null, "Create Account...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume()");
        Intent intent = new Intent(this, GethService.class);
        bindService(intent, mConnection,
                    Context.BIND_AUTO_CREATE
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause()");

        unbindService(mConnection);
    }
}
