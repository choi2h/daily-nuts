import '../assets/css/Profile.css';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import PostItem from '../components/PostItem';
import defaultProfile from '../assets/images/default-profile.png';
import DefaultLayout from '../layers/DefaultLayout';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';
import SubscriptionModal from "../components/SubscriptionModal"; // 모달 컴포넌트 import
import { getExpertProfile } from '../service/MemberInfoService';

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
    const { id } = useParams();
    const navigate = useNavigate();
    const [memberInfo, setMemberInfo] = useState({
      name: '',
      subscriberCount: 0,
      subscribed: false,
      description: ''
    });
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [posts, setPosts] = useState(defaultPosts);

    useEffect(() => {
      getExpertProfile(id).then((res) => {
        console.log(res.data);
        setMemberInfo(res.data);
      }).catch((err) => {
        console.log(err);
      })
    }, [id]);

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
                        <div className="profile-name">{memberInfo.name}</div>
                    </div>
                    <div className="profile-info">
                        <div className="subscription-info">
                          <span className="subscription-label">구독자</span>
                          <span className="subscription-count">{memberInfo.subscriberCount}</span>
                        </div>
                        <button className="subscribe-button" onClick={memberInfo.subscribed && isModalOpen ? closeModal : openModal}>
                          {memberInfo.subscribed ? '구독중' : '구독하기' }
                        </button>
                    </div>
                </div>

                <div className="profile-description">{memberInfo.description}</div>


                {/* ✅ 모달 */}
                <SubscriptionModal
                  isOpen={isModalOpen}
                  onClose={closeModal}
                  memberInfo={memberInfo}
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