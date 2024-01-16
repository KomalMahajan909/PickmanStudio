/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itechvision.ecrobo.pickman.WeightConnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Tshipping.BoxCashRegisterActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;



// このクラスはBT60-端末間でソケット通信をおこなうためのBluetooth接続を管理します。
public class BtSocketService {
    // デバッグ
    private static final String TAG = "BtSocketService ";
    private static final boolean D = true;
    // ソケット作成時の SDP record 名
    private static final String NAME_SECURE = "BtSocketSecure";
    // ペアリングしていないデバイスは接続対象としない
    //private static final String NAME_INSECURE = "BtSocketInsecure";
    // SPP通信用 UUID
    private static final UUID MY_UUID_SECURE =
        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    	//UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    //private static final UUID MY_UUID_INSECURE =
    //    UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    // メンバフィールド
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mSecureAcceptThread;
    private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private CmdmodeThread mCmdmodeThread;
    private int mState;
    private ArrayList<String> mCmdList = new ArrayList<String>();

    // 接続状態を表す定数
    public static final int STATE_NONE = 0;       // 初期状態
    public static final int STATE_LISTEN = 1;     // 他からの接続待ち listening (server) mode
    public static final int STATE_CONNECTING = 2; // 接続
    public static final int STATE_CONNECTED = 3;  // 接続中
    public static final int STATE_ENTERCMD = 4;  // コマンドモード移行開始
    public static final int STATE_CMDMODE = 5;  // コマンドモード移行中
    public static final int STATE_EXITCMD = 6;  // コマンドモード移行終了

    Context mcontext ;

    /**
     * コンストラクタ、Bluetoothソケット通信セッションの準備
     * @param context  呼び出し元のアクティビティ
     * @param handler  呼び出し元のアクティビティにメッセージを送るためのハンドラ
     */
    public BtSocketService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
        mcontext= context;
    }

    /**
     * 接続状態を設定する
     * @param state  接続状態
     */
    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
     // 呼び出し元のアクティビティを更新するためにハンドラに接続状態を設定し、メッセージを送信する
        mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    /**
     * 接続状態を取得する */
    public synchronized int getState() {
        return mState;
    }

    /**
     * ソケットサービスの開始  */
    public synchronized void start() {
        //if (D) Log.d(TAG, "start");

        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        if (mCmdmodeThread != null) {mCmdmodeThread.cancel(); mCmdmodeThread = null;}
        
        setState(STATE_LISTEN);

        // 端末はデバイスを検出するだけなので AcceptThread() は必要なし
        // Start the thread to listen on a BluetoothServerSocket
        //if (mSecureAcceptThread == null) {
        //    mSecureAcceptThread = new AcceptThread(true);
        //    mSecureAcceptThread.start();
        //}
        //if (mInsecureAcceptThread == null) {
        //    mInsecureAcceptThread = new AcceptThread(false);
        //    mInsecureAcceptThread.start();
        //}
    }

    /**
     * ConnectThread 作成/開始
     * @param device  接続する BluetoothDevice
     * @param secure  The Socket Security type - Secure (true) , Insecure (false)
     */
    public synchronized void connect(BluetoothDevice device, boolean secure) {
        if (device == null) {
            Log.d(TAG, "connect() device == null");
        	return;
        }

    	if (D) Log.d(TAG, "connect to: " + device);

        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        if (mCmdmodeThread != null) {mCmdmodeThread.cancel(); mCmdmodeThread = null;}
        
        // ConnectThread 作成/開始
        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * ConnectedThread 作成/開始
     * @param socket  接続中の BluetoothSocket
     * @param device  接続中の BluetoothDevice
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device, final String socketType) {
        if (D) Log.d(TAG, "connected, Socket Type:" + socketType);

        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        if (mCmdmodeThread != null) {mCmdmodeThread.cancel(); mCmdmodeThread = null;}
        
        if (mSecureAcceptThread != null) {mSecureAcceptThread.cancel(); mSecureAcceptThread = null;}
        if (mInsecureAcceptThread != null) {mInsecureAcceptThread.cancel(); mInsecureAcceptThread = null;}

        // ConnectedThread 作成/開始
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();

        // 呼び出し元のアクティビティに接続デバイス名を送信
        Message msg = mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BoxCashRegisterActivity.DEVICE_NAME, device.getName());
        String address = device.getAddress();
        int length = address.length();
        String tempName = String.format("FireFly-%s%s", 
        		address.substring(length-5, length-3), address.substring(length-2, length));
        bundle.putString(BoxCashRegisterActivity.DEFAULT_NAME, tempName);
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }

    /**
     * CmdmodeThread 作成/開始
     * @param device  接続する BluetoothDevice
     */
    public synchronized void entercmd(BluetoothDevice device) {
        if (device == null) {
            Log.d(TAG, "entercmd() device == null");
        	return;
        }
        if (D) Log.d(TAG, "connect to: " + device);

        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        if (mCmdmodeThread != null) {mCmdmodeThread.cancel(); mCmdmodeThread = null;}
        
        if (mSecureAcceptThread != null) {mSecureAcceptThread.cancel(); mSecureAcceptThread = null;}
        if (mInsecureAcceptThread != null) {mInsecureAcceptThread.cancel(); mInsecureAcceptThread = null;}

        // CmdmodeThread 作成/開始
        mCmdmodeThread = new CmdmodeThread(device);
        mCmdmodeThread.start();
        setState(STATE_ENTERCMD);
    }

    /**
     * 接続状態を変更しCmdmodeThread を終了させる(onActivityResult()からの呼び出し)
     * @param data  ConfigActivity で作成した設定コマンド
     */
    public synchronized void exitcmd(Intent data) {
        // 何度も呼ばれる場合があることを念頭に入れてmCmdList は初期化しておく
        mCmdList.clear();
		if (data != null) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				mCmdList = extras.getStringArrayList(ConfigActivity.EXTRA_CMD_LIST);
			}
		}
        setState(STATE_EXITCMD);
    }
    
    /**
     * すべてのスレッドを中止する */
    public synchronized void stop() {
        if (D) Log.d(TAG, "stop");

        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mCmdmodeThread != null) {mCmdmodeThread.cancel(); mCmdmodeThread = null;}
        if (mSecureAcceptThread != null) {mSecureAcceptThread.cancel(); mSecureAcceptThread = null;}
        if (mInsecureAcceptThread != null) {mInsecureAcceptThread.cancel(); mInsecureAcceptThread = null;}
        setState(STATE_NONE);
    }

    /**
     * 同期されていない方法で ConnectedThread#write を実行
     * @param out 送信データ
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // 一時オブジェクトを作成
        ConnectedThread r;
        // BtSocketServiceのインスタンス(this)で同期をとって
        // 競合中ではないことが保障されている状況で
        // ConnectedThreadのインスタンス(mConnectedThread) のコピーを保持する
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // 非同期で ConnectedThread#write を実行
        r.write(out);
    }

    /**
     * ソケット接続失敗 エラー通知 */
    private void connectionFailed() {
        // 呼び出し元のアクティビティにメッセージを送信
        Message msg = mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_CONNECTION_LOST);
        Bundle bundle = new Bundle();
        bundle.putString(BoxCashRegisterActivity.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        // サービス再初期化
        BtSocketService.this.start();
    }

    /** ソケット切断 エラー通知 */
    private void connectionLost() {
        // 呼び出し元のアクティビティにメッセージを送信
        Message msg = mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(BoxCashRegisterActivity.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        // サービス再初期化
        BtSocketService.this.start();
    }

    /**
     * コマンドモード移行失敗 エラー通知 */
    private void connectionTimeout() {
        // 呼び出し元のアクティビティにメッセージを送信
        Message msg = mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_TIMEOUT);
        //Bundle bundle = new Bundle();
        //bundle.putString(BoxCashRegisterActivity.TOAST, getResources().getText(R.string.error_msg04).toString());
        //msg.setData(bundle);
        mHandler.sendMessage(msg);
        
        // サービス再初期化
        BtSocketService.this.start();
    }

    /**
     * これは接続の待ち受け中に動作するスレッドです。
     * サーバクライアントのように振舞います。
     * 接続が受け入れられるまでまたはキャンセルされるまで動作します。
     */
    private class AcceptThread extends Thread {
        // サーバソケット
        private final BluetoothServerSocket mmServerSocket;
        private String mSocketType;

        @SuppressWarnings("unused")
		public AcceptThread(boolean secure) {
            BluetoothServerSocket tmp = null;
            mSocketType = secure ? "Secure":"Insecure";

            // 待ち受け用サーバソケットを新規作成
            try {
                if (secure) {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE,
                        MY_UUID_SECURE);
                } else {
                    //tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(
                    //        NAME_INSECURE, MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            if (D) Log.d(TAG, "Socket Type: " + mSocketType +
                    "BEGIN mAcceptThread" + this);
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket = null;

            // 接続状態でない限り サーバソケットの待ち受ける
            while (mState != STATE_CONNECTED) {
                try {
                	// 接続が成功するか例外が発生するまでこの呼び出しはブロックされます。
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "Socket Type: " + mSocketType + "accept() failed", e);
                    break;
                }

                // 接続が受け入れられた場合
                if (socket != null) {
                    synchronized (BtSocketService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // 正常処理。
                            connected(socket, socket.getRemoteDevice(),
                                    mSocketType);
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // 準備ができていないか接続済み。新しいソケットを終了します。
                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread, socket Type: " + mSocketType);

        }

        public void cancel() {
            if (D) Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }


    /**
     * これはデバイスとの接続を試みる間に動作するスレッドです。
     * 接続が成功または失敗するまでストレートにスレッドが処理されます。
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // BluetoothDevice からBluetoothSocket を取得する
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                            MY_UUID_SECURE);
                } else {
                    //tmp = device.createInsecureRfcommSocketToServiceRecord(
                    //        MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
        	//if (D) Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // これを実行するとフリーズするのでコメントにする
            // Always cancel discovery because it will slow down a connection
            //mAdapter.cancelDiscovery();

            // BluetoothSocketへの接続を行う
            try {
            	// 接続が成功するか例外が発生するまでこの呼び出しはブロックされます。
                mmSocket.connect();
            } catch (IOException e) {
                // ソケットをクローズする
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            // 終了したので ConnectThreadをリセットする
            synchronized (BtSocketService.this) {
                mConnectThread = null;
            }

            // 正常処理。
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
                if(D) Log.d(TAG, "ConnectThread mmSocket.close() OK");
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread mmSocket.close() failed", e);
            }
        }
    }

    /**
     * これはデバイス接続中に動作するスレッドです。
     * すべての送受信を処理します。
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
        	if (D) Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // BluetoothSocket 入出力ストリームを取得
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
        	//if (D) Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // 接続中は入力ストリームを待ち受け続ける。
            while (true) {
                try {
                    // 入力ストリームを読み出し
                    bytes = mmInStream.read(buffer);

                    // 呼び出し元のアクティビティに受信データを付加したメッセージを送信
                    mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_READ, mState, bytes, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }

        /**
         * 出力ストリームへの書き込み
         * @param buffer  送信データ
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // 呼び出し元のアクティビティにデータ書き込み通知のためのメッセージを送信
                mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_WRITE, mState, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
                if(D) Log.d(TAG, "ConnectedThread mmSocket.close() OK");
            } catch (IOException e) {
                Log.e(TAG, "ConnectedThread mmSocket.close() failed", e);
            }
        }
    }

    private class CmdmodeThread extends Thread {
        /**
         * タイムアウトエラーであるかどうかを判断するための内部クラスです。 
         * 一定時間が経過しても接続状態(mmmState)が更新されない場合、CmdmodeThread を中断します  
         */
        private class TimeoutThread extends Thread {
            private int mmmTimeout;
            private int mmmState;

            public TimeoutThread(int timeout, int state) {
            	mmmTimeout = timeout;
            	mmmState = state;
            }

            public void run() {

                try {
                    Thread.sleep(mmmTimeout);
                } catch (InterruptedException e) {
                    return;
                }

                // mmmState が更新されない場合、CmdmodeThread を中断します
    			if (BtSocketService.this.getState() == mmmState){
                   	if (D) Log.i(TAG, "TimeoutThread:interrupt!");
               		// wait()中であれば interrupt()呼び出しで例外発生
               		mCmdmodeThread.interrupt();
               		// ソケットをクローズ->ブロック中のCmdmodeThread.run():read()が例外発生
               		mCmdmodeThread.cancel();	
                }
            }
        }
    	private final BluetoothSocket mmSocket;

        /**
         * コンストラクタ、BluetoothSocketの準備
         * @param device  接続する BluetoothDevice
         */
        public CmdmodeThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;

            // BluetoothDevice からBluetoothSocket を取得する
            try {
            	tmp = device.createRfcommSocketToServiceRecord(
                        MY_UUID_SECURE);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
        }

        /**
         * デバイスとの接続、コマンドモードへ移行、設定パラメータ読み出し、
         * ConfigActivity終了まで待機、設定パラメータ書き込み、ノーマルモードへ移行    
         */
        public void run() {
            //if (D) Log.i(TAG, "BEGIN mCmdmodeThread ");
            setName("CmdmodeThread");
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // これを実行するとフリーズするのでコメントにする
          // Always cancel discovery because it will slow down a connection
            //mAdapter.cancelDiscovery();
          // BluetoothSocketへの接続を行う
            try {
            	// 接続が成功するか例外が発生するまでこの呼び出しはブロックされます。
                mmSocket.connect();
            } catch (IOException e) {
                // ソケットをクローズ
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            TimeoutThread tempThread = new TimeoutThread(4000, STATE_ENTERCMD);
            tempThread.start();
            
            // BluetoothSocket 入出力ストリームを取得
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            try {
                byte[] send = new byte[]{'$','$','$'};//"$$$".getBytes();
                byte[] buffer = new byte[1024];
                int bytes;

                tmpOut.write(send);

            	Thread.sleep(40); //40ミリ秒Sleepする
                
                // 電源再点灯していないとここでブロックされたままになる。
                bytes = tmpIn.read(buffer);
                
                String cmdResponse = new String(buffer, 0, bytes);
                //if(D) Log.d(TAG, "cmdResponse1:" + cmdResponse);

            } catch (IOException e) {
                Log.e(TAG, "Exception during enter cmdmode", e);
            } catch (InterruptedException e){
            }

            try {
                byte[] send = new byte[]{'D','\r','\n'};//"D\r\n".getBytes();
                byte[] buffer = new byte[1024];
                int bytes;

                tmpOut.write(send);
                
            	Thread.sleep(400); //400ミリ秒Sleepする

                // 入力ストリームを読み出し
                bytes = tmpIn.read(buffer);

                //String cmdResponse = new String(buffer, 0, bytes);
                //if(D) Log.d(TAG, "cmdResponse2:" + cmdResponse);
                
                BtSocketService.this.setState(STATE_CMDMODE);
                
                // 呼び出し元のアクティビティに受信データを付加したメッセージを送信
                mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_SETTING, mState, bytes, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during send D command", e);
            } catch (InterruptedException e){
            }
            
            if (BtSocketService.this.getState() != STATE_CMDMODE){
                if(D) Log.d(TAG, "mCmdmodeThread run() コマンドモードに移行できませんでした。");
                connectionTimeout();
                mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_RECONNECT, mState, -1, Boolean.valueOf(false))
                .sendToTarget();            	
                return;
            }
            
            while (true) {
                try {
                	Thread.sleep(400); //400ミリ秒Sleepする
                } catch (InterruptedException e){
                    // サービス再初期化
                    BtSocketService.this.start();
                    return;
                }

               	if (BtSocketService.this.getState() != STATE_CMDMODE){
               		break;
               	}
            }

            try {
            	synchronized (BtSocketService.this) {
		            for ( int i = 0; i < mCmdList.size(); i++ ) {
		                String text = mCmdList.get(i);
		                byte[] send = text.getBytes();
		                byte[] buffer = new byte[1024];
		                int bytes;
		                
		                if(D) Log.d(TAG, "cmd:" + text);
		                tmpOut.write(send);
		            	Thread.sleep(40); //40ミリ秒Sleepする
	
		                // 入力ストリームを読み出し
		                bytes = tmpIn.read(buffer);
	
		                String cmdResponse = new String(buffer, 0, bytes);
		                //if(D) Log.d(TAG, "Response3:" + cmdResponse);
		            }
                }
            } catch (IOException e) {
                Log.e(TAG, "Exception during send command", e);
            } catch (InterruptedException e){
            }

            try {
                byte[] send = new byte[]{'-','-','-','\r','\n'};//"---\r\n".getBytes();
                byte[] buffer = new byte[1024];
                int bytes;

                tmpOut.write(send);
             	Thread.sleep(40); //40ミリ秒Sleepする
                
                 // 入力ストリームを読み出し
                 bytes = tmpIn.read(buffer);
                 String cmdResponse = new String(buffer, 0, bytes);

            } catch (IOException e) {
                Log.e(TAG, "Exception during exit command", e);
            	synchronized (BtSocketService.this) {
            		mCmdList.clear();
            	}
            } catch (InterruptedException e){
            	synchronized (BtSocketService.this) {
            		mCmdList.clear();
            	}
            }

            // デバイス電源をOFFにする前にいったんソケットを切断する
            BtSocketService.this.start();

        	synchronized (BtSocketService.this) {
	            if(mCmdList.size() != 0){
	                mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_RECONNECT, mState, -1, Boolean.valueOf(true))
	                .sendToTarget();            	
	            } else {
	                mHandler.obtainMessage(BoxCashRegisterActivity.MESSAGE_RECONNECT, mState, -1, Boolean.valueOf(false))
	                .sendToTarget();            	
	            }
        	}
        }

        public void cancel() {
            try {
                mmSocket.close();
                if(D) Log.d(TAG, "CmdmodeThread mmSocket.close() OK");
            } catch (IOException e) {
                Log.e(TAG, "CmdmodeThread mmSocket.close() failed", e);
            }
        }
    }

}
