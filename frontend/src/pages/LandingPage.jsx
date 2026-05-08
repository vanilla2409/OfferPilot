import { Link } from 'react-router-dom';
import { Rocket, Target, Code, Brain } from 'lucide-react';

export default function LandingPage() {
    return (
        <div className="container flex flex-col items-center justify-center gap-8 mt-8 text-center" style={{ minHeight: 'calc(100vh - 8rem)' }}>
            <h1 className="text-gradient" style={{ fontSize: '3rem', maxWidth: '800px' }}>Ace Your Next Interview with OfferPilot</h1>
            <p className="text-secondary" style={{ maxWidth: '600px', fontSize: '1.25rem' }}>
                AI-powered mock interviews, personalized DSA roadmaps, and instant feedback to help you land your dream tech job.
            </p>
            <div className="flex items-center gap-4 mt-4">
                <Link to="/practice" className="btn btn-primary text-xl" style={{ padding: '1rem 2rem' }}>Start Practicing <Rocket size={20} /></Link>
                <Link to="/login" className="btn btn-secondary text-xl" style={{ padding: '1rem 2rem' }}>Create Free Account</Link>
            </div>

            <div className="flex gap-6 mt-8" style={{ flexWrap: 'wrap', justifyItems: 'center', justifyContent: 'center' }}>
                <div className="card flex flex-col items-center gap-3" style={{ width: '300px' }}>
                    <Code className="text-blue-500" size={36} />
                    <h3>Coding Practice</h3>
                    <p className="text-secondary text-sm text-center">Solve DSA problems with an integrated environment and AI hints.</p>
                </div>
                <div className="card flex flex-col items-center gap-3" style={{ width: '300px' }}>
                    <Brain className="text-emerald-500" size={36} />
                    <h3>HR Interviews</h3>
                    <p className="text-secondary text-sm text-center">Simulate behavioral interviews with our real-time AI persona.</p>
                </div>
                <div className="card flex flex-col items-center gap-3" style={{ width: '300px' }}>
                    <Target className="text-violet-500" size={36} />
                    <h3>Smart Roadmaps</h3>
                    <p className="text-secondary text-sm text-center">Get a personalized learning path based on your performance.</p>
                </div>
            </div>
        </div>
    );
}
