import '../assets/css/feed.css';
import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { GoComment } from "react-icons/go";

function PostItem({post}) {
    const isLiked = false;
    return (
        <article key={post.id} className="post">
              <div className="post-content">
                <div className="post-header">
                  <div className="post-author">
                    <div className="author-avatar">
                      {post.avatar}
                    </div>
                    <div className="author-info">
                      <span className="author-name">{post.author}</span>
                      <span className="post-time">· {post.time}</span>
                    </div>
                  </div>
                </div>
                
                <div className="post-body">
                  <div className="post-text">
                    <h2 className="post-title">{post.title}</h2>
                    <p className="post-excerpt">{post.content}</p>
                    
                    <div className="post-meta">
                      <div className="post-actions">
                        <button className="heart-btn">
                          {isLiked ? <IoMdHeart size={24}/> : <IoMdHeartEmpty size={24}/>}
                          <span className="like-count">2</span>
                        </button>
                        <button className="comment-btn">
                          <GoComment size={22} />
                          <span className="comment-count">3</span>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </article>
    );
}

export default PostItem;