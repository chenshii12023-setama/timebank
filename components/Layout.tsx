import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Home, PlusSquare, MessageSquare, User, Users } from 'lucide-react';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const navigate = useNavigate();
  const location = useLocation();

  const isActive = (path: string) => location.pathname === path;

  return (
    <div className="min-h-screen bg-gray-50 flex justify-center">
      {/* Mobile Container Simulation for Desktop Viewers */}
      <div className="w-full md:max-w-md bg-white min-h-screen shadow-2xl relative flex flex-col">
        
        {/* Main Content Area */}
        <main className="flex-1 overflow-y-auto pb-20 no-scrollbar">
          {children}
        </main>

        {/* Bottom Navigation */}
        <nav className="fixed bottom-0 w-full md:max-w-md bg-white border-t border-gray-100 px-6 py-3 flex justify-between items-center z-50 safe-area-bottom">
          <button 
            onClick={() => navigate('/')}
            className={`flex flex-col items-center space-y-1 ${isActive('/') ? 'text-teal-600' : 'text-gray-400'}`}
          >
            <Home size={24} strokeWidth={isActive('/') ? 2.5 : 2} />
            <span className="text-[10px] font-medium">大厅</span>
          </button>

          <button 
            onClick={() => navigate('/community')}
            className={`flex flex-col items-center space-y-1 ${isActive('/community') ? 'text-teal-600' : 'text-gray-400'}`}
          >
            <Users size={24} strokeWidth={isActive('/community') ? 2.5 : 2} />
            <span className="text-[10px] font-medium">社区</span>
          </button>

          <button 
            onClick={() => navigate('/publish')}
            className="flex flex-col items-center -mt-8"
          >
            <div className="bg-teal-600 text-white p-4 rounded-full shadow-lg hover:bg-teal-700 transition-colors">
              <PlusSquare size={28} />
            </div>
            <span className="text-[10px] font-medium text-gray-500 mt-1">发布</span>
          </button>

          <button 
            onClick={() => navigate('/messages')}
            className={`flex flex-col items-center space-y-1 ${isActive('/messages') ? 'text-teal-600' : 'text-gray-400'}`}
          >
            <MessageSquare size={24} strokeWidth={isActive('/messages') ? 2.5 : 2} />
            <span className="text-[10px] font-medium">消息</span>
          </button>

          <button 
            onClick={() => navigate('/profile')}
            className={`flex flex-col items-center space-y-1 ${isActive('/profile') ? 'text-teal-600' : 'text-gray-400'}`}
          >
            <User size={24} strokeWidth={isActive('/profile') ? 2.5 : 2} />
            <span className="text-[10px] font-medium">我的</span>
          </button>
        </nav>
      </div>
    </div>
  );
};

export default Layout;