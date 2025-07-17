import { useState, useEffect, useCallback, useRef } from 'react';
import '../assets/css/Feed.css';
import PostItem from '../components/PostItem';
import DefaultLayout from '../layers/DefaultLayout';
import TabHeaderLyaout from '../layers/TabHeaderLayout';
import { useNavigate } from 'react-router';
import axios from 'axios';

// const defaultPosts = [
//   {
//     id: 1,
//     author: "Amit Das",
//     time: "2025-07-11",
//     avatar: "A",
//     title: "Your portfolio is stopping you from getting that job",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "Portfolio",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: false
//   },
//   {
//     id: 2,
//     author: "Amit Das",
//     time: "2025-07-11",
//     avatar: "A",
//     title: "Melody mobile app: a UI UX case study",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "UI ux Design",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: true
//   },
//   {
//     id: 3,
//     author: "Amit Das",
//     time: "2025-07-09",
//     avatar: "A",
//     title: "Wellness app: a UI UX case study",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "UI ux Design",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: false
//   },
//   {
//     id: 4,
//     author: "Amit Das",
//     time: "2025-07-09",
//     avatar: "A",
//     title: "Wellness app: a UI UX case study",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "UI ux Design",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: false
//   },
//   {
//     id: 5,
//     author: "Amit Das",
//     time: "2025-07-09",
//     avatar: "A",
//     title: "Wellness app: a UI UX case study",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "UI ux Design",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: false
//   },
//   {
//     id: 6,
//     author: "Amit Das",
//     time: "2025-07-09",
//     avatar: "A",
//     title: "Wellness app: a UI UX case study",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "UI ux Design",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: false
//   },
//   {
//     id: 7,
//     author: "Amit Das",
//     time: "2025-07-09",
//     avatar: "A",
//     title: "Wellness app: a UI UX case study",
//     contents: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
//     category: "UI ux Design",
//     readTime: "3 min read",
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: true,
//     liked: false
//   }
// ];

// // 더미 데이터 생성 함수
// const generateMorePosts = (startId) => {
//   const titles = [
//     "10 Tips for Better UX Design",
//     "React Best Practices in 2024",
//     "The Future of Web Development",
//     "Design Systems That Actually Work",
//     "Mobile First Design Principles",
//     "Color Theory for Designers",
//     "Typography in Digital Design",
//     "User Research Methods",
//     "Accessibility in Web Design",
//     "Figma Tips and Tricks"
//   ];
  
//   const contents = [
//     "Learn the essential principles that make great user experiences. From research to implementation, discover what works...",
//     "Stay up to date with the latest React patterns and best practices. Performance optimization, hooks, and more...",
//     "Explore emerging technologies and trends that will shape how we build digital products in the coming years...",
//     "Building scalable design systems that teams actually use. Component libraries, tokens, and documentation...",
//     "Why mobile-first approach is crucial for modern web development. Responsive design patterns and techniques...",
//     "Understanding color psychology and how to use it effectively in your designs. Palettes, contrast, and accessibility...",
//     "The art of choosing and pairing fonts for digital interfaces. Hierarchy, readability, and brand consistency...",
//     "Effective methods for understanding your users. Interviews, surveys, usability testing, and analytics...",
//     "Making your designs inclusive and accessible to all users. WCAG guidelines, testing, and implementation...",
//     "Power user tips for getting the most out of Figma. Plugins, shortcuts, and advanced techniques..."
//   ];
  
//   const categories = ["Design", "Development", "UX Research", "Typography", "Accessibility"];
  
//   return Array.from({ length: 5 }, (_, i) => ({
//     id: startId + i,
//     author: `User ${startId + i}`,
//     time: `2025-07-${10 - Math.floor(i / 3)}`,
//     avatar: `U${startId + i}`,
//     title: titles[i % titles.length],
//     contents: contents[i % contents.length],
//     category: categories[i % categories.length],
//     readTime: `${Math.floor(Math.random() * 5) + 2} min read`,
//     tag: "Selected for you",
//     image: "/api/placeholder/400/300",
//     hasImage: Math.random() > 0.3,
//     liked: Math.random() > 0.7
//   }));
// };

const categories = [
    { id: 0, name: '전체' },
    { id: 1, name: '스트레스' },
    { id: 2, name: '대인관계' },
    { id: 3, name: '자기이해' },
    { id: 4, name: '불안' },
    { id: 5, name: '기억' },
    { id: 6, name: '기타' },
  ];

const FeedPage = () => {
  const [posts, setPosts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const [sortCriteria, setSortCriteria] = useState("createdAt");
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
  const fetchInitialPosts = async () => {
    setLoading(true);

    try {
      const params = {
        page: 0,
        size: size,
        criteria: sortCriteria,
      };
      if (selectedCategory.id !== 0) {
        params.categoryId = selectedCategory.id;
      }
      const res = await axios.get('/api/posts', { params });

      setPosts(res.data.content || []);
      setHasMore(!res.data.last);
      setCurrentPage(1);
    } catch (err) {
      console.error('초기 글 로딩 실패:', err);
      setPosts([]);
    } finally {
      setLoading(false);
    }
  };

  fetchInitialPosts();
}, [selectedCategory, sortCriteria]);

  const changeCategory = (category) => {
    console.log(category.target);
    setSelectedCategory(category);
    setCurrentPage(0);
  }

  const toggleLike = async (postId, liked) => {
    try {
      const url = `/api/post/${postId}/like`;
      let res;

      if (liked) {
        res = await axios.delete(url);
      } else {
        res = await axios.post(url);
      }

      const {likeCount, isLiked} = res.data;

      setPosts((prevPosts) => 
        prevPosts.map((post) =>
          post.id == postId
            ? {...post, likeCount: likeCount, 
              liked: isLiked} : post
        )
      );

    } catch (err) {
      console.error('좋아요 처리 오류:', err);
    }
  };

  const postOnClick = (id) => {
    console.log("click post!!!! " + id);
    navigate(`/post/${id}`);
  }

  const size = 10;
  const isFetchingRef = useRef(false);

  const loadMorePosts = useCallback(async () => {
    if (loading || !hasMore) return;

    isFetchingRef.current = true;
    setLoading(true);

    try {
      const params = {
        page: currentPage,
        size: size,
        criteria: sortCriteria,
      };
      if (selectedCategory.id !== 0) {
        params.categoryId = selectedCategory.id;
      }

      const res = await axios.get('/api/posts', { params });

      const newPosts = res.data.content;

      setPosts((prev) => {
        const prevIds = new Set(prev.map(p => p.id));
        const uniqueNewPosts = newPosts.filter(p => !prevIds.has(p.id));
        const updated = [...prev, ...uniqueNewPosts];
        return updated;
      });
      setHasMore(!res.data.last);
      setCurrentPage((prev) => prev + 1);
    } catch (err) {
      console.error('포스트 로딩 오류:', err);
    } finally {
      setLoading(false);
      isFetchingRef.current = false;
    }
  }, [loading, hasMore, currentPage, sortCriteria, selectedCategory]);

  useEffect(() => {
    setPosts([]);
    setCurrentPage(0);
    setHasMore(true);
  }, [selectedCategory, sortCriteria]);
  
  // // 더 많은 포스트 로드
  // const loadMorePosts = useCallback(async () => {
  //   if (loading || !hasMore) return;
    
  //   setLoading(true);
    
  //   // API 호출 시뮬레이션
  //   setTimeout(() => {
  //     const nextId = posts.length + 1;
  //     const newPosts = generateMorePosts(nextId);
      
  //     setPosts(prev => [...prev, ...newPosts]);
  //     setLoading(false);
      
  //     // 임시로 30개 이후에는 더 이상 로드하지 않음
  //     if (posts.length >= 25) {
  //       setHasMore(false);
  //     }
  //   }, 1000);
  // }, [loading, hasMore, posts.length]);

  // 무한 스크롤 이벤트 핸들러
  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.pageYOffset;
      const windowHeight = window.innerHeight;
      const documentHeight = document.documentElement.scrollHeight;
      
      if (scrollTop + windowHeight >= documentHeight - 1000 && !loading && hasMore) {
        loadMorePosts();
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, [loadMorePosts, loading, hasMore]);

  return (
    <>
      <DefaultLayout>
          <TabHeaderLyaout 
            categories={categories} 
            selectedCategory={selectedCategory} 
            changeCategory={changeCategory}
            type="feed"
          >
            <div className="main-content">
              <div className="posts-container">
                {posts.map((post) => 
                  <PostItem key={post.id} post={post} toggleLike={toggleLike} onClick={postOnClick}/>
                )}
                
                {loading && (
                  <div className="loading-container">
                    <div className="loading-spinner">
                      <div className="spinner"></div>
                      <span>Loading more posts...</span>
                    </div>
                  </div>
                )}
                
                {!hasMore && (
                  <div className="end-message">
                    <span>모든 게시물을 확인했습니다 ✨</span>
                  </div>
                )}
              </div>
            </div>
          </TabHeaderLyaout>
        </DefaultLayout>
    </>
  );
};

export default FeedPage;