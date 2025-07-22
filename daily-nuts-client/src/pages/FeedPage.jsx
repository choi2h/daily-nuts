import { useState, useEffect, useCallback, useRef } from 'react';
import '../assets/css/Feed.css';
import PostItem from '../components/PostItem';
import DefaultLayout from '../layers/DefaultLayout';
import TabHeaderLyaout from '../layers/TabHeaderLayout';
import { useLocation, useNavigate } from 'react-router';
import axios from '../api/axiosConfig';

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
  const {pathname} = useLocation(); 
  const [posts, setPosts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const [sortCriteria, setSortCriteria] = useState("createdAt");
  const [currentPage, setCurrentPage] = useState(0);

  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const navigate = useNavigate();

  const changeCategory = (category) => {
    setSelectedCategory(category);
    setCurrentPage(0);
  }

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
      setPosts((prevPosts) => 
        prevPosts.map((post) =>
          post.id == postId
            ? {...post, likeCount, liked} : post
        )
      );

      if(beforeLiked && pathname === "/posts/likes") window.location.reload();
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

  const loadMorePosts = useCallback(async (page) => {
    console.log('요청 page:', currentPage);
    if (loading || !hasMore || isFetchingRef.current) return;

    isFetchingRef.current = true;
    setLoading(true);

    try {
      const params = {
        page,
        size,
        criteria: sortCriteria,
        categoryId: selectedCategory.id,
      };
      let url = '/posts';
      if(pathname === "/posts/subscribe") url += '/sub';
      else if(pathname === "/posts/likes") url += '/liked';

      const res = await axios.get(url, { params });
      const newPosts = res.data.posts;
      if (!newPosts || !Array.isArray(newPosts)) {
        return;
      }
      console.log(res);
      setPosts((prev) => {
        const prevIds = new Set(prev.map(p => p.id));
        const uniqueNewPosts = newPosts.filter(p => !prevIds.has(p.id));
        return [...prev, ...uniqueNewPosts];
      });

      setHasMore(!res.data.last);
      setCurrentPage(page + 1);
    } catch (err) {
      console.error('포스트 로딩 오류:', err);
    } finally {
      isFetchingRef.current = false;

      setLoading(false);
    }
  }, [loading, hasMore, currentPage, sortCriteria, selectedCategory, pathname]);

  useEffect(() => {
    console.log(pathname);
    setPosts([]);
    setCurrentPage(0);
    setHasMore(true);
    isFetchingRef.current = false;
  }, [selectedCategory, sortCriteria, pathname]);

  useEffect(() => {
    if (currentPage === 0 && hasMore) {
      loadMorePosts(0);
    }
  }, [currentPage, hasMore]);

  // 무한 스크롤 이벤트 핸들러
  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.pageYOffset;
      const windowHeight = window.innerHeight;
      const documentHeight = document.documentElement.scrollHeight;
      
      if (scrollTop + windowHeight >= documentHeight - 1000 && !loading && hasMore) {
        loadMorePosts(currentPage);
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, [loadMorePosts, currentPage, loading, hasMore]);

  return (
    <>
      <DefaultLayout>
          <TabHeaderLyaout 
            categories={categories} 
            selectedCategory={selectedCategory} 
            changeCategory={changeCategory}
            type="feed"
          >
            <div className="feed-toolbar">
              <select
                className="sort-dropdown"
                value={sortCriteria}
                onChange={(e) => setSortCriteria(e.target.value)}
              >
                <option value="createdAt">최신순</option>
                <option value="likeCount">좋아요순</option>
                <option value="commentCount">댓글순</option>
              </select>
            </div>
            
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