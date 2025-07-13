import React, { useState } from 'react';
import { IoArrowBack } from 'react-icons/io5';
import { IoSearchOutline } from 'react-icons/io5';
import './TabBarWithSearch.css';

function BasicHeader() {
  const [searchQuery, setSearchQuery] = useState('');

  const handleBackClick = () => {
    console.log('뒤로가기 클릭');
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchSubmit = (searchTerm) => {
    console.log('검색어:', searchTerm);
  };

  return (
    <div className="tab-bar-container">
      <div className="tab-bar-header">
        {/* 뒤로가기 버튼 */}
        <button
          onClick={handleBackClick}
          className="back-button"
        >
          <IoArrowBack size={20} />
        </button>

        {/* 빈 공간 */}
        <div className="spacer"></div>

        {/* 검색창 */}
        <div className="search-container">
          <div className="search-input-container">
            <IoSearchOutline className="search-icon" size={16} />
            <input
              type="text"
              value={searchQuery}
              onChange={handleSearchChange}
              placeholder="검색"
              className="search-input"
              onKeyPress={(e) => {
                if (e.key === 'Enter') {
                  handleSearchSubmit(searchQuery);
                }
              }}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default BasicHeader;