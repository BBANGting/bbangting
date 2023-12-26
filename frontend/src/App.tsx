import { Route, Routes } from 'react-router-dom';
import './styles/global.css';
import { Header } from './components/Header';
import { Home } from './pages/Home';
import { Login } from './pages/Login';
import { Signup } from './pages/Signup';
import { ComingSoon } from './pages/ComingSoon';
import { Store } from './pages/Store';
import { MyPage } from './pages/MyPage';
import { MyStorePage } from './pages/MyStorePage';
import StoreDetail from './pages/StoreDetail';
import BreadDetail from './pages/BreadDetail';
import BreadUpload from './pages/BreadUpload';
import { ReactQueryDevtools } from 'react-query/devtools';
import StoreUpload from './pages/StoreUpload';

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/commingsoon" element={<ComingSoon />} />
        <Route path="/store" element={<Store />} />
        <Route path="/store/:storeId" element={<StoreDetail />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/mystorepage" element={<MyStorePage />} />
        <Route path="/bread/:breadId" element={<BreadDetail />} />
        <Route path="/new/bread/:storeId" element={<BreadUpload />} />
        <Route path="/new/store" element={<StoreUpload />} />
      </Routes>
      <ReactQueryDevtools position="bottom-right" />
    </>
  );
}

export default App;
