import { BiReply } from "react-icons/bi";
import defaultProfile from '../assets/images/default-profile.png';

function ReplyItem({
    reply,
    currentMemberId,
    isEditing,
    editedContent,
    onEditClick,
    onEditChange,
    onEditSubmit,
    onDeleteClick
}) {
    const isAuthor = reply.authorId === currentMemberId;

  return (
    <div key={reply.id} className="reply-item">
      <div className="reply-indicator">
        <BiReply size={16} />
      </div>
      <div className="comment-avatar">
        <img src={defaultProfile} alt={reply.author} />
      </div>
      <div className="comment-content">
        <div className="comment-header">
          <div className="comment-header-info">
            <span className="comment-author">{reply.author}</span>
            <span className="comment-date">{reply.date}</span>
          </div>
          <div className="comment-actions">
            {isAuthor && !isEditing && (
          <>
            <button
              className="action-button"
              onClick={() => onEditClick(reply.id, reply.content)}
            >
              수정
            </button>
            <button
              className="action-button"
              onClick={() => onDeleteClick(reply.id)}
            >
              삭제
            </button>
          </>
        )}
          </div>
        </div>

        {isEditing ? (
          <form onSubmit={(e) => onEditSubmit(e, reply.id)}>
            <textarea
              value={editedContent[reply.id] || ''}
              onChange={(e) => onEditChange(e, reply.id)}
              className="reply-edit-input"
            />
            <button type="submit" className="reply-edit-submit">수정완료</button>
          </form>
        ) : (
          <div className="comment-text">{reply.content}</div>
        )}
      </div>
    </div>
  );
}

export default ReplyItem;