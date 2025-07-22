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
  const [isOwnProfile, setIsOwnProfile] = useState(false);

  const sortPosts = (posts) => {
    return [...posts].sort((a, b) => {
      if (a.pinned === b.pinned) {
        return new Date(b.createdAt) - new Date(a.createdAt);
      }
      return b.pinned - a.pinned;
    });
  };

  useEffect(() => {
    const fetchExpert = async () => {
      try {
        const res = await axios.get(`/post/expert/${id}`);
        const data = res.data;
        console.log(data);

        setExpert(data);
        setFixedPosts(data.fixedPosts || []);
        setNormalPosts(data.normalPosts || []);

        const storedMemberId = localStorage.getItem("memberId");
        if (storedMemberId && String(storedMemberId) === String(data.id)) {
          setIsOwnProfile(true);
        }
      } catch (err) {
        setTimeout(() => {
          window.alert('등록된 전문가가 없습니다.');
          navigate("/");
        }, 2000);
      }
    };

    if (id) {
      fetchExpert();
    }
  }, [id]);

    const toggleLike = async (postId, beforeLiked) => {
    try {
      const url = `/post/${postId}/like`;
      let res;

      if (beforeLiked) {
        res = await axios.delete(url);
      } else {
        res = await axios.post(url);
      }

      const {likeCount, liked} = res.data;
      setFixedPosts((prevPosts) => 
        prevPosts.map((post) =>
          post.id == postId
            ? {...post, likeCount, liked} : post
        )
      );

      setNormalPosts((prevPosts) => 
        prevPosts.map((post) =>
          post.id == postId
            ? {...post, likeCount, liked} : post
        )
      );
    } catch (err) {
      console.error('좋아요 처리 오류:', err);
    }
  };

  const postOnClick = (id) => {
    navigate(`/post/${id}`);
  };

  const togglePinned = async (postId) => {
    const allPosts = [...fixedPosts, ...normalPosts];
    const post = allPosts.find(p => p.id === postId);
    const pinnedCount = fixedPosts.length;

    if (!post.pinned && pinnedCount >= 3) {
      alert("고정글은 최대 3개까지 설정할 수 있습니다.");
      return;
    }

    const updated = { ...post, pinned: !post.pinned };

    try {
      await axios.patch(`/post/${postId}/pin?pinned=${!post.pinned}`);

      if (updated.pinned) {
        setFixedPosts(prev => sortPosts([...prev, updated]));
        setNormalPosts(prev => prev.filter(p => p.id !== postId));
      } else {
        setNormalPosts(prev => sortPosts([...prev, updated]));
        setFixedPosts(prev => prev.filter(p => p.id !== postId));
      }

    } catch (err) {
      alert("고정글 설정에 실패했습니다.");
      console.error(err);
    }
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
                            <img className="profile-image" src={expert.profileImage ? `/profile-images/${expert.profileImage}` : defaultProfile} alt="프로필 사진" />
                        </div>
                        <div className="profile-name">{expert.name}</div>
                    </div>
                    <div className="profile-info">
                        <div className="subscription-info">
                          <span className="subscription-label">구독자</span>
                          <span className="subscription-count">{expert.subscriberCount}</span>
                        </div>
                        {
                          expert.id === localStorage.getItem('memberId') ? '' :
                          (
                             <button className={`subscribe-button ${expert.subscribed ? 'disable' : ''}`} onClick={expert.subscribed && isModalOpen ? closeModal : openModal}>
                              {expert.subscribed ? '구독중' : '구독하기' }
                            </button>
                          )
                        }
                       
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
                  <span className="posts-count">{fixedPosts.length + normalPosts.length}</span>
                </div>

                <div className='main-content'>
                  {expert.fixedPosts.length === 0 && expert.normalPosts.length === 0 ? (
                    <p>게시글이 없습니다.</p>
                  ) : (
                    <>
                      {fixedPosts.map((post, idx) => (
                        <PostItem
                          key={`fixed-${idx}`}
                          post={post}
                          toggleLike={toggleLike}
                          onClick={postOnClick}
                          isOwnProfile={isOwnProfile}
                          togglePinned={togglePinned}
                        />
                      ))}

                      {normalPosts.map((post, idx) => (
                        <PostItem
                          key={`normal-${idx}`}
                          post={post}
                          toggleLike={toggleLike}
                          onClick={postOnClick}
                          isOwnProfile={isOwnProfile}
                          togglePinned={togglePinned}
                        />
                      ))}
                    </>
                  )}
                </div>
              </div>
            </div>
        </BlankHeaderLayout>
    </DefaultLayout>
  );
};

export default ProfilePage;