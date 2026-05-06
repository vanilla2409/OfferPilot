from fastapi import FastAPI, UploadFile, File
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import uvicorn
import os
from dotenv import load_dotenv

# Load environment logic
load_dotenv()

app = FastAPI(title="OfferPilot AI Service")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class ResumeScoreRequest(BaseModel):
    text: str

class CodeFeedbackRequest(BaseModel):
    question_description: str
    code: str

@app.get("/")
def read_root():
    return {"status": "AI Service Running"}

@app.post("/api/ai/resume")
def analyze_resume(req: ResumeScoreRequest):
    # In reality, this would use openai API
    # Mocked complex response shape
    return {
        "score": 82,
        "keywords_found": ["Java", "Spring Boot", "React"],
        "feedback": "Strong project experience. Add quantitative metrics to roles.",
        "skills_gap": ["Docker", "Kubernetes"]
    }

@app.post("/api/ai/code-feedback")
def evaluate_code(req: CodeFeedbackRequest):
    # Call OpenAI to evaluate code internally
    return {
        "overallScore": 85,
        "correctness": 90,
        "efficiency": 80,
        "strengths": "Good logical flow and variable naming.",
        "improvements": "Consider a hash map for O(n) lookups.",
        "detailed_feedback": "Looping twice leads to O(n^2) time complexity. Using extra space can improve speed."
    }

@app.post("/api/ai/hint")
def generate_hint(question: str):
    return {
        "hint": "Try thinking about how you can avoid nested loops using a HashSet."
    }

if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
