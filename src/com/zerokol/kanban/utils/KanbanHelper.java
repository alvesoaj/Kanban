package com.zerokol.kanban.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
// import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
// import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.client.methods.HttpPut;
// import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class KanbanHelper {
	// public static final String DOMAIN =
	// "http://tvinterativa.interaje.com.br";
	public static final String DOMAIN = "http://192.168.1.123:3000";

	public static final Integer USER_STATUS_NEW = 1;
	public static final Integer USER_STATUS_DUMMY = 2;
	public static final Integer USER_STATUS_BLOCKED = 3;
	public static final Integer USER_STATUS_OFFLINE = 4; // Only on Devices

	public static final String STATION_CODE = "CSOGS36307";

	public static String getFormatedData(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
				Locale.getDefault());
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static Date convertStringDateToDate(String date) {
		try {
			if (date != null && date.compareTo("null") != 0
					&& date.compareTo("") != 0) {
				if (date.length() > 11) {
					return (Date) new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
							.parse(date);
				} else {
					return (Date) new SimpleDateFormat("yyyy-MM-dd",
							Locale.getDefault()).parse(date);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getTimeCurrent() {
		Calendar cal = Calendar.getInstance();

		Date timeNow = new Date();

		cal.setTime(timeNow);

		// Um numero negativo vai decrementar a data
		cal.add(Calendar.MILLISECOND,
				TimeZone.getDefault().getOffset(timeNow.getTime()) * -1);

		return cal.getTime();
	}

	public static String getHumanData(Date date) {
		if (date != null) {
			return new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
					.format(date);
		} else {
			return "NULL";
		}
	}

	public static String makeGETRequest(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		// Fazendo a requisicao ao servidor
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(get, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * public static String makePUTRequest(String url, MultipartEntityBuilder
	 * meb) { // Atualizar na WEB HttpClient httpclient = new
	 * DefaultHttpClient(); HttpPut put = new HttpPut(url);
	 * 
	 * try { put.setEntity(meb.build());
	 * 
	 * // setup the request headers // post.setHeader("Accept",
	 * "application/json"); // post.setHeader("Content-Type",
	 * "application/json"); // post.setHeader("Content-Type", "image/jpeg");
	 * 
	 * ResponseHandler<String> responseHandler = new BasicResponseHandler();
	 * return httpclient.execute(put, responseHandler); } catch
	 * (HttpResponseException e) { e.printStackTrace(); return null; } catch
	 * (IOException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static String makePOSTRequest(String url, MultipartEntityBuilder
	 * meb) { HttpClient httpclient = new DefaultHttpClient(); HttpPost post =
	 * new HttpPost(url);
	 * 
	 * try { post.setEntity(meb.build());
	 * 
	 * // setup the request headers // post.setHeader("Accept",
	 * "application/json"); // post.setHeader("Content-Type",
	 * "application/json"); // post.setHeader("Content-Type", "image/jpeg");
	 * 
	 * ResponseHandler<String> responseHandler = new BasicResponseHandler();
	 * return httpclient.execute(post, responseHandler); } catch
	 * (HttpResponseException e) { e.printStackTrace(); return null; } catch
	 * (IOException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static String makeDELETERequest(String url) { HttpClient
	 * httpclient = new DefaultHttpClient(); HttpDelete delete = new
	 * HttpDelete(url);
	 * 
	 * // Fazendo a requisicao ao servidor try { ResponseHandler<String>
	 * responseHandler = new BasicResponseHandler(); return
	 * httpclient.execute(delete, responseHandler); } catch
	 * (ClientProtocolException e) { e.printStackTrace(); return null; } catch
	 * (IllegalStateException e) { e.printStackTrace(); return null; } catch
	 * (IOException e) { e.printStackTrace(); return null; } }
	 */
	public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
		if (bitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// TODO see if it raise problems
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			return baos.toByteArray();
		} else {
			return null;
		}
	}

	public static Bitmap convertByteArrayToBitmap(byte[] byteArray) {
		if (byteArray != null) {
			ByteArrayInputStream imageStream = new ByteArrayInputStream(
					byteArray);
			return BitmapFactory.decodeStream(imageStream);
		} else {
			return null;
		}
	}

	public static Bitmap getBitmapFromWeb(String imageURL) {
		Bitmap bitmap = null;
		if (imageURL != null && imageURL.compareTo("null") != 0) {
			try {
				InputStream in = new URL(DOMAIN + imageURL).openStream();

				bitmap = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
}
