import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { FaThumbtack } from "react-icons/fa";
import { GoComment } from "react-icons/go";
import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/Feed.css';
import { useNavigate } from "react-router";
import axios from "axios";

function PostItem({post, toggleLike, onClick}) {
  const navigate = useNavigate();

  const handleCardClick = async () => {
    try {
      await axios.get(`/post/${post.id}`);
      navigate(`/post/${post.id}`);
    } catch (err) {
      if (err.response?.status === 403) {
        alert('구독자만 볼 수 있는 게시글입니다.');
      } else if (err.response?.status === 401) {
        alert('로그인이 필요합니다.');
      } else {
        alert('게시글에 접근할 수 없습니다.');
      }
    }
  };

  const handleLikeClick = (e) => {
    e.stopPropagation();
    console.log("liked 상태:", post);
    toggleLike(post.id, post.liked);
  };

  const moveProfile = () => {
    navigate(`/profile/${post.memberId}`);
  }

  const getDateFormat = (dateStr) => {
    const date = new Date(dateStr);

    return date.toLocaleDateString("ko-KR", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit"
    }).replace(/\./g, "").trim().replace(/\s+/g, "-");
  }

  return (
      <div className="post" >
        <div className="post-header">
          <div className="post-avatar" onClick={moveProfile}>
              <img className="post-image" src={defaultProfile} alt="Profile" />
              <div className="author-info">
                  <span className="author-name">{post.writer}</span>
                  <span className="post-date">· {getDateFormat(post.createdAt)}</span>
              </div>
          </div>
        </div>
        
        <div onClick={handleCardClick}>
          <div className="post-title">
            {post.pinned && (
              <FaThumbtack
                size={14}
                style={{ marginRight: '6px', color: '#f39c12', verticalAlign: 'middle' }}
              />
          )}
          {post.title}
          </div>
          <div className="post-excerpt">{post.contents}</div>
          
          <div className="post-actions">
            <button 
              className={`action-btn ${post.liked ? 'liked' : ''}`}
              onClick={handleLikeClick}
            >
              {post.liked ? <IoMdHeart size={24}/> : <IoMdHeartEmpty size={24}/>} 
              <span>{post.likeCount ?? 0}</span>
            </button>
            <button 
              className="action-btn"
              onClick={() => onClick(post.id)}
            >
              <GoComment size={22} /> <span>{post.commentCount}</span>
            </button>
          </div>
        </div>
      </div>
  );
}

export default PostItem;