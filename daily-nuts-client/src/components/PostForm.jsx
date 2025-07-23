import { useState, useEffect } from 'react';
import { IoChevronDown } from 'react-icons/io5';
import '../assets/css/PostWrite.css';
import { useNavigate } from 'react-router';
import axios from 'axios';

const PostForm = ({ initialData = null, isEditMode = false }) => {
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

  const navigate = useNavigate();

  useEffect(() => {
    if (initialData) {
      setTitle(initialData.title);
      setContent(initialData.contents);
      const matched = categories.find(c => c.name === initialData.categoryName);
      if (matched) setCategory(matched);
    }
  }, [initialData]);

  const handleSubmit = async() => {
    if (!title.trim()) {
      alert('제목을 입력해주세요.');
      return;
    }

    if (!content.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }
    
    if (!category || category.id === 0) {
      alert('카테고리를 선택해주세요.');
      return;
    }

    const token = localStorage.getItem("accessToken");
    const requestBody = {
      title,
      contents: content,
      categoryId: category.id,
    };

    try {
     if (isEditMode) {
        await axios.put(`/post/${initialData.id}`, requestBody, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        alert("수정 완료");
        navigate(`/post/${initialData.id}`);
      } else {
        const res = await axios.post('/post', requestBody, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        navigate(`/post/${res.data.id}`);
      }
    } catch (err) {
      alert(`글 ${isEditMode ? '수정' : '작성'} 실패`);
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate(-1);
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
          {isEditMode ? '수정하기' : '작성하기'}
        </button>
      </div>
    </div>
  );
};

export default PostForm;