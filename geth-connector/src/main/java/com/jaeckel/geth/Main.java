package com.jaeckel.geth;

import com.jaeckel.geth.json.EthAccountsResult;
import com.jaeckel.geth.json.EthSyncingResponse;
import com.jaeckel.geth.json.NetPeerCountResponse;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("main()");

        GethConnector gethConnector = new GethConnector();
        gethConnector.netPeerCount(new EthereumJsonRpc.Callback<NetPeerCountResponse>() {
            @Override
            public void onResult(NetPeerCountResponse netPeerCountResponse) {
                System.out.println("netPeerCountResponse: " + netPeerCountResponse);
            }

            @Override
            public void onError(JSONRPC2Error error) {
                System.out.println("error: " + error);
            }
        });

        gethConnector.ethSyncing(new EthereumJsonRpc.Callback<EthSyncingResponse>() {
            @Override
            public void onResult(EthSyncingResponse ethSyncingResponse) {
                System.out.println("EthSyncingResult: " + ethSyncingResponse);
            }

            @Override
            public void onError(JSONRPC2Error error) {
                System.out.println("error: " + error);
            }
        });

        gethConnector.ethAccounts(new EthereumJsonRpc.Callback<EthAccountsResult>() {
            @Override
            public void onResult(EthAccountsResult ethAccountsResult) {
                System.out.println("ethAccountsResult: " + ethAccountsResult);
            }

            @Override
            public void onError(JSONRPC2Error error) {
                System.out.println("error: " + error);
            }
        });
    }
}