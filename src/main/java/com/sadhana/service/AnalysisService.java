package com.sadhana.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sadhana.entity.ResumeAnalysisResult;
import com.sadhana.repository.ResumeAnalysisResultRepository;
import com.sadhana.response.AnalysisResponse;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AnalysisService {

    @Autowired
    private PromptBuilder promptBuilder;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ResumeAnalysisResultRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AnalysisResponse analyze(String resumeText, String jobDescription) {
        // Step 1: build prompt
        String prompt = promptBuilder.buildPrompt(resumeText, jobDescription);

        // Step 2: call Gemini
        String rawResponse = geminiService.callGemini(prompt);

        // Step 3: clean + parse JSON
        String cleanJson = cleanResponse(rawResponse);
        ResumeAnalysisResult result = parseToEntity(cleanJson, resumeText, jobDescription);
        ResumeAnalysisResult saved = repository.save(result);

        // Step 4: save to DB
          return toResponse(saved);
    }

    private String cleanResponse(String rawResponse) {
        // Gemini sometimes wraps JSON in ```json ... ``` markdown fences — strip those if present
        return rawResponse
                .replaceAll("(?s)```json", "")
                .replaceAll("(?s)```", "")
                .trim();
    }

    private ResumeAnalysisResult parseToEntity(String json, String resumeText, String jobDescription) {
        try {
            JsonNode node = objectMapper.readTree(json);

            ResumeAnalysisResult result = new ResumeAnalysisResult();
            result.setResume(resumeText);
            result.setJobDescription(jobDescription);
            result.setMatchScore(node.get("matchScore").asInt());
            result.setMissingSkills(node.get("missingSkills").toString()); // stored as JSON array string
            result.setSuggestions(node.get("suggestions").toString());    // stored as JSON array string

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response: " + json, e);
        }
    }
    
    private AnalysisResponse toResponse(ResumeAnalysisResult result) {
        try {
            AnalysisResponse response = new AnalysisResponse();
            response.setId(result.getId());
            response.setMatchScore(result.getMatchSore());
            response.setMissingSkills(
                objectMapper.readValue(result.getMissingSkills(), new TypeReference<List<String>>() {})
            );
            response.setSuggestions(
                objectMapper.readValue(result.getSuggestions(), new TypeReference<List<String>>() {})
            );
            response.setCreatedAt(result.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to build response", e);
        }
}
}
