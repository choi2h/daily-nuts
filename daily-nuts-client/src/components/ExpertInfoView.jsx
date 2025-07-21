const ExpertInfoView = ({ info, onEdit }) => {
  return (
    <div className="info-view">
      <div className="info-section">
        <h3 className="section-title">프로필 설명</h3>
        <div className="profile-description">{info.description}</div>
      </div>

      <div className="info-section">
        <h3 className="section-title">첨부자료</h3>
        <div className="file-list">
          {!info.filesinfo ? 
          info.files.map((cert, index) => (
            <div key={index} className="file-item-view">
              <div className="file-info">
                <span className="file-icon">📄</span>
                <span className="file-name">{cert.name}</span>
              </div>
            </div>
          )) : ''}
        </div>
      </div>

      <button onClick={onEdit} className="edit-button">
        수정하기
      </button>
    </div>
  );
};

export default ExpertInfoView;