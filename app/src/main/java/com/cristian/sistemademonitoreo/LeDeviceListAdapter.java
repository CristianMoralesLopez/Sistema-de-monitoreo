package com.cristian.sistemademonitoreo;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LeDeviceListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<BluetoothDevice> mDevices;

    public LeDeviceListAdapter(Activity activity) {

        this.activity = activity;
        mDevices = new ArrayList<BluetoothDevice>();
    }


    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View renglon = inflater.inflate(R.layout.devicebluetooth, null, false);
        TextView name = renglon.findViewById(R.id.name);
        name.setText(mDevices.get(position).getName());
        return renglon;
    }

    public void addDevice(BluetoothDevice device){
        mDevices.add(device);
        notifyDataSetChanged();
    }

    public boolean contains (BluetoothDevice device){

        return mDevices.contains(device);

    }


}
