package com.sadhana.request;

import jakarta.validation.constraints.NotBlank;

public class AnalyzeRequest {
   
	@NotBlank(message = "Resume text is required")
    private String resumeText;

    @NotBlank(message = "Job description is required")
    private String jobDescription;

    // Getters and setters
    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
	
	
}
