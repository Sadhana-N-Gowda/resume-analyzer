# AI Resume Analyzer

A Spring Boot REST API that analyzes how well a resume matches a job description using Google's Gemini API. Returns a match score, missing skills, and actionable suggestions — then persists each analysis to MySQL.

## Features
- Compare resume text against a job description
- AI-generated match score (0-100)
- Identifies missing skills relevant to the role
- Suggests concrete resume improvements
- Persists analysis history to MySQL

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.x
- **Persistence**: Spring Data JPA, MySQL
- **AI Integration**: Google Gemini API
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Maven

## Architecture
Client → Controller → Service Layer → Gemini API
│
▼
Repository (JPA)
│
▼
MySQL

**Flow:**
1. Client sends resume + job description to `POST /api/analyze`
2. `PromptBuilder` constructs a structured prompt
3. `GeminiService` calls the Gemini API and returns raw text
4. `AnalysisService` parses the JSON response and persists it
5. Structured result (score, missing skills, suggestions) returned to client

## API Example

**Request:**
```bash
{
  "resumeText": "Java developer with Spring Boot experience, REST APIs, MySQL, AWS...",
  "jobDescription": "Looking for a backend engineer with Java, Spring Boot, AWS, Docker..."
}
```

**Response:**
```json
{
  "id": 1,
  "matchScore": 78,
  "missingSkills": ["Kubernetes", "GraphQL"],
  "suggestions": ["Add a project demonstrating cloud deployment", "Highlight Docker experience"],
  "createdAt": "2026-08-09T10:30:00"
}
```

## Setup

1. Clone the repo
```bash
   git clone https://github.com/yourusername/resume-analyzer.git
```
2. Create a MySQL database:
```sql
   CREATE DATABASE resume_analyzer;
```
3. Set your Gemini API key as an environment variable:
```bash
   export GEMINI_API_KEY=your_key_here
```
4. Update `application.properties` with your MySQL credentials
5. Run:
```bash
   ./mvnw spring-boot:run
```

## Design Notes
- Prompt construction is isolated from the API call layer (`PromptBuilder` vs `GeminiService`) — swapping LLM providers only requires changing one class
- `missingSkills`/`suggestions` are stored as JSON strings for v1 simplicity; a production version would normalize these into a child table
- Centralized exception handling via `@RestControllerAdvice` for clean error responses

## Future Improvements
- Docker containerization
- AWS deployment
- File upload (PDF parsing) instead of plain text input
- Frontend UI