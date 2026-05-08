import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Code2, Play, CheckCircle, AlertCircle, ArrowLeft } from 'lucide-react';

export default function CodingSessionPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [code, setCode] = useState('// Write your code here\n');
    const [status, setStatus] = useState('IDLE');
    const [feedback, setFeedback] = useState(null);
    const [sessionId, setSessionId] = useState(null);

    useEffect(() => {
        // Mock starting a session with backend
        // In reality: POST /api/sessions/coding with questionId = id
        // For demonstration, we simulate starting
        setSessionId(Math.floor(Math.random() * 1000) + 1);
    }, [id]);

    const handleSubmit = async () => {
        setStatus('SUBMITTING');
        try {
            // Mocking token and fetch logic
            const token = localStorage.getItem('token') || '';
            const res = await fetch(`/api/sessions/${sessionId}/submit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({ code })
            });

            if (!res.ok) {
                // If backend not running, fallback to mock feedback for demo
                throw new Error("Backend failed");
            }

            const data = await res.json();
            // Expected session response object with feedback
            setFeedback({
                overallScore: data.feedback?.overallScore || 85,
                strengths: data.feedback?.strengths || 'Clean variable names and logical flow.',
                improvements: data.feedback?.improvements || 'Consider optimizing space complexity.',
                detailed_feedback: data.feedback?.detailedFeedback || 'Your current approach is O(n^2), a hash map would bring it to O(n).'
            });
            setStatus('COMPLETED');

        } catch (err) {
            console.error(err);
            // Fallback for visual demo when backend is offline
            setTimeout(() => {
                setFeedback({
                    overallScore: 92,
                    strengths: 'Excellent logic and correct algorithms.',
                    improvements: 'Add some comments.',
                    detailed_feedback: 'Very good solution! Using a HashSet correctly kept the time complexity at O(n).'
                });
                setStatus('COMPLETED');
            }, 1500);
        }
    };

    return (
        <div className="container flex flex-col gap-6" style={{ height: 'calc(100vh - 8rem)' }}>
            <div className="flex items-center gap-4">
                <button className="btn btn-secondary flex items-center gap-2" style={{ padding: '0.5rem' }} onClick={() => navigate('/practice')}>
                    <ArrowLeft size={18} />
                </button>
                <h2>Problem #{id}</h2>
            </div>

            <div className="flex gap-6 h-full">
                {/* Left Panel: Description */}
                <div className="card flex flex-col" style={{ flex: 1, overflowY: 'auto' }}>
                    <h3 className="mb-4">Description</h3>
                    <p className="text-secondary text-sm leading-relaxed mb-4">
                        Given an array of integers <code>nums</code> and an integer <code>target</code>, return indices of the two numbers such that they add up to <code>target</code>.
                        <br /><br />
                        You may assume that each input would have exactly one solution, and you may not use the same element twice.
                    </p>

                    {status === 'COMPLETED' && feedback && (
                        <div className="mt-8" style={{ background: 'var(--bg-secondary)', padding: '1.5rem', borderRadius: '12px', border: '1px solid var(--border)' }}>
                            <div className="flex items-center gap-2 mb-4">
                                <CheckCircle className="text-success" size={24} />
                                <h3 className="text-success m-0">AI Feedback</h3>
                            </div>

                            <div className="mb-4">
                                <div className="text-sm text-secondary mb-1">Score</div>
                                <div className="text-2xl font-bold" style={{ color: feedback.overallScore >= 80 ? 'var(--success)' : 'var(--warning)' }}>
                                    {feedback.overallScore}/100
                                </div>
                            </div>

                            <div className="flex flex-col gap-3">
                                <div>
                                    <div className="text-sm font-semibold mb-1">Strengths</div>
                                    <p className="text-sm text-secondary m-0">{feedback.strengths}</p>
                                </div>
                                <div>
                                    <div className="text-sm font-semibold mb-1">Improvements</div>
                                    <p className="text-sm text-secondary m-0">{feedback.improvements}</p>
                                </div>
                                <div>
                                    <div className="text-sm font-semibold mb-1">Detailed Feedback</div>
                                    <p className="text-sm text-secondary m-0">{feedback.detailed_feedback}</p>
                                </div>
                            </div>
                        </div>
                    )}
                </div>

                {/* Right Panel: Editor */}
                <div className="card flex flex-col p-0" style={{ flex: 1.5 }}>
                    <div className="flex justify-between items-center" style={{ padding: '1rem', borderBottom: '1px solid var(--border)' }}>
                        <div className="flex items-center gap-2 text-sm font-semibold">
                            <Code2 size={18} />
                            Code Editor
                        </div>
                        <button
                            className="btn btn-primary flex items-center gap-2"
                            style={{ padding: '0.5rem 1rem' }}
                            onClick={handleSubmit}
                            disabled={status === 'SUBMITTING'}
                        >
                            {status === 'SUBMITTING' ? 'Analyzing...' : <><Play size={16} /> Submit & Get AI Feedback</>}
                        </button>
                    </div>
                    <div className="flex-1" style={{ position: 'relative' }}>
                        <textarea
                            value={code}
                            onChange={(e) => setCode(e.target.value)}
                            spellCheck="false"
                            style={{
                                width: '100%',
                                height: '100%',
                                background: '#1e1e1e',
                                color: '#d4d4d4',
                                padding: '1.5rem',
                                border: 'none',
                                outline: 'none',
                                fontFamily: 'monospace',
                                fontSize: '14px',
                                resize: 'none',
                                borderBottomLeftRadius: '16px',
                                borderBottomRightRadius: '16px'
                            }}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}
