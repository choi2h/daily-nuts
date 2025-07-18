import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/Comment.css';


function CommentItem({
    comment,
    currentMemberId,
  onReplyClick,
  onEditClick,
    onDeleteClick,
    onEditChange,
  setEditedContent,
  isEditing,
  editedContent,
  onEditSubmit
}) {
    const isAuthor = comment.authorId === currentMemberId;
  return (
      <div className="comment-item">
           {/* 프로필 */}
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
            {isAuthor && !isEditing && (
          <>
            <button className="action-button" onClick={() => onEditClick(comment.id, comment.content)}>수정</button>
            <button className="action-button" onClick={() => onDeleteClick(comment.id)}>삭제</button>
          </>
        )}
          </div>
        </div>

        {isEditing ? (
            <form onSubmit={(e) => onEditSubmit(e, comment.id)}>
                <textarea
                value={editedContent[comment.id] || comment.content}
                onChange={(e) => onEditChange(e, comment.id)}  
                className="comment-edit-input"
                />

                <button type="submit" className="comment-edit-submit">수정 완료</button>
            </form>
            ) : (
            <div className="comment-text">{comment.content}</div>
            )}


        <div className="comment-actions">
          <button className="reply-button" onClick={() => onReplyClick(comment.id)}>답글달기</button>
        </div>
      </div>
    </div>
  );
}


export default CommentItem;