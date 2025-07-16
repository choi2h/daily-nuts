import { useState } from 'react';
import { IoChevronDown } from 'react-icons/io5';
import '../assets/css/post-write.css';

const PostForm = () => {
  const [category, setCategory] = useState('카테고리');
  const [content, setContent] = useState('');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const categories = ['카테고리', '전체', '스트레스', '대인관계', '자기이해', '불안', '기억', '기타'];

  const handleSubmit = () => {
    if (!content.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }
    console.log('게시글 제출:', { category, content });
    setContent('');
    setCategory('카테고리');
  };

  const handleCancel = () => {
    setContent('');
    setCategory('카테고리');
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
            <span>{category}</span>
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
                  {cat}
                </button>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* 제목 */}
      <div className="title-section">
        <h2 className="title-text">제목을 입력하세요</h2>
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