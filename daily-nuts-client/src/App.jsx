import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router';
import FeedPage from './pages/FeedPage';
import MyPage from './pages/MyPage';
import PostDetail from './pages/PostDetailPage';
import SignupPage from './pages/SignupPage';
import LoginPage from './pages/LoginPage';
import PostWritePage from './pages/PostWritePage';
import ProfilePage from './pages/ProfilePage';
import NotificationPage from './pages/NotificationPage';
import SubscribeListPage from './pages/SubscribeListPage';
import PostEditPage from './pages/PostEditPage';
import SearchResultPage from './pages/SearchResultPage';

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
        <Route path='/notification' element={<NotificationPage/>}></Route>
        <Route path='/subscribe' element={<SubscribeListPage/>}></Route>
        <Route path='/signup' element={<SignupPage/>}></Route>
        <Route path='/login' element={<LoginPage/>}></Route>
        <Route path="/post/edit/:id" element={<PostEditPage />}></Route>
        <Route path="/search" element={<SearchResultPage/>}></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
