package com.sadhana.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name= "analysis_result")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeAnalysisResult {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 @Lob
	 @Column(name = "resume_text", columnDefinition = "TEXT")
	private String resume;
	 
	 @Lob
	 @Column(name = "job_description", columnDefinition = "TEXT")
	private String jobDescription;
	 
	 @Column(name = "match_score")
	  private Integer matchSore;
	 
	 @Lob
	  @Column(name = "missing_skills", columnDefinition = "TEXT")
	private String missingSkills;
	 
	 @Lob
	    @Column(name = "suggestions", columnDefinition = "TEXT")
	private String suggestions;
	 
	 @Column(name = "created_at")
	private LocalDateTime createdAt;
	
	 @PrePersist
	    protected void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }

	 public void setResume(String resumeText) {
      this.resume= resumeText;		
	 }

	 public void setJobDescription(String jobDescription2) {
         this.jobDescription= jobDescription2;		
	 }

	 public void setMissingSkills(String string) {
       this.missingSkills= string;		
	 }

	 public void setMatchScore(int asInt) {
        this.matchSore= asInt;		
	 }

	 public void setSuggestions(String string) {
         this. suggestions= string;		
	 }

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public Integer getMatchSore() {
		 return matchSore;
	 }


	 public LocalDateTime getCreatedAt() {
		 return createdAt;
	 }

	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

	 public String getResume() {
		 return resume;
	 }

	 public String getJobDescription() {
		 return jobDescription;
	 }

	 public String getMissingSkills() {
		 return missingSkills;
	 }

	 public String getSuggestions() {
		 return suggestions;
	 }

	 

}
