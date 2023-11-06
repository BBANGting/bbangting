import { Route, Routes } from 'react-router-dom';
import './App.css';
import { Header } from './components/Header';
import { Home } from './pages/Home';
import { Login } from './pages/Login';
import { Signup } from './pages/Signup';
import { CommingSoon } from './pages/CommingSoon';
import { Store } from './pages/Store';

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/commingsoon" element={<CommingSoon />} />
        <Route path="/store" element={<Store />} />
      </Routes>
    </>
  );
}

export default App;
