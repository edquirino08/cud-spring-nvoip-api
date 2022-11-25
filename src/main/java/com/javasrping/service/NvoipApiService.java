package com.javasrping.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.dto.SmsDTO;
import com.javasrping.dto.TorpedoDTO;
import com.javasrping.model.Sms;
import com.javasrping.model.Torpedo;
import com.javasrping.model.User;
import com.javasrping.repository.SmsRepository;
import com.javasrping.repository.TorpedoRepository;
import com.javasrping.repository.UserRepository;

@Service
public class NvoipApiService extends BaseService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SmsRepository smsRepository;

	@Autowired
	private TorpedoRepository torpedoRepository;

	public void sendSMS(SmsDTO smsDTO) throws Exception {

		if (this.checkNumber(smsDTO.getDestiny()))
			;

		Sms sms = new Sms(smsDTO.getMessage(), smsDTO.getSender(), smsDTO.getDestiny());

		try {
			String url = "https://api.nvoip.com.br/v2/sms?napikey=MFlIWlFMVWNuVTJNNThQbk1DaFRJMG5nRzVPNjlTQkw=";
			URI endereco = URI.create(url);

			var client = HttpClient.newHttpClient();
			var body = HttpRequest.BodyPublishers
					.ofString("{\r\n" + "    \"message\":\""+smsDTO.getMessage()+"\",\r\n" + "    \"numberPhone\":\" "
							+ sms.getDestiny() + "\"\r\n" + "}");
			HttpRequest request = HttpRequest.newBuilder(endereco).POST(body).header("Content-Type", "application/json")
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			this.smsRepository.save(sms);
			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void sendTorpedo(TorpedoDTO torpedoDTO) throws Exception {

		if (this.checkNumber(torpedoDTO.getDestiny()))
			;

		Torpedo torpedo = new Torpedo(torpedoDTO.getMessage(), torpedoDTO.getSender(), torpedoDTO.getDestiny());

		String accessToken = getAccessTokenNvoip(userRepository.findBynumbersip(torpedoDTO.getSender()));

		try {
			String url = "https://api.nvoip.com.br/v2/torpedo/voice";
			URI endereco = URI.create(url);

			var client = HttpClient.newHttpClient();
			var body = HttpRequest.BodyPublishers.ofString(
					"{\"caller\":\"" + torpedoDTO.getSender() + "\",\r\n       \"called\":\"" + torpedoDTO.getDestiny()
							+ "\",\r\n   \"audios\":[\r\n  {\r\n     \"audio\":" + torpedoDTO.getMessage() + ",\r\n"
							+ "   \"positionAudio\":1\r\n  }\r\n       ],\r\n       \"dtmfs\":[]\r\n  } ");
			HttpRequest request = HttpRequest.newBuilder(endereco).POST(body).header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + accessToken).build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			this.torpedoRepository.save(torpedo);

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
