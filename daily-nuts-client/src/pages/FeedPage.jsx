import { useEffect, useState } from 'react';
import '../assets/css/feed.css';
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
//   }
// ];

const categories = [
    { id: 0, name: '전체' },
    { id: 1, name: '스트레스' },
    { id: 2, name: '대인관계' },
    { id: 3, name: '자기이해' },
    { id: 4, name: '불안' },
    { id: 5, name: '기억' },
    { id: 6, name: '기타' },
  ];

// const FeedPage = () => {


//   return (
//     <DefaultLayout>
//       <TabHeaderLyaout 
//         categories={categories} 
//         selectedCategory={selectedCategory} 
//         changeCategory={changeCategory}
//         type="feed"
//       >
        // <div>
        //     {posts.map(post => (
        //       <PostItem key={post.id} post={post}/>
        //     ))}
        // </div>
//       </TabHeaderLyaout>
//     </DefaultLayout>
//   );
// };

// export default FeedPage;

const FeedPage = () => {
  const [posts, setPosts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const [sortCriteria, setSortCriteria] = useState("createdAt");
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchPosts = async () => {
      const params = {
        page: currentPage,
        criteria: sortCriteria,
      };
      if (selectedCategory.id != 0) {
        params.categoryId = selectedCategory.id;
      }

      try {
        const res = await axios.get('/api/posts', {params});
        console.log("응답 데이터 확인:", res.data);
        setPosts(res.data.content || []);
        setTotalPages(res.data.totalPages);
      } catch (err) {
        console.error('글 목록 못 불러옴:', err);
        setPosts([]);
      }
    }
    fetchPosts();
  }, [selectedCategory, sortCriteria, currentPage]);

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

  return (
    <>
      <DefaultLayout>
        <TabHeaderLyaout 
          categories={categories} 
          selectedCategory={selectedCategory} 
          changeCategory={changeCategory}
          type="feed"
        >
            {/* 모바일 헤더 */}
            <div className="mobile-header">
              <div className="logo">하루견과</div>
              <div></div>
            </div>

            <div className="main-content">
              <div>
                {posts.map((post, idx) => 
                  <PostItem key={idx} post={post} toggleLike={toggleLike} onClick={postOnClick}/>
                  )}
              </div>
            </div>

            <div className="pagination">
              {Array.from({ length: totalPages }, (_, i) => (
                <button
                  key={i}
                  onClick={() => setCurrentPage(i)}
                  className={currentPage === i ? 'active' : ''}
                >
                  {i + 1}
                </button>
              ))}
            </div>
          </TabHeaderLyaout>
        </DefaultLayout>
    </>
  );
};

export default FeedPage;
