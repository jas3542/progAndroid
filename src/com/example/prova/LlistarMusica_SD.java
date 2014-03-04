package com.example.prova;

import java.util.ArrayList;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.MediaStore;

public class LlistarMusica_SD extends AsyncTask<ArrayList<String>, Void, ArrayList<String>>{

	private Context context;
	public LlistarMusica_SD(Context contextt){
		context = contextt;
	}
	
	@Override
	protected ArrayList<String> doInBackground(ArrayList<String>...params) {
		int count = params.length;
		
		
		for (int i = 0 ;i< count; i++){
			// Some audio may be explicitly marked as not being music
			String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
			String[] selectionArgs = null;
			String sortOrder = "ARTIST";
			/*
			 * String[] projection = { MediaStore.Audio.Media._ID,
			 * MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE,
			 * MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
			 * MediaStore.Audio.Media.DURATION };
			 */

			//Hauria de agafar la imatge amb MediaStore.Audio.Albums.ALBUM_ART enlloc de MediaStore.Audio.Media.ALBUM?
			String[] projection = {MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Albums.ALBUM };

			Cursor cursor =    context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
									projection, selection, selectionArgs, sortOrder+ " DESC ");

			
			params[i] = new ArrayList<String>();
			while (cursor.moveToNext()) {
				//byte[] blob = cursor.getBlob(0);
				//Bitmap bm = BitmapFactory.decodeByteArray(blob, 0 ,blob.length);
				//Album album = new Album(bm, cursor.getString(1));
				params[i].add(cursor.getString(0)+ " || "+cursor.getString(1));
			}
			return params[i];
			
		}
		return null;
	}
	
	//no el faig servir,perque estic provant threads
	public ArrayList<String> obtenirAlbums(ArrayList<String> llista) {
		// Some audio may be explicitly marked as not being music
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		String[] selectionArgs = null;
		String sortOrder = "ARTIST";
		/*
		 * String[] projection = { MediaStore.Audio.Media._ID,
		 * MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE,
		 * MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
		 * MediaStore.Audio.Media.DURATION };
		 */

		//Hauria de agafar la imatge amb MediaStore.Audio.Albums.ALBUM_ART
		String[] projection = { MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST };

		Cursor cursor =   context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
								projection, selection, selectionArgs, sortOrder+ " DESC ");

		llista = new ArrayList<String>();
		while (cursor.moveToNext()) {
			llista.add(cursor.getString(0) + "||" + cursor.getString(1));
		}

		return llista;
	}

	
}
