import { BiReply } from "react-icons/bi";
import defaultProfile from '../assets/images/default-profile.png';

function ReplyItem({reply}) {
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
                        <button className="action-button">수정</button>
                        <button className="action-button">삭제</button>
                    </div>
                </div>
                <div className="comment-text">{reply.content}</div>
            </div>
        </div>
    );
}

export default ReplyItem;