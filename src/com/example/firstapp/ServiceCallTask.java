package com.example.firstapp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.os.AsyncTask;

public class ServiceCallTask extends AsyncTask<String, Integer, Map> {

	protected Map doInBackground(String... urls) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&photoset_id=72157622393146405&extras=url_t,url_m,url_s&api_key=9494c46ffd79439f6f83e7bca19a855d&format=json&jsoncallback=jsonp1368240878497&_=1368240878522");
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
			    InputStream instream = entity.getContent();
			    try {
			    	Reader reader = new InputStreamReader(instream, "UTF-8");
			    	Map data = new Gson().fromJson(reader, Map.class);
			    	return data;
			    } finally {
			        instream.close();
			    }
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return null;
	}
}
