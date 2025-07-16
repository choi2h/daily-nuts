import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router';
import FeedPage from './pages/FeedPage';
import MyPage from './pages/MyPage';
import PostDetail from './pages/PostDetailPage';
import SignupPage from './pages/SignupPage';
import LoginPage from './pages/LoginPage';
import PostWritePage from './pages/PostWritePage';
import ProfilePage from './pages/ProfilePage';

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<FeedPage/>}></Route>
        <Route path='/posts' element={<FeedPage/>}></Route>
        <Route path='/posts/likes' element={<FeedPage/>}></Route>
        <Route path='/post/:id' element={<PostDetail/>}></Route>
        <Route path='/post/write' element={<PostWritePage/>}></Route>
        <Route path='/mypage' element={<MyPage/>}></Route>
        <Route path='/profile/:id' element={<ProfilePage/>}></Route>
        <Route path='/signup' element={<SignupPage/>}></Route>
        <Route path='/login' element={<LoginPage/>}></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
