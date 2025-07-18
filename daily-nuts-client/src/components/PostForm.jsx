import { useState } from 'react';
import { IoChevronDown } from 'react-icons/io5';
import '../assets/css/PostWrite.css';
import { useNavigate } from 'react-router';
import axios from 'axios';

const PostForm = () => {
  const categories = [
    { id: 0, name: '전체' },
    { id: 1, name: '스트레스' },
    { id: 2, name: '대인관계' },
    { id: 3, name: '자기이해' },
    { id: 4, name: '불안' },
    { id: 5, name: '기억' },
    { id: 6, name: '기타' },
  ];

  const [category, setCategory] = useState(categories[0]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  //const categories = ['카테고리', '전체', '스트레스', '대인관계', '자기이해', '불안', '기억', '기타'];

  const navigate = useNavigate();

  const handleSubmit = async() => {
    if (!title.trim()) {
      alert('제목을 입력해주세요.');
      return;
    }

    if (!content.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }

    const categoryId = category.id;
    if (!category || category.id === 0) {
      alert('카테고리를 선택해주세요.');
      return;
    }

    try {
      const res = await axios.post('/post', {
        title, contents: content, categoryId,
      });

      const postId = res.data.id;
      navigate(`/post/${postId}`);
    } catch (err) {
      console.error('글 작성 실패:' + err);
      alert('글 작성에 실패했습니다.');
    }

    // console.log('게시글 제출:', { category, content });
    // setContent('');
    // setCategory('카테고리');
  };

  const handleCancel = () => {
    setContent('');
    setCategory(categories[0]);
    setIsDropdownOpen(false);
  };

  const selectCategory = (selectedCategory) => {
    setCategory(selectedCategory);
    setIsDropdownOpen(false);
  };

  return (
    <div className="post-creation-container">
      {/* 카테고리 선택 */}
      <div className="category-section">
        <div className="category-dropdown">
          <button 
            className="category-button" 
            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
          >
            <span>{category.name}</span>
            <IoChevronDown className={`chevron-icon ${isDropdownOpen ? 'open' : ''}`} />
          </button>
          
          {isDropdownOpen && (
            <div className="dropdown-menu">
              {categories.map((cat) => (
                <button
                  key={cat}
                  className="dropdown-item"
                  onClick={() => selectCategory(cat)}
                >
                  {cat.name}
                </button>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* 제목 */}
      <div className="title-section">
        <input
          className='title-input'
          type='text'
          placeholder='제목을 입력해주세요.'
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </div>

      {/* 내용 입력 */}
      <div className="content-section">
        <textarea 
          className="content-textarea"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="내용을 입력해주세요. 궁금한 것을 자유롭게 질문하세요"
        />
      </div>

      {/* 구분선 */}
      <div className="divider"></div>

      {/* 버튼 */}
      <div className="button-section">
        <button className="cancel-button" onClick={handleCancel}>
          취소하기
        </button>
        <button className="submit-button" onClick={handleSubmit}>
          작성하기
        </button>
      </div>
    </div>
  );
};

export default PostForm;