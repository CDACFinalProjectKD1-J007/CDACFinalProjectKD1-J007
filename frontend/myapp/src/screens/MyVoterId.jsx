import voterIdImage from '../images/download.jpg';
import './css/VoterIDPage.css'; 

function VoterIDPage() {

  return (
    <div className="voter-id-container">
      <h1>Your Voter ID</h1>
      <img src={voterIdImage} alt="Voter ID" />

    </div>
  );
}

export default VoterIDPage;
