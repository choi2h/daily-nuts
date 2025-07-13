import { useState } from 'react';
import '../assets/css/feed.css';
import PostItem from '../components/PostItem';
import DefaultLayout from '../layers/DefaultLayout';
import TabHeaderLyaout from '../layers/TabHeaderLayout';

const posts = [
  {
    id: 1,
    author: "Amit Das",
    time: "2025-07-11",
    avatar: "A",
    title: "Your portfolio is stopping you from getting that job",
    content: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
    category: "Portfolio",
    readTime: "3 min read",
    tag: "Selected for you",
    image: "/api/placeholder/400/300",
    hasImage: true
  },
  {
    id: 2,
    author: "Amit Das",
    time: "2025-07-11",
    avatar: "A",
    title: "Melody mobile app: a UI UX case study",
    content: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
    category: "UI ux Design",
    readTime: "3 min read",
    tag: "Selected for you",
    image: "/api/placeholder/400/300",
    hasImage: true
  },
  {
    id: 3,
    author: "Amit Das",
    time: "2025-07-09",
    avatar: "A",
    title: "Wellness app: a UI UX case study",
    content: "An intense way to learn about the process and practice your designs skills — My 1st hackathon Hackathons have been on my mind since I heard it was a good way to gain experience as a junior UX designer. As my portfolio...",
    category: "UI ux Design",
    readTime: "3 min read",
    tag: "Selected for you",
    image: "/api/placeholder/400/300",
    hasImage: true
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

const FeedPage = () => {
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);

  const changeCategory = (category) => {
    console.log(category.target);
    setSelectedCategory(category);
  }

  return (
    <DefaultLayout>
      <TabHeaderLyaout 
        categories={categories} 
        selectedCategory={selectedCategory} 
        changeCategory={changeCategory}
        type="feed"
      >
        <div>
            {posts.map(post => (
              <PostItem key={post.id} post={post}/>
            ))}
        </div>
      </TabHeaderLyaout>
    </DefaultLayout>
  );
};

export default FeedPage;