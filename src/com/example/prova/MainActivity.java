package com.example.prova;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements Dialog_EditName.DialogListener {

	private ArrayList<String> llista;
	private ArrayAdapter<String> adapter;
	private ListView musicList;

	private SearchView mSearchView;
	
	//persistencia
	private static final String SHARED_PREFERENCES_KEY = "ActivitySharedPreferences_data";
	private String name;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//persistencia llegir
		SharedPreferences sPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		name = sPreferences.getString("name", "");
		
		
		
		//per guardar, ho tinc que fer a settings.
		/*String contenidoTexto   = txtTexto.getText().toString();
		Editor editor   = sPreferences.edit();
		editor.putString("contenidoTexto", contenidoTexto);
		editor.commit();*/
		
		// carregaLlista();
		carregaLlistaSDMusica();
		
		

	}

	private void carregaLlistaSDMusica() {
		// classe que fa la busqueda per el dispositiu; envio el context perque
		// em fara falta.
		// LlistarMusica_SD musicaSD = new
		// LlistarMusica_SD(getApplicationContext());

		// provant amb asyncTask

		LlistarMusica_SD musicaSD = new LlistarMusica_SD(getApplicationContext());
		llista = musicaSD.doInBackground(llista);

		// afegeixo adapter i mostro ListView; Hauria de fer una llistView
		// personalitzada.
		musicList = (ListView) findViewById(R.id.MusicList);
		musicList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, llista);
		musicList.setAdapter(adapter);

	}

	private void carregaLlista() {

		// guardo la llista des del fitxer .xml a una arraylist
		String[] llistaa = getResources().getStringArray(R.array.musicList);
		llista = new ArrayList<String>();
		for (int i = 0; i < llistaa.length; i++) {
			llista.add(llistaa[i]);
		}
		musicList = (ListView) findViewById(R.id.MusicList);
		musicList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, llista);
		musicList.setAdapter(adapter);

		// llistener Click
		musicList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				// crida reproductor i reprodueix la canço.
			}
		});
		// llistener longClick
		musicList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {

				// Start the CAB using the ActionMode.Callback
				startActionMode(new CABActionMode(position));
				v.setSelected(true);
				return true;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		// buscador
		searcher(menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// faig el refresh a la llista
			carregaLlista();
			// adapter.notifyDataSetChanged();
			return true;
		case R.id.action_settings:
			
			//persistencia
			Intent intent = new Intent(this,SettingsActivity.class);
			intent.putExtra("name", name);
			startActivity(intent);
			
			
			return true;
		default:
			return false;
		}

	}

	// buscador.
	private void searcher(Menu menu) {
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				// filtra per adaptador

				// ------>Fer la prova Aquiiiii

				adapter.getFilter().filter("(.*)" + newText + "(.*)");
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// filtra per adaptador
				adapter.getFilter().filter(query);
				return true;
			}
		};
		mSearchView.setOnQueryTextListener(queryTextListener);
	}

	// Menu Contextual per LongClick
	class CABActionMode implements ActionMode.Callback {
		private int position;

		public CABActionMode(int positionn) {
			position = positionn;
		}

		// quan s'escull una opcio del menu contextual
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

			switch (item.getItemId()) {
			case R.id.action_edit:
				Dialog_EditName dialog = new Dialog_EditName();
				dialog.show(getFragmentManager(), "dialeg");

				mode.finish();
				return true;

			case R.id.action_delete:
				llista.remove(position);
				// hauria de eliminar des del arxiu? perque cada cop ho carrega
				// des de alla
				// notifiquem del canvi
				adapter.notifyDataSetChanged();
				adapter.notifyDataSetInvalidated();
				mode.finish();
				return true;

			default:
				return false;
			}
		}

		// quan es crei el menu contextual es mostrarant les opcions
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle("Options");
			mode.getMenuInflater().inflate(R.menu.main_contextual_actionmode, menu);
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {

		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		String name = "";

		Dialog dialogView = dialog.getDialog();
		final EditText nameChanged = (EditText) dialogView.findViewById(R.id.name);
		name = nameChanged.getText().toString();

		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList("llista", llista);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		llista = savedInstanceState.getStringArrayList("llista");
		musicList = (ListView) findViewById(R.id.MusicList);
		musicList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, llista);
		musicList.setAdapter(adapter);

		super.onRestoreInstanceState(savedInstanceState);
	}

}
