import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import axios from 'axios';
import PostForm from '../components/PostForm';
import DefaultLayout from '../layers/DefaultLayout';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';

const PostEditPage = () => {
  const { id } = useParams();
  const [post, setPost] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    axios.get(`/post/${id}`, {
      headers: { Authorization: `Bearer ${token}` }
    }).then(res => {
      setPost(res.data);
    }).catch(err => {
      console.error("게시글 불러오기 실패", err);
      alert("글을 불러오지 못했습니다.");
    });
  }, [id]);

  return (
    <DefaultLayout className="app">
      <BlankHeaderLayout title="글 수정" type="card">
        <PostForm initialData={post} isEditMode={true} />
      </BlankHeaderLayout>
    </DefaultLayout>
  );
};

export default PostEditPage;