import '../assets/css/Profile.css';
import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router';
import PostItem from '../components/PostItem';
import defaultProfile from '../assets/images/default-profile.png';
import DefaultLayout from '../layers/DefaultLayout';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';
import SubscriptionModal from "../components/SubscriptionModal"; // 모달 컴포넌트 import
import axios from '../api/axiosConfig';

const ProfilePage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [expert, setExpert] = useState(null);
  const [fixedPosts, setFixedPosts] = useState([]);
  const [normalPosts, setNormalPosts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    axios.get(`/member/expert/${id}`)
      .then(res => {
        const data = res.data;
        data.isSubscribed = data.subscribed;
        setExpert(res.data);
        setFixedPosts(res.data.fixedPosts || []);
        setNormalPosts(res.data.normalPosts || []);
      })
      .catch(err => {
        alert('등록된 전문가가 없습니다.');
        navigate(-1);
      });
  }, [id]);

  const toggleLike = (postId) => {
    const toggleInPosts = (posts) =>
      posts.map(post =>
        post.id === postId ? { ...post, liked: !post.liked } : post
      );
      setFixedPosts(prev => toggleInPosts(prev));
      setNormalPosts(prev => toggleInPosts(prev));
  };

  const postOnClick = (id) => {
    navigate(`/post/${id}`);
  };

  if (!expert) {
    return (
      <DefaultLayout className="app">
        <BlankHeaderLayout>
        </BlankHeaderLayout>
      </DefaultLayout>
    );
  }

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <DefaultLayout className="app">
        <BlankHeaderLayout>
            <div className="profile-layout">
            {/* 프로필 섹션 */}
            <div className="profile-section">
                <div className="profile-header">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            <img className="profile-image" src={defaultProfile} alt="프로필 사진" />
                        </div>
                        <div className="profile-name">{expert.name}</div>
                    </div>
                    <div className="profile-info">
                        <div className="subscription-info">
                          <span className="subscription-label">구독자</span>
                          <span className="subscription-count">{expert.subscriberCount}</span>
                        </div>

                        <button className={`subscribe-button ${expert.subscribed ? 'disable' : ''}`} onClick={expert.subscribed && isModalOpen ? closeModal : openModal}>
                          {expert.subscribed ? '구독중' : '구독하기' }
                        </button>
                    </div>
                </div>

                <div className="profile-description">
                  {expert.description.split('\n').map((line, i) => (
                    <p key={i}>{line}</p>
                  ))}
                </div>

                <SubscriptionModal
                  isOpen={isModalOpen}
                  onClose={closeModal}
                  expertId={expert.id}
                  expertName={expert.name}
                  expertDescription={expert.description}
                  price={2900}
                />
            </div>

            {/* 게시글 섹션 */}
            <div className="posts-section">
                <div className="posts-header">
                  <span className="posts-label">고정 글</span>
                  <span className="posts-count">{fixedPosts.length}</span>
                </div>

                <div className='main-content'>
                  {fixedPosts.length === 0
                    ? <p>고정 글이 없습니다.</p>
                    : fixedPosts.map((post, idx) =>
                        <PostItem key={`fixed-${idx}`} post={post} toggleLike={toggleLike} onClick={postOnClick} />
                      )}
                </div>

                <hr style={{ border: '2px solid #aaa', margin: '2rem 0' }} />

                <div className="posts-header">
                  <span className="posts-label">일반 글</span>
                  <span className="posts-count">{normalPosts.length}</span>
                </div>
                <div className="main-content">
                  {normalPosts.length === 0
                    ? <p>일반 글이 없습니다.</p>
                    : normalPosts.map((post, idx) =>
                        <PostItem key={`normal-${idx}`} post={post} toggleLike={toggleLike} onClick={postOnClick} />
                      )}
                </div>

            </div>
            </div>
        </BlankHeaderLayout>
    </DefaultLayout>
  );
};

export default ProfilePage;