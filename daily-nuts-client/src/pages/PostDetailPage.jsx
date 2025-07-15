import { useEffect, useState } from 'react';
import '../assets/css/post-detail.css';
import '../assets/css/comment.css';
import { useLocation } from 'react-router';
import DefaultLayout from '../layers/DefaultLayout';
import PostDetailItem from '../components/PostDetailItem';
import ReplyItem from '../components/ReplyItem';
import CommentItem from '../components/CommentItem';

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

    const toggleLike = () => {
        console.log(toggleLike);
  };
    

    return (
        <DefaultLayout className="app">
            <div className="post-detail">
                <PostDetailItem post={post} toggleLike={toggleLike} />
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
                    <div className="comment-container">
                        {comments.map((comment) => (
                        <div key={comment.id} className="comment-thread">
                            {/* 메인 댓글 */}
                            <CommentItem comment={comment}/>

                            {/* 답글들 */}
                            {comment.replies && comment.replies.map((reply) => (
                                <ReplyItem reply={reply}/>
                            ))}
                        </div>
                        ))}
                    </div>
                </div>
            </div>
        </DefaultLayout>
    );
};

export default PostDetail;