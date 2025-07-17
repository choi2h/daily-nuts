import '../assets/css/Profile.css';
import { useState } from 'react';
import { useNavigate } from 'react-router';
import PostItem from '../components/PostItem';
import defaultProfile from '../assets/images/default-profile.png';
import DefaultLayout from '../layers/DefaultLayout';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';
import SubscriptionModal from "../components/SubscriptionModal"; // 모달 컴포넌트 import

const defaultPosts = [
  {
    id: 1,
    author: "Amit Das",
    time: "2025-07-11",
    avatar: "A",
    title: "Your portfolio is stopping you from getting that job",
    contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
    category: "Portfolio",
    readTime: "3 min read",
    tag: "Selected for you",
    image: "/api/placeholder/400/300",
    hasImage: true,
    liked: false
  },
  {
    id: 2,
    author: "Amit Das",
    time: "2025-07-11",
    avatar: "A",
    title: "Melody mobile app: a UI UX case study",
    contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
    category: "UI ux Design",
    readTime: "3 min read",
    tag: "Selected for you",
    image: "/api/placeholder/400/300",
    hasImage: true,
    liked: true
  },
  {
    id: 3,
    author: "Amit Das",
    time: "2025-07-09",
    avatar: "A",
    title: "Wellness app: a UI UX case study",
    contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
    category: "UI ux Design",
    readTime: "3 min read",
    tag: "Selected for you",
    image: "/api/placeholder/400/300",
    hasImage: true,
    liked: false
  }
];

const ProfilePage = () => {
    const navigate = useNavigate();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [posts, setPosts] = useState(defaultPosts);

    const toggleLike = (postId) => {
        setPosts(posts.map(post => 
        post.id === postId 
            ? { 
                ...post, 
                liked: !post.liked, 
                // likes: post.liked ? post.likes - 1 : post.likes + 1 
            }
            : post
        ));
    };

    const postOnClick = (id) => {
      console.log("click post!!!! " + id);
      navigate(`/post/${id}`);
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
                        <div className="profile-name">김00</div>
                    </div>
                    <div className="profile-info">
                        <div className="subscription-info">
                          <span className="subscription-label">구독자</span>
                          <span className="subscription-count">30</span>
                        </div>
                        <button className="subscribe-button" onClick={isModalOpen ? closeModal : openModal}>구독하기</button>
                    </div>
                </div>

                <div className="profile-description">
                  <p>성격심리학 박사 | 발달심리 전문가 | 종단연구 분석가</p>
                  <p>- 연세대학교 심리학 박사</p>
                  <p>- 성격 및 발달 종단 연구 다수 참여 (청소년 ~ 장노년층 대상)</p>
                  <p>- 『성격의 안정성과 변화 가능성』 주제로 국내외 학술지 논문 다수 게재</p>
                  <p>- 성격의 유전율과 환경 영향, 성인기 이후 성격 변화에 대한 연구 중점</p>
                  <p>- 대중심리 컬럼, 기업 인성검사 자문, 방송 심리자문 등 경험 보유</p>
                </div>


                {/* ✅ 모달 */}
                <SubscriptionModal
                  isOpen={isModalOpen}
                  onClose={closeModal}
                  expertName='김00'
                  price='2900'
                />
            </div>

            {/* 게시글 섹션 */}
            <div className="posts-section">
                <div className="posts-header">
                <span className="posts-label">전체 글</span>
                <span className="posts-count">30</span>
                </div>

                <div className="main-content">
                    <div>
                    {posts.map((post, idx) => 
                        <PostItem key={idx} post={post} toggleLike={toggleLike} onClick={postOnClick}/>
                        )}
                    </div>
                </div>
            </div>
            </div>
        </BlankHeaderLayout>
    </DefaultLayout>
  );
};

export default ProfilePage;