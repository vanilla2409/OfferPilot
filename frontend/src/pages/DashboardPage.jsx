import { Link } from 'react-router-dom';
import { Target, TrendingUp, Clock, CheckCircle } from 'lucide-react';

export default function DashboardPage() {
    return (
        <div className="container flex flex-col gap-8">
            <div className="flex justify-between items-center" style={{ flexWrap: 'wrap', gap: '1rem' }}>
                <h2>Welcome to your Dashboard</h2>
                <Link to="/practice" className="btn btn-primary">New Session</Link>
            </div>

            <div className="flex gap-4 mb-4" style={{ flexWrap: 'wrap' }}>
                <div className="card flex flex-col gap-2" style={{ flex: '1 1 200px' }}>
                    <div className="flex items-center gap-2 text-secondary">
                        <CheckCircle size={20} className="text-success" />
                        <span>Questions Solved</span>
                    </div>
                    <h1 className="text-gradient">42</h1>
                </div>
                <div className="card flex flex-col gap-2" style={{ flex: '1 1 200px' }}>
                    <div className="flex items-center gap-2 text-secondary">
                        <TrendingUp size={20} className="text-blue-500" />
                        <span>Avg Coding Score</span>
                    </div>
                    <h1 className="text-gradient">86%</h1>
                </div>
                <div className="card flex flex-col gap-2" style={{ flex: '1 1 200px' }}>
                    <div className="flex items-center gap-2 text-secondary">
                        <Clock size={20} className="text-warning" />
                        <span>Hours Practiced</span>
                    </div>
                    <h1 className="text-gradient">14.5</h1>
                </div>
            </div>

            <h3>Recent Activity</h3>
            <div className="card flex flex-col gap-0" style={{ padding: 0 }}>
                <div className="flex items-center justify-between" style={{ padding: '1.5rem', borderBottom: '1px solid var(--border-light)' }}>
                    <div>
                        <h4 style={{ marginBottom: '0.25rem' }}>Two Sum</h4>
                        <span className="text-sm text-secondary">Coding Interview • 2 hours ago</span>
                    </div>
                    <span className="badge badge-easy">EASY</span>
                </div>
                <div className="flex items-center justify-between" style={{ padding: '1.5rem', borderBottom: '1px solid var(--border-light)' }}>
                    <div>
                        <h4 style={{ marginBottom: '0.25rem' }}>Behavioral Mock</h4>
                        <span className="text-sm text-secondary">HR Interview • Yesterday</span>
                    </div>
                    <span className="badge" style={{ background: 'rgba(139, 92, 246, 0.2)', color: 'var(--accent-tertiary)' }}>HR</span>
                </div>
                <div className="flex items-center justify-between" style={{ padding: '1.5rem' }}>
                    <div>
                        <h4 style={{ marginBottom: '0.25rem' }}>LRU Cache</h4>
                        <span className="text-sm text-secondary">Coding Interview • 3 days ago</span>
                    </div>
                    <span className="badge badge-hard">HARD</span>
                </div>
            </div>
        </div>
    );
}
