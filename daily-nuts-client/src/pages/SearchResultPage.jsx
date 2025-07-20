import { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import axios from 'axios';
import ExpertSearchItem from '../components/ExpertSearchItem';
import DefaultLayout from '../layers/DefaultLayout';
import TabHeaderLayout from '../layers/TabHeaderLayout';
import '../assets/css/SearchResult.css';

const categories = [
  { id: 0, name: '전체' },
  { id: 1, name: '스트레스' },
  { id: 2, name: '대인관계' },
  { id: 3, name: '자기이해' },
  { id: 4, name: '불안' },
  { id: 5, name: '기억' },
  { id: 6, name: '기타' },
];

function ExpertSearchResultPage() {
  const [experts, setExperts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const location = useLocation();

  const queryParams = new URLSearchParams(location.search);
  const searchQuery = queryParams.get('query');

  useEffect(() => {
    if (!searchQuery) {
      setExperts([]);
      setLoading(false);
      return;
    }

    const fetchExperts = async () => {
      try {
        const res = await axios.get('/member/search', {
          params: { name: searchQuery },
          withCredentials: true,
        });

        const data = res.data;
        const result = Array.isArray(data)
          ? data
          : Array.isArray(data?.experts)
          ? data.experts
          : data
          ? [data]
          : [];

        setExperts(result);
      } catch (err) {
        console.error('전문가 검색 실패:', err);
        setExperts([]);
      } finally {
        setLoading(false);
      }
    };

    fetchExperts();
  }, [searchQuery]);

  const changeCategory = (category) => {
    setSelectedCategory(category);
  };

  return (
    <DefaultLayout>
      <TabHeaderLayout
        categories={categories}
        selectedCategory={selectedCategory}
        changeCategory={changeCategory}
        type="feed"
      >
        <div className="main-content">
          <div className="subscribe-container">
            <div className="count-header">
              <span className="posts-label">검색 결과 수: </span>
              <span className="posts-count">{experts.length}</span>
            </div>

            {loading ? (
              <p className="loading-message">로딩 중...</p>
            ) : experts.length === 0 ? (
              <p className="no-result-message">검색 결과가 없습니다.</p>
            ) : (
              experts.map((expert, idx) => (
                <ExpertSearchItem key={idx} expert={expert} />
              ))
            )}
          </div>
        </div>
      </TabHeaderLayout>
    </DefaultLayout>
  );
}

export default ExpertSearchResultPage;
