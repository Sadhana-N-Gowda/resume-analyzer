package com.sadhana.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {
  
	 @Value("${gemini.api.key}")
	    private String apiKey;
	 
	 @Value("${gemini.api.url}")
	    private String apiUrl;
	 
	 private final RestTemplate restTemplate = new RestTemplate();
	 
	 public String callGemini(String prompt) {
	        String urlWithKey = apiUrl + "?key=" + apiKey;

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Gemini's expected request body structure
	        Map<String, Object> requestBody = Map.of(
	            "contents", List.of(
	                Map.of("parts", List.of(
	                    Map.of("text", prompt)
	                ))
	            )
	        );
	        
	        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

	        ResponseEntity<Map> response = restTemplate.postForEntity(urlWithKey, entity, Map.class);

	        return extractTextFromResponse(response.getBody());
	        
	        
}
	 @SuppressWarnings("unchecked")
	    private String extractTextFromResponse(Map<String, Object> responseBody) {
	        // Gemini response shape: candidates[0].content.parts[0].text
	        List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
	        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
	        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
	        return (String) parts.get(0).get("text");
	    }
}
