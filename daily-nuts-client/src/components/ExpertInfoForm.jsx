import { useState } from 'react';

const ExpertInfoForm = ({ initialData, onCancel, onSave }) => {
  const [formData, setFormData] = useState({
    description: initialData.description || '',
    files: initialData.files || [],
    previewUrls: []
  });

  const handleTextChange = (e) => {
    setFormData({ ...formData, description: e.target.value });
  };

  const handleFileUpload = (e) => {
    const uploadedFiles = Array.from(e.target.files);
    const newPreviewUrls = uploadedFiles.map((file) => URL.createObjectURL(file));
    setFormData((prev) => ({
      ...prev,
      files: [...prev.files, ...uploadedFiles],
      previewUrls: [...prev.previewUrls, ...newPreviewUrls]
    }));
  };

  const removeFile = (index) => {
    const updatedFiles = formData.files.filter((_, i) => i !== index);
    setFormData({ ...formData, files: updatedFiles });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.description) {
      alert('증명내용을 입력해주세요.');
      return;
    }
    if (!formData.files.length) {
      alert('증명 파일을 추가해주세요.');
      return;
    }
    onSave(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="registration-form">
      <div className="form-section">
        <h3 className="section-title">프로필 설명</h3>
        <textarea
          className="profile-textarea"
          value={formData.description}
          onChange={handleTextChange}
          placeholder="프로필 설명을 입력하세요"
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
        <button type="button" onClick={onCancel} className="cancel-button">
          취소
        </button>
      </div>
    </form>
  );
};

export default ExpertInfoForm;