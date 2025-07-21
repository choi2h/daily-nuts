import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { GoComment } from "react-icons/go";
import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/Feed.css';

function PostItem({post, toggleLike, onClick}) {
  const handleCardClick = () => {
    onClick(post.id);
  };

  const handleLikeClick = (e) => {
    e.stopPropagation();
    console.log("liked 상태:", post.liked);
    toggleLike(post.id, post.liked);
  };

    return (
         <div className="post" onClick={handleCardClick}>
          <div className="post-header">
            <div className="post-avatar">
                <div className="profile-avatar">
                    <img className="profile-image" src={defaultProfile} alt="Profile" />
                </div>
                <div className="author-info">
                    <span className="author-name">{post.writer}</span>
                    <span className="post-date">{post.time}</span>
                </div>
            </div>
          </div>
          
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
    );
}

export default PostItem;