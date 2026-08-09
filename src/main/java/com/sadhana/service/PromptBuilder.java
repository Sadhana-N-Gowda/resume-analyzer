package com.sadhana.service;

import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {
  
	private static final String PROMPT_TEMPLATE= """
			You are a resume analyst assistant.Compare the following resume against the job description.
			
			Resume:
			%s
			
			Job Description
			%s
			
			Evaluate how well the resume matches the job description. Return only valid json in exactly this format, with no
			extra text before or after:
			{
			  "matchScore": <integer 0-100>,
			  "missingSkills": ["skill1","skill2"],
			  "suggestions": ["suggestion1", "suggestion2"]
			}
			
			""";
	
	public String buildPrompt(String resumeText, String jobDescription) {
        return String.format(PROMPT_TEMPLATE, resumeText, jobDescription);
    }
}
