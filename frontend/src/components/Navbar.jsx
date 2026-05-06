import { Link, useLocation } from 'react-router-dom';
import { Rocket, User, Grid } from 'lucide-react';

export default function Navbar() {
    const location = useLocation();
    const isActive = (path) => location.pathname === path;

    return (
        <header>
            <div className="container nav-container">
                <Link to="/" className="flex items-center gap-2" style={{ textDecoration: 'none' }}>
                    <Rocket className="text-blue-500" size={28} />
                    <h2 className="text-gradient">OfferPilot</h2>
                </Link>
                <nav className="nav-links">
                    <Link to="/practice" className={`nav-link ${isActive('/practice') ? 'active' : ''}`}>Practice</Link>
                    <Link to="/dashboard" className={`nav-link ${isActive('/dashboard') ? 'active' : ''}`}>Dashboard</Link>

                    <div className="flex items-center gap-4">
                        <Link to="/login" className="btn btn-secondary text-sm">Log in</Link>
                        <Link to="/login" className="btn btn-primary text-sm">Sign up</Link>
                    </div>
                </nav>
            </div>
        </header>
    );
}
