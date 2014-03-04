package com.example.prova;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Dialog_EditName extends DialogFragment {

	private DialogListener listener;
	
	public Dialog_EditName() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		
		View v = inflater.inflate(R.layout.dialog_editname, null);
		final EditText e_nom = (EditText) v.findViewById(R.id.name);

		builder.setView(v)
			.setPositiveButton(R.string.Ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// aqui que canvii el nom del element escollit
						String nomCanviat = e_nom.getText().toString();
						Toast.makeText(getActivity().getBaseContext(),nomCanviat , Toast.LENGTH_LONG).show();
						
						//listener.onDialogPositiveClick(Dialog_EditName.this);
						
					}
				}).setNegativeButton(R.string.Cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					
					}
				});
		AlertDialog dialog = builder.create();
		dialog.setIcon(R.drawable.ic_action_edit);
		
		
		
		return dialog;
	}
	
	public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
}
