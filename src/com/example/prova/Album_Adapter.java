package com.example.prova;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class Album_Adapter extends BaseAdapter{

	private Activity activity;
	private ArrayList<String> albums;
	
	public Album_Adapter(Activity activityy,ArrayList<String> llista){
		activity = activityy;
		albums = llista;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return albums.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return albums.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		//return albums.get(position).getId();
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.custom_listview, null);
		}
		
		//Album album = albums.get(position);
		//possem foto
		ImageView foto = (ImageView) v.findViewById(R.id.imageAlbum);
		//foto.setImageBitmap(album.getDibuix());
		//possem nom album
		TextView nom = (TextView) v.findViewById(R.id.textAlbum);
		//nom.setText(album.getNom());
		return v;
	}
	


	
}
