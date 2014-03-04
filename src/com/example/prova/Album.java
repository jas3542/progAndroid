package com.example.prova;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Album {

	private Bitmap cover;
	private String nom;
	private long id;
	
	public Album(long idd, Bitmap coverr, String nomm){
		id = idd;
		cover = coverr;
		nom = nomm;
	}

	public Album (Bitmap coverr,String nomm){
		cover = coverr;
		nom = nomm;
	}

	public Bitmap getDibuix() {
		return cover;
	}

	public void setDibuix(Bitmap dibuix) {
		this.cover = dibuix;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
