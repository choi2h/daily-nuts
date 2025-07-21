import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { GoComment } from "react-icons/go";
import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/Feed.css';
import { useNavigate } from "react-router";

function PostItem({post, toggleLike, onClick}) {
  const navigate = useNavigate();

  const handleCardClick = () => {
    onClick(post.id);
  };

  const handleLikeClick = (e) => {
    e.stopPropagation();
    console.log("liked 상태:", post.liked);
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
          <div className="post-title">{post.title}</div>
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
              <GoComment size={22} /> <span>3</span>
            </button>
          </div>
        </div>
      </div>
  );
}

export default PostItem;