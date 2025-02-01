function Footer () {
  const currentYear = new Date().getFullYear();
  return (
    <footer style={{ position: 'relative', width: '100%', padding: '10px 0'}}>
      <p className="footer-text">&copy; {currentYear} Election Commission of India</p>
    </footer>
  );
};

export default Footer;

