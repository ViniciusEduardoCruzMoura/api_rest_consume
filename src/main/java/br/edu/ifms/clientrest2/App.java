package br.edu.ifms.clientrest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class App {

	public static void doPostNoticia() {
		try {
			URL url = new URL("http://localhost:8080/clientrest/api/client/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");

			String input = "<client>" +
								"<firstName>Marcus Tulius</firstName>" +
                                "<lastName>Cicero</lastName>" + 
                                "<email>ResPvblicaRomana@spqr.latin</email>" +
                                "<phone>992814545</phone>" +
							"</client>";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Falhou, HTTP error code -> " + conn.getResponseCode());
			}

			String recurso = conn.getHeaderField("Location");

			System.out.println("-> " + recurso);

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void doGetNoticias() {
		try {

			URL url = new URL("http://localhost:8080/clientrest/api/client/clients");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		doPostNoticia();
		doGetNoticias();
	}
}
