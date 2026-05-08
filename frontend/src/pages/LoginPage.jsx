import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
    const [isLogin, setIsLogin] = useState(true);
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        // Mock authentication redirect
        navigate('/dashboard');
    };

    return (
        <div className="container flex items-center justify-center" style={{ minHeight: 'calc(100vh - 10rem)' }}>
            <div className="card flex flex-col gap-6" style={{ width: '100%', maxWidth: '400px' }}>
                <h2 className="text-center">{isLogin ? 'Welcome Back' : 'Create Account'}</h2>
                <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
                    {!isLogin && (
                        <div className="flex flex-col gap-1">
                            <label className="text-sm text-secondary">Full Name</label>
                            <input type="text" className="input" placeholder="John Doe" required />
                        </div>
                    )}
                    <div className="flex flex-col gap-1">
                        <label className="text-sm text-secondary">Email Address</label>
                        <input type="email" className="input" placeholder="you@example.com" required />
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm text-secondary">Password</label>
                        <input type="password" className="input" placeholder="••••••••" required />
                    </div>
                    <button type="submit" className="btn btn-primary mt-4" style={{ width: '100%' }}>
                        {isLogin ? 'Log In' : 'Sign Up'}
                    </button>
                </form>
                <div className="text-center text-sm text-secondary">
                    {isLogin ? "Don't have an account? " : "Already have an account? "}
                    <button
                        onClick={() => setIsLogin(!isLogin)}
                        className="text-blue-500 hover:text-blue-400"
                        style={{ background: 'none', border: 'none', cursor: 'pointer', padding: 0, fontWeight: 600 }}
                    >
                        {isLogin ? 'Sign up' : 'Log in'}
                    </button>
                </div>
            </div>
        </div>
    );
}
