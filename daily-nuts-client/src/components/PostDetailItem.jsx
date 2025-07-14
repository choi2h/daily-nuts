import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { GoComment } from "react-icons/go";

function PostDetailItem({post, isLiked, handleLike}) {
    return (
            <div>
                <div className="post-detail-header">
                    <span className="post-detail-category">{post.category}</span>
                    <h1 className="post-detail-title">{post.title}</h1>
                </div>

                <div className="post-detail-meta">
                    <div className="author-info">
                    <img 
                        src="/api/placeholder/40/40" 
                        alt="Author"
                        className="author-avatar"
                    />
                    <span className="author-name">{post.writer}</span>
                    </div>
                    <span className="post-detail-date">{post.createdDate}</span>
                </div>

                <div className="post-detail-content">
                    <p>
                        {post.contents}
                    </p>
                </div>

                <div className="post-actions">
                    <button className="heart-btn">
                        {isLiked ? <IoMdHeart size={24}/> : <IoMdHeartEmpty size={24}
                        onClick={handleLike}/>}
                        <span className="like-count">2</span>
                    </button>
                    <button className="comment-btn">
                        <GoComment size={22} />
                        <span className="comment-count">3</span>
                    </button>
                </div>
            </div>
    );
}

export default PostDetailItem;