import React from 'react';
import { HashRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Home from './pages/Home';
import Publish from './pages/Publish';
import Profile from './pages/Profile';
import Community from './pages/Community';
import TaskDetail from './pages/TaskDetail';
import Messages from './pages/Messages';
import Arbitration from './pages/Arbitration';
import MyTasks from './pages/MyTasks';

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        {/* Pages with Main Bottom Navigation */}
        <Route path="/" element={<Layout><Home /></Layout>} />
        <Route path="/community" element={<Layout><Community /></Layout>} />
        <Route path="/messages" element={<Layout><Messages /></Layout>} />
        <Route path="/profile" element={<Layout><Profile /></Layout>} />
        
        {/* Full Screen Pages (No Bottom Nav) */}
        <Route path="/publish" element={<Publish />} />
        <Route path="/task/:id" element={<TaskDetail />} />
        <Route path="/arbitration" element={<Arbitration />} />
        <Route path="/my-tasks" element={<MyTasks />} />
      </Routes>
    </Router>
  );
};

export default App;