import { useEffect, useState } from 'react';
import '../assets/css/post-detail.css';
import '../assets/css/comment.css';
import { useLocation } from 'react-router';
import DefaultLayout from '../layers/DefaultLayout';
import PostDetailItem from '../components/PostDetailItem';
import { IoArrowUndoOutline } from 'react-icons/io5';

const post = {
    category: "기타",
    title : "성격은 변하는가?",
    writer: "김00",
    createdDate: "2025.07.08",
    contents: `성격은 변하는가? 보통 동양사회에서 성격은 변할 수 있는 것으로 간주되고 반면 지능은 바꿀 수 없는 것으로 간주된다. 
            이러한 인식은 지능의 유전률이 성격의 유전률보다 낮다는 사실에 비춰볼 때 아이러니하다. 유전률을 제외하고 보더라도 
                    성격은 매우 안정적으로 유지된다. 845년간 진행된 네덜란드의 연구에서도 성격이 안정적으로 유지되어 발견되었으며 
                    잠노년층을 대상으로 6년간 진행한 종단 연구에서도 성격의 상대적 측면이 변하지 않음이 발견되었다. 인간의 성격은 
                    아동기에는 변화가 크나 청소년기부터 변화의 폭이 작아져 점차 고정된다`,
}

const testComments = [
    {
      id: 1,
      author: '작성자',
      date: '2025.08.09',
      content: '정말 공감됩니다..!',
      avatar: '/api/placeholder/40/40',
      isAuthor: true,
      replies: [
        {
          id: 2,
          author: '작성자',
          date: '2025.08.09',
          content: '정말 공감됩니다..!',
          avatar: '/api/placeholder/40/40',
          isAuthor: true,
          isReply: true
        }
      ]
    }
]

const PostDetail = () => {
    const {tab} = useLocation();
    const [comment, setComment] = useState('');
    const [likes, setLikes] = useState(16);
    const [isLiked, setIsLiked] = useState(false);
    const [comments, setComments] = useState(testComments);

    useEffect(() => {
        console.log(tab);
    })

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };

    const handleCommentSubmit = (e) => {
        e.preventDefault();
        if (comment.trim()) {
        console.log('댓글 등록:', comment);
        setComment('');
        // 여기에 실제 댓글 등록 로직을 추가하세요
        }
    };

    const handleLike = () => {
        setIsLiked(!isLiked);
        setLikes(isLiked ? likes - 1 : likes + 1);
    };
    

    return (
        <DefaultLayout className="app">
            <div className="post-detail">
                <PostDetailItem post={post} isLiked={isLiked} handleLike={handleLike}/>
                <div className="comment-section">
                    <div className="comment-title">댓글</div>
                    <form onSubmit={handleCommentSubmit} className="comment-form">
                    <textarea
                        className="comment-input"
                        value={comment}
                        onChange={handleCommentChange}
                        placeholder="답변을 남겨보세요 !"
                        rows="4"
                    />
                    <button type="submit" className="comment-submit">
                        등록
                    </button>
                    </form>

                {comments.map((comment) => (
                <div key={comment.id} className="comment-thread">
                    {/* 메인 댓글 */}
                    <div className="comment-item">
                    <div className="comment-avatar">
                        <img src={comment.avatar} alt={comment.author} />
                    </div>
                    <div className="comment-content">
                        <div className="comment-header-info">
                        <span className="comment-author">{comment.author}</span>
                        <span className="comment-date">{comment.date}</span>
                        </div>
                        <div className="comment-text">{comment.content}</div>
                        <div className="comment-actions">
                        <button className="reply-button">답글</button>
                        </div>
                    </div>
                    </div>

                    {/* 답글들 */}
                    {comment.replies && comment.replies.map((reply) => (
                    <div key={reply.id} className="reply-item">
                        <div className="reply-indicator">
                        <IoArrowUndoOutline size={16} />
                        </div>
                        <div className="comment-avatar">
                        <img src={reply.avatar} alt={reply.author} />
                        </div>
                        <div className="comment-content">
                        <div className="comment-header-info">
                            <span className="comment-author">{reply.author}</span>
                            <span className="comment-date">{reply.date}</span>
                        </div>
                        <div className="comment-text">{reply.content}</div>
                        <div className="comment-actions">
                            <button className="action-button">수정</button>
                            <button className="action-button">삭제</button>
                        </div>
                        </div>
                    </div>
                    ))}
                </div>
                ))}
                </div>
            </div>
        </DefaultLayout>
    );
};

export default PostDetail;