package com.javasrping.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.model.User;
import com.javasrping.repository.UserRepository;

@Service
public class NvoipApiService extends BaseService {

	@Autowired
	private UserRepository userRepository;

	public void sendSMS(Long id) throws Exception {

		String telefone = userRepository.findById(id).get().getTelephone();

		try {
			String url = "https://api.nvoip.com.br/v2/sms?napikey=MFlIWlFMVWNuVTJNNThQbk1DaFRJMG5nRzVPNjlTQkw=";
			URI endereco = URI.create(url);

			var client = HttpClient.newHttpClient();
			var body = HttpRequest.BodyPublishers
					.ofString("{\r\n" + "    \"message\":\"mensagem teste teste devs\",\r\n" + "    \"numberPhone\":\" "
							+ telefone + "\"\r\n" + "}");
			HttpRequest request = HttpRequest.newBuilder(endereco).POST(body).header("Content-Type", "application/json")
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void sendTorpedo(User user) {

		String telefone = userRepository.findById(user.getId()).get().getTelephone();
		
		String accessToken = getAccessTokenNvoip(user);

		try {
			String url = "https://api.nvoip.com.br/v2/torpedo/voice";
			URI endereco = URI.create(url);

			var client = HttpClient.newHttpClient();
			var body = HttpRequest.BodyPublishers.ofString("{\"caller\":\""+user.getNumbersip()+"\",\r\n       \"called\":\""
					+ telefone
					+ "\",\r\n   \"audios\":[\r\n  {\r\n     \"audio\":\"Este é um teste de áudio, por favor ignore.\",\r\n"
					+ "   \"positionAudio\":1\r\n  }\r\n       ],\r\n       \"dtmfs\":[]\r\n  } ");
			HttpRequest request = HttpRequest.newBuilder(endereco).POST(body).header("Content-Type", "application/json")
					.header("Authorization",
							"Bearer "+accessToken)
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public String getAccessTokenNvoip(User user) {

		String numbersip = user.getNumbersip();
		String userToken = user.getUserToken();
		HttpResponse<String> response = null;

		try {
			String url = "https://api.nvoip.com.br/v2/oauth/token";
			URI endereco = URI.create(url);

			var client = HttpClient.newHttpClient();
			var body = HttpRequest.BodyPublishers
					.ofString("username=" + numbersip + "&password=" + userToken + "&grant_type=password");

			HttpRequest request = HttpRequest.newBuilder(endereco).POST(body)
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Authorization", "Basic TnZvaXBBcGlWMjpUblp2YVhCQmNHbFdNakl3TWpFPQ==").build();

			response = client.send(request, BodyHandlers.ofString());

			System.out.println(response.body().toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return getTokenByJson(response.body().toString());
	}

	private String getTokenByJson(String str) {

		JSONObject jsonObject = new JSONObject(str);

		String token = jsonObject.get("access_token").toString();

		return token;
	}

}
