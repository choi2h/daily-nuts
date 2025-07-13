import { useState } from 'react';
import '../assets/css/expert-info.css';

const ExpertInfo = () => {
  const [formData, setFormData] = useState({
    profileDescription: '',
    files: []
  });

  const handleTextChange = (e) => {
    setFormData({
      ...formData,
      profileDescription: e.target.value
    });
  };

  const handleFileUpload = (e) => {
    const uploadedFiles = Array.from(e.target.files);
    setFormData({
      ...formData,
      files: [...formData.files, ...uploadedFiles]
    });
  };

  const removeFile = (index) => {
    const updatedFiles = formData.files.filter((_, i) => i !== index);
    setFormData({
      ...formData,
      files: updatedFiles
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('전문가 등록 정보:', formData);
    // 여기에 실제 제출 로직을 추가하세요
  };

  return (
    <div className="expert-registration">
      <form onSubmit={handleSubmit} className="registration-form">
        <div className="form-section">
          <h3 className="section-title">프로필 설명</h3>
          <textarea
            className="profile-textarea"
            value={formData.profileDescription}
            onChange={handleTextChange}
            placeholder="전문가로서의 역할 및 경력을 간단히 기술하고 본인의 전문성에 대한 설명을 작성해 주세요.
- 서비스 제공 분야에 대한 전문성 및 경험 기술
- 자격증의 전문성을 발휘 가능성, 주요한 국내외 학술 논문 및 기타 저술 성과에 대한 경험을 간단히 기술하고 본인의 전문성에 대한 설명을 다음과 같이 작성해 주세요.
대화형의 방식, 기법 및 방법론을 자세히 설명해 주세요."
            rows="8"
          />
        </div>

        <div className="form-section">
          <h3 className="section-title">첨부자료</h3>
          
          <div className="file-upload-section">
            <label htmlFor="file-upload" className="file-upload-label">
              <div className="upload-icon">📁</div>
              파일 선택
            </label>
            <input
              id="file-upload"
              type="file"
              multiple
              accept=".jpg,.jpeg,.png,.pdf,.doc,.docx"
              onChange={handleFileUpload}
              className="file-input"
            />
          </div>

          <div className="file-list">
            {formData.files.map((file, index) => (
              <div key={index} className="file-item">
                <div className="file-info">
                  <span className="file-icon">📄</span>
                  <span className="file-name">{file.name}</span>
                </div>
                <button
                  type="button"
                  onClick={() => removeFile(index)}
                  className="remove-file-btn"
                >
                  ×
                </button>
              </div>
            ))}
            
            {/* 기본 파일 목록 (예시) */}
            <div className="file-item">
              <div className="file-info">
                <span className="file-icon">📄</span>
                <span className="file-name">Search_3c.jpg</span>
              </div>
              <button type="button" className="remove-file-btn">×</button>
            </div>
            <div className="file-item">
              <div className="file-info">
                <span className="file-icon">📄</span>
                <span className="file-name">Search_3c.jpg</span>
              </div>
              <button type="button" className="remove-file-btn">×</button>
            </div>
            <div className="file-item">
              <div className="file-info">
                <span className="file-icon">📄</span>
                <span className="file-name">Search_3c.jpg</span>
              </div>
              <button type="button" className="remove-file-btn">×</button>
            </div>
          </div>
        </div>

        <button type="submit" className="submit-button">
          수정하기
        </button>
      </form>
    </div>
  );
};

export default ExpertInfo;