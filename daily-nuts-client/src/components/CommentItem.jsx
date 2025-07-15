import defaultProfile from '../assets/images/default-profile.png';

function CommentItem({comment}) {
    return (
        <div className="comment-item">
            <div className="comment-avatar">
                <img src={defaultProfile} alt={comment.author} />
            </div>
            <div className="comment-content">
                <div className="comment-header">
                    <div className="comment-header-info">
                        <span className="comment-author">{comment.author}</span>
                        <span className="comment-date">{comment.date}</span>
                    </div>
                    <div className="comment-actions">
                        <button className="action-button">수정</button>
                        <button className="action-button">삭제</button>
                    </div>
                </div>
                <div className="comment-text">{comment.content}</div>
                    <div className="comment-actions">
                    <button className="reply-button">답글달기</button>
                </div>
            </div>
        </div>
    );
}

export default CommentItem;