import { useState } from 'react';
import '../assets/css/feed.css';
import PostItem from '../components/PostItem';
import DefaultLayout from '../layers/DefaultLayout';
import TabHeaderLyaout from '../layers/TabHeaderLayout';
import { useNavigate } from 'react-router';

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

const categories = [
  {
    name: "전체",
    type: "all"
  },
  {
    name: "스트레스", 
    type: "stress"
  },
  {
    name: "대인관계",
    type: "relationship"
  },
    {
    name: "자기이해",
    type: "self-understanding"
  },
    {
    name: "불안",
    type: "unrest"
  },
    {
    name: "기억",
    type: "memory"
  },
    {
    name: "기타",
    type: "other"
  },
]

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
  const [posts, setPosts] = useState(defaultPosts);
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const navigate = useNavigate();

  const changeCategory = (category) => {
    console.log(category.target);
    setSelectedCategory(category);
  }

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
              <div className="logo">하루건과</div>
              <div></div>
            </div>

            <div className="main-content">
              <div>
                {posts.map((post, idx) => 
                  <PostItem key={idx} post={post} toggleLike={toggleLike} onClick={postOnClick}/>
                  )}
              </div>
            </div>
          </TabHeaderLyaout>
        </DefaultLayout>
    </>
  );
};

export default FeedPage;
