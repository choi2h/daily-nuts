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
  const [posts, setPosts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchExpert = async () => {
      try {
        const res = await axios.get(`/member/expert/${id}`);
        const data = res.data;

        const fixed = data.fixedPosts?.map(post => ({ ...post, pinned: true })) || [];
        const normal = data.normalPosts?.map(post => ({ ...post, pinned: false })) || [];

        const combined = [...fixed, ...normal];

        setExpert(data);
        setPosts(combined);
      } catch (err) {
        console.error('전문가 조회 오류:', err); // 디버깅용
        setTimeout(() => {
          alert('등록된 전문가가 없습니다.');
          navigate(-1);
        }, 2000);
      }
    };

    if (id) {
      fetchExpert();
    }
  }, [id]);

  const toggleLike = (postId) => {
    setPosts(prev =>
      prev.map(post =>
        post.id === postId ? { ...post, liked: !post.liked } : post
      )
    );
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
                  <span className="posts-label">작성글</span>
                  <span className="posts-count">{posts.length}</span>
                </div>

                <div className='main-content'>
                  {posts.length === 0 ? (
                    <p>게시글이 없습니다.</p>
                  ) : (
                    posts.map((post, idx) => (
                        <PostItem key={`fixed-${idx}`} post={post} toggleLike={toggleLike} onClick={postOnClick} />
                      ))
                    )}
                </div>
              </div>
            </div>
        </BlankHeaderLayout>
    </DefaultLayout>
  );
};

export default ProfilePage;