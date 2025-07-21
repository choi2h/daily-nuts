import { IoMdHeartEmpty, IoMdHeart } from "react-icons/io";
import { GoComment } from "react-icons/go";
import defaultProfile from '../assets/images/default-profile.png';
import { useNavigate } from "react-router";
import axios from "axios";

function PostDetailItem({post, toggleLike, setPost, isAuthor, commentCount}) {
    const navigate = useNavigate();

    if (!post) return null;

    const onEditClick = () => {
        navigate(`/post/edit/${post.id}`);
    }

     const onDeleteClick = async () => {
        const confirmed = window.confirm("게시글을 삭제하시겠습니까?");
        if (!confirmed) return;

         try {
            const token = localStorage.getItem("token");

            await axios.delete(`/post/${post.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            alert("삭제가 완료되었습니다.");
            navigate("/");
        } catch (err) {
            console.error("삭제 실패:", err);
            alert("삭제에 실패했습니다.");
        }
    };

    return (
            <div>
                <div className="post-detail-header">
                    <span className="post-detail-category">{post.categoryName}</span>
                    <h1 className="post-detail-title">{post.title}</h1>
                </div>

                <div className="post-detail-meta">
                    <div className="author-info">
                        <div className="profile-avatar">
                            <img className="profile-image" src={defaultProfile} alt="Profile" />
                        </div>
                        <span className="author-name">{post.writer}</span>
                    </div>
                    <span className="post-date">{post.createdAt}</span>
                </div>

                {isAuthor && (
                    <div className="post-modify-actions">
                    <button className="action-button" onClick={onEditClick}>수정</button>
                    <button className="action-button" onClick={onDeleteClick}>삭제</button>
                    </div>
                )}

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
                    {post.liked ? <IoMdHeart size={24}/> : <IoMdHeartEmpty size={24}/>} 
                    <span>{post.likeCount ?? 0}</span>
                    </button>
                    <button 
                    className="action-btn"
                    >
                    <GoComment size={22} /> <span>{commentCount}</span>
                    </button>
                </div>
            </div>
    );
}

export default PostDetailItem;