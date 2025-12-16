import React, { useState } from 'react';
import { ArrowLeft, Star, Clock, CheckCircle, AlertCircle } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { MOCK_TASKS, CURRENT_USER } from '../constants';
import { TaskStatus } from '../types';

const MyTasks: React.FC = () => {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState<'published' | 'accepted'>('accepted');

  // In a real app, query based on IDs. Here we filter mock data.
  // For 'accepted', we include the specific mock task we created for history
  const tasks = activeTab === 'accepted' 
    ? MOCK_TASKS.filter(t => t.assignee?.id === CURRENT_USER.id)
    : MOCK_TASKS.filter(t => t.author.id === CURRENT_USER.id);

  // Add a dummy task if list is empty for demonstration
  const displayTasks = tasks.length > 0 ? tasks : [];

  const getStatusColor = (status: TaskStatus) => {
    switch (status) {
      case TaskStatus.COMPLETED: return 'text-green-600 bg-green-50';
      case TaskStatus.IN_PROGRESS: return 'text-blue-600 bg-blue-50';
      default: return 'text-orange-600 bg-orange-50';
    }
  };

  const getStatusText = (status: TaskStatus) => {
     switch (status) {
      case TaskStatus.COMPLETED: return '已完成';
      case TaskStatus.IN_PROGRESS: return '进行中';
      case TaskStatus.ACCEPTED: return '已接单';
      default: return '待接单';
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="bg-white px-4 py-4 flex items-center border-b border-gray-100 sticky top-0 z-10">
        <button onClick={() => navigate(-1)} className="p-2 -ml-2 text-gray-600">
          <ArrowLeft size={24} />
        </button>
        <h1 className="ml-2 text-lg font-bold text-slate-800">我的任务 & 评价</h1>
      </div>

      <div className="bg-white px-4 pt-2 pb-0">
        <div className="flex border-b border-gray-100">
           <button 
             onClick={() => setActiveTab('accepted')}
             className={`flex-1 py-3 text-sm font-medium border-b-2 transition-colors ${activeTab === 'accepted' ? 'border-teal-600 text-teal-600' : 'border-transparent text-gray-500'}`}
           >
             我接受的
           </button>
           <button 
             onClick={() => setActiveTab('published')}
             className={`flex-1 py-3 text-sm font-medium border-b-2 transition-colors ${activeTab === 'published' ? 'border-teal-600 text-teal-600' : 'border-transparent text-gray-500'}`}
           >
             我发布的
           </button>
        </div>
      </div>

      <div className="p-4 space-y-4">
        {displayTasks.length === 0 ? (
           <div className="text-center py-20 text-gray-400 text-sm">
             <Clock size={40} className="mx-auto mb-2 opacity-30" />
             暂无记录
           </div>
        ) : (
          displayTasks.map(task => (
            <div key={task.id} className="bg-white rounded-xl shadow-sm p-4 border border-gray-100">
              <div className="flex justify-between items-start mb-2">
                <h3 className="font-bold text-gray-800 text-sm">{task.title}</h3>
                <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${getStatusColor(task.status)}`}>
                  {getStatusText(task.status)}
                </span>
              </div>
              
              <div className="flex justify-between items-end text-xs text-gray-500 mb-3">
                 <span>{task.createdAt}</span>
                 <span className="font-bold text-teal-600">{task.coins} 币</span>
              </div>

              {/* Rating History Section */}
              {task.status === TaskStatus.COMPLETED && task.rating && (
                <div className="bg-gray-50 rounded-lg p-3 mt-2 border border-gray-100">
                  <div className="flex items-center justify-between mb-1">
                    <span className="text-xs font-bold text-gray-600">获得评价</span>
                    <div className="flex text-orange-400">
                      {[...Array(5)].map((_, i) => (
                        <Star key={i} size={12} className={i < task.rating! ? "fill-current" : "text-gray-300"} />
                      ))}
                    </div>
                  </div>
                  <p className="text-xs text-gray-600 italic">"{task.review}"</p>
                </div>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default MyTasks;