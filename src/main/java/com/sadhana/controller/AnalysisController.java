package com.sadhana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sadhana.entity.ResumeAnalysisResult;
import com.sadhana.request.AnalyzeRequest;
import com.sadhana.service.AnalysisService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/analyze")
    public ResponseEntity<ResumeAnalysisResult> analyze(@Valid @RequestBody AnalyzeRequest request) {
    	ResumeAnalysisResult result = analysisService.analyze(
                request.getResumeText(),
                request.getJobDescription()
        );
        return ResponseEntity.ok(result);
    }
}

/*
{
"resumeText": "Backend developer with 2+ years building Java/Spring Boot microservices, REST APIs, and cloud-deployed systems on AWS. Hands-on experience with Kafka, JWT/Spring Security, and CI/CD pipelines (Jenkins, GitHub Actions). Developed Spring Boot REST APIs, implemented role-based authorization using JWT and Spring Security across microservices. Applied SOLID principles, TDD, JUnit, and Mockito to refactor legacy code. Skills: Java, Spring Boot, Spring Data JPA, Spring Security, Spring Cloud, MySQL, REST, Docker, Kafka, AWS.",
"jobDescription": "We are looking for a Backend Java Developer with 2-4 years of experience. Required skills: Java, Spring Boot, Microservices, REST APIs, MySQL or PostgreSQL, AWS, Docker, Kubernetes. Experience with Kafka and CI/CD pipelines is a plus. Knowledge of GraphQL and Redis caching preferred."
}
*/

/*{
"createdAt": "2026-08-09T07:16:05.2161705",
"id": 2,
"jobDescription": "We are looking for a Backend Java Developer with 2-4 years of experience. Required skills: Java, Spring Boot, Microservices, REST APIs, MySQL or PostgreSQL, AWS, Docker, Kubernetes. Experience with Kafka and CI/CD pipelines is a plus. Knowledge of GraphQL and Redis caching preferred.",
"matchSore": 80,
"missingSkills": "[\"Kubernetes\",\"GraphQL\",\"Redis\"]",
"resume": "Backend developer with 2+ years building Java/Spring Boot microservices, REST APIs, and cloud-deployed systems on AWS. Hands-on experience with Kafka, JWT/Spring Security, and CI/CD pipelines (Jenkins, GitHub Actions). Developed Spring Boot REST APIs, implemented role-based authorization using JWT and Spring Security across microservices. Applied SOLID principles, TDD, JUnit, and Mockito to refactor legacy code. Skills: Java, Spring Boot, Spring Data JPA, Spring Security, Spring Cloud, MySQL, REST, Docker, Kafka, AWS.",
"suggestions": "[\"Add experience with Kubernetes to strengthen your cloud deployment profile.\",\"Mention knowledge or project experience with Redis caching and GraphQL if you have any.\"]"
}*/