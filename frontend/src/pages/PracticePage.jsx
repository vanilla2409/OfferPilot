import { Search, Code2 } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

export default function PracticePage() {
    const navigate = useNavigate();
    const questions = [
        { id: 1, title: 'Two Sum', difficulty: 'EASY', topic: 'Arrays', status: 'Solved' },
        { id: 2, title: 'Valid Parentheses', difficulty: 'EASY', topic: 'Stack', status: 'Unsolved' },
        { id: 3, title: 'Longest Substring Without Repeating Characters', difficulty: 'MEDIUM', topic: 'Sliding Window', status: 'Unsolved' },
        { id: 4, title: 'Merge Intervals', difficulty: 'MEDIUM', topic: 'Sorting', status: 'Unsolved' },
        { id: 5, title: 'LRU Cache', difficulty: 'HARD', topic: 'Design', status: 'Solved' }
    ];

    const getBadgeClass = (diff) => {
        switch (diff) {
            case 'EASY': return 'badge-easy';
            case 'MEDIUM': return 'badge-medium';
            case 'HARD': return 'badge-hard';
            default: return '';
        }
    };

    return (
        <div className="container flex flex-col gap-6">
            <div className="flex justify-between items-center" style={{ flexWrap: 'wrap', gap: '1rem' }}>
                <h2>Coding Practice</h2>
                <div className="flex items-center gap-2" style={{ position: 'relative' }}>
                    <Search className="text-secondary" style={{ position: 'absolute', left: '0.75rem' }} size={18} />
                    <input type="text" className="input" placeholder="Search problems..." style={{ paddingLeft: '2.5rem', width: '300px' }} />
                </div>
            </div>

            <div className="card" style={{ padding: '0' }}>
                <div className="flex justify-between text-secondary text-sm" style={{ padding: '1.25rem 1.5rem', borderBottom: '1px solid var(--border)' }}>
                    <div style={{ flex: 3 }}>Title</div>
                    <div style={{ flex: 1 }}>Topic</div>
                    <div style={{ flex: 1, textAlign: 'center' }}>Difficulty</div>
                    <div style={{ flex: 1, textAlign: 'right' }}>Status</div>
                </div>

                <div className="flex flex-col">
                    {questions.map((q, idx) => (
                        <div key={q.id}
                            onClick={() => navigate(`/practice/${q.id}`)}
                            className="flex items-center justify-between transition-colors hover:bg-neutral-800"
                            style={{
                                padding: '1.25rem 1.5rem',
                                borderBottom: idx !== questions.length - 1 ? '1px solid var(--border-light)' : 'none',
                                cursor: 'pointer',
                            }}>
                            <div className="flex items-center gap-3" style={{ flex: 3 }}>
                                <Code2 size={20} className={q.status === 'Solved' ? 'text-success' : 'text-blue-500'} />
                                <span style={{ fontWeight: 500 }}>{q.title}</span>
                            </div>
                            <div className="text-secondary text-sm" style={{ flex: 1 }}>{q.topic}</div>
                            <div style={{ flex: 1, textAlign: 'center' }}>
                                <span className={`badge ${getBadgeClass(q.difficulty)}`}>{q.difficulty}</span>
                            </div>
                            <div className={`text-sm ${q.status === 'Solved' ? 'text-success' : 'text-secondary'}`} style={{ flex: 1, textAlign: 'right', fontWeight: q.status === 'Solved' ? 600 : 400 }}>
                                {q.status}
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
