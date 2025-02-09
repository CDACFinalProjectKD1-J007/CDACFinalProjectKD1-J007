import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../css/DocumentUpload.css";

function DocumentUpload() {
  const userId = localStorage.getItem("userId");
  const [files, setFiles] = useState({
    aadharCard: null,
    panCard: null,
    birthCertificate: null,
    a4Photo: null,
  });
  const [uploadedDocuments, setUploadedDocuments] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFiles({ ...files, [e.target.name]: e.target.files[0] });
  };

  const handleUpload = async (e) => {
    e.preventDefault();

    if (Object.values(files).every((file) => file === null)) {
      toast.error("Please select at least one document to upload!");
      return;
    }

    setLoading(true);

    try {
      const formData = new FormData();
      formData.append("userId", userId);

      for (const [key, file] of Object.entries(files)) {
        if (file) {
          formData.append(key, file);
        }
      }

      await axios.post("http://localhost:8080/api/documents/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      toast.success("Documents uploaded successfully!");
      fetchDocuments(); // Refresh documents after upload
    } catch (error) {
      console.error("Error uploading documents:", error);
      toast.error("Failed to upload documents. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  // ✅ Wrap fetchDocuments with useCallback to prevent re-creation
  const fetchDocuments = useCallback(async () => {
    if (!userId) {
      toast.error("User ID not found. Please log in again.");
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/documents/${userId}`);
      if (response.data && response.data.length > 0) {
        setUploadedDocuments(response.data[0]);
      } else {
        setUploadedDocuments(null);
      }
    } catch (error) {
      console.error("Error fetching documents:", error);
      toast.error("Failed to fetch documents.");
    } finally {
      setLoading(false);
    }
  }, [userId]); // ✅ Only re-create if userId changes

  // ✅ Now fetchDocuments is stable and can be added to useEffect safely
  useEffect(() => {
    fetchDocuments();
  }, [fetchDocuments]);

  return (
    <div className="document-upload-container">
      <h1>Upload & View Documents</h1>
      <h3>User ID: {userId}</h3>

      <h2>Upload Documents</h2>
      <form className="document-upload-form" onSubmit={handleUpload}>
        <label>Aadhar Card</label>
        <input type="file" name="aadharCard" onChange={handleFileChange} />

        <label>PAN Card</label>
        <input type="file" name="panCard" onChange={handleFileChange} />

        <label>Birth Certificate</label>
        <input type="file" name="birthCertificate" onChange={handleFileChange} />

        <label>A4 Size Photo</label>
        <input type="file" name="a4Photo" onChange={handleFileChange} />

        <button type="submit" disabled={loading || Object.values(files).every((file) => file === null)}>
          {loading ? "Uploading..." : "Upload/Update Documents"}
        </button>
      </form>

      <h2>View Uploaded Documents</h2>
      {uploadedDocuments ? (
        <div className="uploaded-documents">
          <h3>Uploaded Documents</h3>
          {Object.entries(uploadedDocuments)
            .filter(([key, url]) => key.includes("Url") && url)
            .map(([key, url]) => (
              <div className="document-preview" key={key}>
                <p>
                  <strong>{key.replace(/([A-Z])/g, " $1")}: </strong>
                  <a href={`http://localhost:8080${url}`} target="_blank" rel="noopener noreferrer">
                    View
                  </a>
                </p>
                <img src={`http://localhost:8080${url}`} alt={key} width="200" />
              </div>
            ))}
        </div>
      ) : (
        <p>No documents uploaded yet.</p>
      )}

      <ToastContainer />
    </div>
  );
}

export default DocumentUpload;
