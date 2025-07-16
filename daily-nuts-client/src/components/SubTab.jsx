import { useState } from 'react';
import { IoSearchOutline } from 'react-icons/io5';
import '../assets/css/default-layout.css'


function SubTab ({categories, selectedCategory, changeCategory}) {
      const [searchQuery, setSearchQuery] = useState('');

        const handleSearchChange = (e) => {
          setSearchQuery(e.target.value);
        };

        const handleSearchSubmit = (searchTerm) => {
          console.log('검색어:', searchTerm);
        };


    return (
        <div className="main-header">
          <div className="tabs">
            {
              categories.map(category => {
                return (
                  <div 
                    key={category.name}
                    className={`tab ${category.name === selectedCategory.name ? 'active' : ''}`}
                    onClick={() => changeCategory(category)}
                  > {category.name} </div>
                )}
              )
            }
          </div>

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
    )
}

export default SubTab;