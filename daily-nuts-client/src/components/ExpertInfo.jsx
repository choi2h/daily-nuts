import { useEffect, useState } from 'react';
import { saveExpertInfo, getExpertInfo } from '../service/ExpertInfoService';
import '../assets/css/ExpertInfo.css';

const info = {
  description : ``,
  certifications : [
  ]
}

const ExpertInfo = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    description: info.description,
    files: info.certifications,         // 실제 파일
    previewUrls: []    // 미리보기 URL
  });

const handleFileUpload = (e) => {
  const uploadedFiles = Array.from(e.target.files);
  const newPreviewUrls = uploadedFiles.map(file => URL.createObjectURL(file));

  setFormData(prev => ({
    ...prev,
    files: [...prev.files, ...uploadedFiles],
    previewUrls: [...prev.previewUrls, ...newPreviewUrls]
  }));
};

  const handleTextChange = (e) => {
    setFormData({
      ...formData,
      description: e.target.value
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
    console.log(formData.description)
    if(!formData.description) {
      alert("증명내용을 입력해주세요.");
      return;
    }
    console.log(formData.files);
    if(!formData.files.length) {
      alert("증명 파일을 추가해주세요.");
      return;
    }
    console.log('전문가 등록 정보:', formData);

    saveExpertInfo(formData.description, formData.files)
      .then((res) => {
        console.log(res);
        setIsEditing(false)
      }
    );
  };

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleCancel = () => {
    setIsEditing(false);
    setFormData({
      profileDescription: info.description,
      files: []
    });
  };

    // 컴포넌트 언마운트 시 메모리 해제
  useEffect(() => {
    return () => {
      getExpertInfo().then((res) => {
        console.log(res);
      })

      if(formData.previewUrls) formData.previewUrls.forEach((url) => URL.revokeObjectURL(url));
    };
  }, [formData.previewUrls]);

  return (
    <div className="profile-card">
      <h2 className="page-title">전문가 등록 정보</h2>
      
      {!isEditing ? (
        // 조회 화면
        <div className="info-view">
          <div className="info-section">
            <h3 className="section-title">프로필 설명</h3>
            <div className="profile-description">
              {info.description}
            </div>
          </div>

          <div className="info-section">
            <h3 className="section-title">첨부자료</h3>
            <div className="file-list">
              {info.certifications.map((cert, index) => (
                <div key={index} className="file-item-view">
                  <div className="file-info">
                    <span className="file-icon">📄</span>
                    <span className="file-name">{cert.name}</span>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <button onClick={handleEdit} className="edit-button">
            수정하기
          </button>
        </div>
      ) : (
        // 수정 화면
        <form onSubmit={handleSubmit} className="registration-form">
          <div className="form-section">
            <h3 className="section-title">프로필 설명</h3>
            <textarea
              className="profile-textarea"
              value={formData.profileDescription}
              onChange={handleTextChange}
              placeholder={info.description}
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

            <div className="preview-container">
              {formData.previewUrls.map((url, index) => (
                <img key={index} src={url} alt={`preview-${index}`} className="preview-image" />
              ))}
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
            </div>
          </div>

          <div className="button-group">
            <button type="submit" className="submit-button">
              저장하기
            </button>
            <button type="button" onClick={handleCancel} className="cancel-button">
              취소
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default ExpertInfo;