import { useEffect, useState } from 'react';
import '../assets/css/PostDetail.css';
import { useLocation, useParams } from 'react-router';
import DefaultLayout from '../layers/DefaultLayout';
import PostDetailItem from '../components/PostDetailItem';
import ReplyItem from '../components/ReplyItem';
import CommentItem from '../components/CommentItem';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';
import axios from 'axios';

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
    const {id} = useParams();
    const [post, setPost] = useState(null);
    const {tab} = useLocation();
    const [comment, setComment] = useState('');
    const [comments, setComments] = useState(testComments);

    useEffect(() => {
        console.log("요청 ID:", id);
        axios.get(`/api/post/${id}`)
        .then((res) => {
            console.log("응답 데이터:", res.data);
            setPost(res.data);
        })
        .catch((err) => {
            console.error('게시글 불러오기 실패:', err);
        });
    }, [id]);

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
            {/* <div className="post-detail"> */}
            <BlankHeaderLayout>
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
                                <ReplyItem key={reply.id} reply={reply}/>
                            ))}
                        </div>
                        ))}
                    </div>
                </div>
            </BlankHeaderLayout>
            {/* </div> */}
        </DefaultLayout>
    );
};

export default PostDetail;