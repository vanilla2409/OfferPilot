import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import PracticePage from './pages/PracticePage';
import CodingSessionPage from './pages/CodingSessionPage';

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/practice" element={<PracticePage />} />
          <Route path="/practice/:id" element={<CodingSessionPage />} />
          {/* We will add more routes as we build more pages */}
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;
