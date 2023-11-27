import { Route, Routes } from 'react-router-dom';
import './App.css';
import { Header } from './components/Header';
import { Home } from './pages/Home';
import { Login } from './pages/Login';
import { Signup } from './pages/Signup';
import { CommingSoon } from './pages/CommingSoon';
import { Store } from './pages/Store';
import { MyPage } from './pages/MyPage';
import { MyStorePage } from './pages/MyStorePage';
import StoreDetail from './pages/StoreDetail';

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
        <Route path="/store/:storeId" element={<StoreDetail />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/mystorepage" element={<MyStorePage />} />
      </Routes>
    </>
  );
}

export default App;
