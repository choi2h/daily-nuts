import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { GoComment } from "react-icons/go";
import defaultProfile from '../assets/images/default-profile.png';

function PostDetailItem({post, toggleLike}) {
    return (
            <div>
                <div className="post-detail-header">
                    <span className="post-detail-category">{post.category}</span>
                    <h1 className="post-detail-title">{post.title}</h1>
                </div>

                <div className="post-detail-meta">
                    <div className="author-info">
                        <div className="profile-avatar">
                            <img className="profile-image" src={defaultProfile} alt="Profile" />
                        </div>
                        <span className="author-name">{post.writer}</span>
                    </div>
                    <span className="post-date">{post.createdDate}</span>
                </div>

                <div className="post-detail-content">
                    <p>
                        {post.contents}
                    </p>
                </div>

                <div className="post-actions">
                    <button 
                    className={`action-btn ${post.liked ? 'liked' : ''}`}
                    onClick={() => toggleLike(post.id)}
                    >
                    {post.liked ? <IoMdHeart size={24}/> : <IoMdHeartEmpty size={24}/>} <span>3</span>
                    </button>
                    <button 
                    className="action-btn"
                    >
                    <GoComment size={22} /> <span>3</span>
                    </button>
                </div>
            </div>
    );
}

export default PostDetailItem;