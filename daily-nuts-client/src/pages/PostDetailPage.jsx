import { useEffect, useState } from 'react';
import '../assets/css/PostDetail.css';
import { useNavigate, useParams } from 'react-router';
import DefaultLayout from '../layers/DefaultLayout';
import PostDetailItem from '../components/PostDetailItem';
import ReplyItem from '../components/ReplyItem';
import CommentItem from '../components/CommentItem';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';
import {addLike, cancleLike} from '../service/PostLikeService';
import axios, { HttpStatusCode } from 'axios';

const convertCommentData = (commentsFromServer,myMemberId) => {
  return commentsFromServer.map(comment => ({
    id: comment.id,
    author: comment.writer,
    authorId: comment.memberId,  
    date: comment.createdAt.substring(0, 10).replace(/-/g, '.'),
    content: comment.contents,
    avatar: '/api/placeholder/40/40',
    isAuthor: comment.memberId === myMemberId,
    replies: comment.replies?.map(reply => ({
      id: reply.id,
      author: reply.writer,
      authorId: reply.memberId,
      date: reply.createdAt.substring(0, 10).replace(/-/g, '.'),
      content: reply.contents,
      avatar: '/api/placeholder/40/40',
      isAuthor: comment.memberId === myMemberId,
      isReply: true,
      parentId: comment.id,
    })) || [],
    isReplyOpen: false,
    replyInput: '',
  }));
};

const PostDetail = () => {
  const navigate = useNavigate();
  const { id: postId } = useParams();
  const [post, setPost] = useState(null);
  const [myMemberId, setMyMemberId] = useState(null);
  const [comment, setComment] = useState(''); 
  const [comments, setComments] = useState([]);
  const [editingCommentId, setEditingCommentId] = useState(null); //댓글 수정  
  const [editedContent, setEditedContent] = useState({});//댓글 수정
  

  useEffect(() => {
    const storedId = localStorage.getItem("memberId");
    if (storedId) {
      setMyMemberId(parseInt(storedId, 10));
    }
  }, []);
 
  // 게시글 상세 정보
  useEffect(() => {
    const fetchPost = async () => {
      try {
        const res = await axios.get(`/post/${postId}`);
        console.log(res.data);
        setPost(res.data);
      } catch (err) {
        if (err.response?.status === 403) {
          alert("구독하지 않은 전문가의 글입니다.");
          navigate(`/`);
        } else {
          console.error('게시글 불러오기 실패:', err);
        }
      }
    };

    fetchPost();
  }, [postId, navigate]);

    const toggleLike = async () => {
          console.log(post);
          if (!post) return;
          console.log(post.liked);
          try {
              if (post.liked) {
                handleCancleLike();
              } else {
                handleAddLike();
              }
          } catch (err) {
              console.error("좋아요 처리 실패:", err);
          }};

    const handleAddLike = () => {
      addLike(post.id).then((res) => {
        if(res.status === HttpStatusCode.Created) {
            setPost(prev => ({
              ...prev,
              liked: true,
              likeCount: res.data.likeCount
            }));
        } else {
          console.log(res.data.message);
          alert(res.data.message);
        }
      }).catch((err) => {
        console.log(err);
      })
    }

    const handleCancleLike = () => {
      console.log('cancle like!!');
      cancleLike(post.id).then((res) => {
        console.log(res.data);
        if(res.status === HttpStatusCode.Created) {
            setPost(prev => ({
              ...prev,
              liked: true,
              likeCount: res.data.likeCount
            }));
        } else {
          console.log(res.data.message);
        }
      }).catch((err) => {
        console.log(err);
      })
    }

    const fetchComments = async () => {
        try {
        const res = await axios.get(`http://localhost:8081/post/${postId}/comments`);
        const formattedComments = convertCommentData(res.data.comments, myMemberId);

        setComments(formattedComments);
        } catch (err) {
        console.error('댓글 목록 가져오기 실패:', err);
        }
    };

  useEffect(() => {
    fetchComments();
  }, [postId]);

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };
  //댓글 등록 
    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        if (comment.trim()) {
        try {
            await axios.post(
            `http://localhost:8081/post/${postId}/comment`,
            {
              contents: comment,
              memberId: myMemberId
            }
            );
            setComment('');
            await fetchComments();
        } catch (error) {
            console.error('댓글 등록 실패:', error);
        }
        }
    };

  // 대댓글 입력창 토글
    const toggleReplyInput = (parentId) => {
        setComments(prev =>
        prev.map(comment =>
            comment.id === parentId
            ? { ...comment, isReplyOpen: !comment.isReplyOpen }
            : comment
        )
        );
    };

  // 대댓글 입력 내용 변경
    const handleReplyChange = (parentId, value) => {
        setComments(prev =>
        prev.map(comment =>
            comment.id === parentId ? { ...comment, replyInput: value } : comment
        )
        );
    };

  // 대댓글 등록
    const handleReplySubmit = async (parentId) => {
        const parentComment = comments.find(c => c.id === parentId);
        const replyContent = parentComment?.replyInput;

        if (!replyContent?.trim()) return;

        try {
        await axios.post(
            `http://localhost:8081/post/${postId}/comment/${parentId}/reply`,
            {
            contents: replyContent,
            memberId: myMemberId,
            }
        );
        await fetchComments();
        } catch (err) {
        console.error('대댓글 등록 실패:', err);
        }
    };
    
    // 수정 버튼 클릭 시
    const handleEditClick = (commentId, currentContent) => {
        setEditingCommentId(commentId);
        setEditedContent(prev => ({ ...prev, [commentId]: currentContent }));
        };
    // 수정 내용 변경 시
    const handleEditChange = (e, commentId) => {
    const value = e.target.value;
    setEditedContent(prev => ({ ...prev, [commentId]: value }));
    };
    
    // 수정 완료
    const handleEditSubmit = async (e, commentId) => {
    e.preventDefault();
    const content = editedContent[commentId];

    if (!content || !content.trim()) {
        alert("수정 내용을 입력하세요");
        return;
    }

    try {
        await axios.put(`http://localhost:8081/post/${postId}/comment/${commentId}`, {
        contents: content,
        memberId: myMemberId // 필요시 수정
        });

        await fetchComments();
        setEditingCommentId(null);

        setEditedContent(prev => {
        const updated = { ...prev };
        delete updated[commentId];
        return updated;
        });
    } catch (error) {
        console.error("댓글 수정 실패", error);
    }
    };

    // 댓글 삭제
    const handleDeleteClick = async (commentId) => {
    const currentMemberId = 1; // 실제 로그인된 ID로 교체하세요

    if (!window.confirm('댓글을 삭제하시겠습니까?')) return;

    console.log('삭제 요청, commentId:', commentId, 'memberId:', currentMemberId);

    try {
        const res = await axios.delete(`http://localhost:8081/post/${postId}/comment/${commentId}`, {
        params: { memberId: currentMemberId }
        });
        console.log('삭제 성공:', res.data);
        await fetchComments();
    } catch (error) {
        console.error('댓글 삭제 실패:', error.response || error.message);
    }
    };

  return (
    <DefaultLayout className="app">
      <BlankHeaderLayout>
        <PostDetailItem post={post} toggleLike={toggleLike} 
        isAuthor={post?.memberId === myMemberId} commentCount={comments.length}/>
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
                <CommentItem
                    key={comment.id}
                        comment={comment}
                          currentMemberId={myMemberId}

                    onReplyClick={() => toggleReplyInput(comment.id)}
                    onEditClick={handleEditClick}
                    isEditing={editingCommentId === comment.id}
                    editedContent={editedContent}
                    onEditChange={handleEditChange}
                    onEditSubmit={handleEditSubmit}
                    onDeleteClick={handleDeleteClick}
                />
                {/* 대댓글 입력창 */}
                {comment.isReplyOpen && (
                  <div className="reply-input-section">
                    <textarea
                      value={comment.replyInput || ''}
                      onChange={(e) => handleReplyChange(comment.id, e.target.value)}
                      placeholder="답글을 입력하세요"
                      className="reply-input"
                    />
                    <button
                      onClick={() => handleReplySubmit(comment.id)}
                      className="reply-submit"
                    >
                      등록
                    </button>
                  </div>
                )}
                {/* 대댓글 목록 */}
                {comment.replies.map(reply => (
                <ReplyItem 
                    key={reply.id} 
                    reply={reply}
                    currentMemberId={myMemberId}
                    isEditing={editingCommentId === reply.id}
                    editedContent={editedContent}
                    onEditClick={handleEditClick}
                    onEditChange={handleEditChange}
                    onEditSubmit={handleEditSubmit}
                    onDeleteClick={handleDeleteClick}
                />
                ))}
                </div>
            ))}
          </div>
        </div>
      </BlankHeaderLayout>
    </DefaultLayout>
  );
};

export default PostDetail;