package com.example.healthcontrollsystem.activity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.R.id;
import com.example.healthcontrollsystem.R.layout;
import com.example.healthcontrollsystem.utils.BlueToothUtils;
import com.example.healthcontrollsystem.utils.ToastUtils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private BluetoothAdapter bluetoothAdapter;
	//蓝牙设备
	private List<BluetoothDevice> devices;
	//设备名
	private List<String> devicesNames;
	//蓝牙接收者
	BluetoothReceiver receiver;
	private final String lockName = "BOLUTEK";
	//蓝牙列表
	private ListView lv_bluetooth_list;
	//蓝牙socket
	BluetoothSocket socket;
	//璇UID琛ㄧず涓插彛鏈嶅姟
	static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	//	private Bluetooth client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layout.activity_main);

		init();
	}

	//鍒濆鍖栨暟鎹�
	public void init(){
		lv_bluetooth_list = (ListView) findViewById(id.lv_bluetooth_list);
		//鑾峰彇钃濈墮閫傞厤鍣�
		bluetoothAdapter = BlueToothUtils.getInstance(this).openBluetooth();
		//鎵撳紑鎼滅储寮曟搸
		bluetoothAdapter.startDiscovery();
		devices = new ArrayList<BluetoothDevice>();
		devicesNames = new ArrayList<String>();
		searchBluetooth(bluetoothAdapter);
	}
	//鎼滅储钃濈墮
	public void searchBluetooth(BluetoothAdapter adapter){
		//		Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		//		enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);//3600涓鸿摑鐗欏彲瑙佹椂闂�
		//		startActivity(enable);
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		receiver = new BluetoothReceiver();
		registerReceiver(receiver, filter);

	}

	private class BluetoothReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (isLock(device)) {
					devicesNames.add(device.getName());
				}
				devices.add(device);
			}
			show();
		}

	}
	//连接
	private void connect(BluetoothDevice device){
		UUID uuid = UUID.fromString(SPP_UUID);

		ToastUtils.showToast("开始连接。。。", getApplicationContext());
		try {
			int state = device.getBondState();
			switch (state) {
			//鏈厤瀵�
			case BluetoothDevice.BOND_NONE:
				Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
				createBondMethod.invoke(device);
				break;
				//宸查厤瀵�
			case BluetoothDevice.BOND_BONDED:
				//连接蓝牙设备
				connectDevice(device);
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getData(socket);
					}
				}).run();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	BluetoothSocket btSocket;
	
	//连接设备
	private void connectDevice(BluetoothDevice btDevice){
		UUID uuid = UUID.fromString(SPP_UUID);
		try {
			socket = btDevice.createRfcommSocketToServiceRecord(uuid);
			socket.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取数据
	private String getData(BluetoothSocket socket){
		try {
			InputStream inputStream = socket.getInputStream();
			String str = inputStream2String(inputStream);
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	//inputstream转换成String'
	public String inputStream2String(InputStream in) throws IOException{ 
		StringBuffer out = new StringBuffer(); 
		byte[] b = new byte[4096]; 
		for(int n;(n = in.read(b))!=-1;){ 
			out.append(new String(b,0,n)); 
		} 
		return out.toString(); 
	}
	//瀛楃涓茶浆鎹㈡垚16杩涘埗
	private byte[] getHexBytes(String message) {
		int len = message.length() / 2;
		char[] chars = message.toCharArray();
		String[] hexStr = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i += 2, j++) {
			hexStr[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
		}
		return bytes;
	}

	//鏄惁閰嶅鎴愬姛
	private boolean isLock(BluetoothDevice device){
		boolean isLockName = (device.getName()).equals(lockName);
		boolean isSingleDevice = devices.indexOf(device.getName())==-1;
		return isLockName && isSingleDevice;
	}

	private void show(){
		lv_bluetooth_list.setAdapter(new MyAdapter(MainActivity.this));
		lv_bluetooth_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (bluetoothAdapter.isDiscovering()) {
					bluetoothAdapter.cancelDiscovery();
					//点击连接
					connect(devices.get(position));
				}
			}
		});
	}

	//接收数据
	private void receiver(){
		try {
			while (true) {
				InputStream inputStream = socket.getInputStream();
				StringBuffer out = new StringBuffer();
				byte[] buffer = new byte[1024];
				int bytes;
				//将inputStream转换成String
				while (true) {
					bytes = inputStream.read(buffer);
					for (int n; (n=inputStream.read(buffer))!=-1; ) {
						out.append(new String(buffer,0,n));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class MyAdapter extends BaseAdapter{

		private Context  context;
		private LayoutInflater inflater;
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return devices.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView==null) {
				convertView = inflater.inflate(layout.list_item, null);
			}
			TextView tv_item = (TextView) convertView.findViewById(id.tv_item);
			tv_item.setText(devices.get(position).getName());
			Toast.makeText(MainActivity.this, devices.size()+"", 1).show();
			return convertView;
		}
	}
}