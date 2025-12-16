import React, { useState } from 'react';
import { Search, SlidersHorizontal, Bell } from 'lucide-react';
import TaskCard from '../components/TaskCard';
import { MOCK_TASKS } from '../constants';
import { useNavigate } from 'react-router-dom';

const Home: React.FC = () => {
  const navigate = useNavigate();
  const [moduleType, setModuleType] = useState<'general' | 'charity'>('general');
  const [filter, setFilter] = useState('全部');

  const generalFilters = ['全部', '家政', '数码', '教育', '跑腿'];
  
  // Filter Logic
  const filteredTasks = MOCK_TASKS.filter(task => {
    // 1. Module Filter
    if (moduleType === 'general' && task.isCharity) return false;
    if (moduleType === 'charity' && !task.isCharity) return false;

    // 2. Category Filter (Only for general module for now)
    if (moduleType === 'general' && filter !== '全部') {
       // Mock category matching logic
       // In real app, check task.category === filter
       return true; 
    }
    return true;
  });

  return (
    <div className="p-4 flex flex-col h-full">
      {/* Header with Module Toggle */}
      <header className="flex justify-between items-center mb-4 pt-2">
        <div className="flex space-x-4">
          <button 
            onClick={() => setModuleType('general')}
            className={`text-lg font-bold transition-colors ${moduleType === 'general' ? 'text-slate-800 scale-105' : 'text-slate-400'}`}
          >
            任务大厅
            {moduleType === 'general' && <div className="h-1 w-6 bg-teal-600 rounded-full mt-1"></div>}
          </button>
          <button 
            onClick={() => setModuleType('charity')}
            className={`text-lg font-bold transition-colors ${moduleType === 'charity' ? 'text-slate-800 scale-105' : 'text-slate-400'}`}
          >
            公益项目
            {moduleType === 'charity' && <div className="h-1 w-6 bg-orange-500 rounded-full mt-1"></div>}
          </button>
        </div>
        <button className="p-2 bg-white rounded-full shadow-sm text-slate-600 relative">
          <Bell size={20} />
          <span className="absolute top-2 right-2 w-2 h-2 bg-red-500 rounded-full border border-white"></span>
        </button>
      </header>

      {/* Module Description / Banner (Optional) */}
      {moduleType === 'charity' && (
        <div className="bg-orange-50 border border-orange-100 rounded-xl p-3 mb-4 flex items-center justify-between">
           <div>
             <h3 className="text-sm font-bold text-orange-800">爱心公益</h3>
             <p className="text-xs text-orange-600">参与志愿服务，传递社区温暖</p>
           </div>
           <div className="text-2xl">🎗️</div>
        </div>
      )}

      {/* Search Bar */}
      <div className="flex space-x-2 mb-4">
        <div className="flex-1 bg-white flex items-center px-4 py-3 rounded-xl shadow-sm border border-gray-100">
          <Search size={18} className="text-gray-400 mr-2" />
          <input 
            type="text" 
            placeholder={moduleType === 'charity' ? "搜索公益活动..." : "搜索技能、任务..."}
            className="flex-1 bg-transparent outline-none text-sm text-gray-700" 
          />
        </div>
        <button className={`${moduleType === 'charity' ? 'bg-orange-500' : 'bg-teal-600'} text-white px-4 rounded-xl shadow-sm hover:opacity-90 flex items-center justify-center transition-colors`}>
          <SlidersHorizontal size={18} />
        </button>
      </div>

      {/* Categories / Filters (Only for General) */}
      {moduleType === 'general' && (
        <div className="flex space-x-2 overflow-x-auto pb-4 mb-2 no-scrollbar">
          {generalFilters.map((f) => (
            <button
              key={f}
              onClick={() => setFilter(f)}
              className={`px-4 py-2 rounded-full text-xs font-medium whitespace-nowrap transition-colors ${
                filter === f 
                  ? 'bg-teal-600 text-white shadow-md' 
                  : 'bg-white text-gray-600 border border-gray-200'
              }`}
            >
              {f}
            </button>
          ))}
        </div>
      )}

      {/* Task List */}
      <div className="space-y-2 pb-20">
        <h2 className="text-sm font-semibold text-slate-700 mb-2">
            {moduleType === 'general' ? '为你推荐' : '最新活动'}
        </h2>
        
        {filteredTasks.length > 0 ? (
          filteredTasks.map((task) => (
            <TaskCard 
              key={task.id} 
              task={task} 
              onClick={() => navigate(`/task/${task.id}`)} 
            />
          ))
        ) : (
          <div className="text-center py-10 text-gray-400 text-sm">
            暂无相关{moduleType === 'general' ? '任务' : '公益活动'}
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;