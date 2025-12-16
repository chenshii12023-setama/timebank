import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, Clock, MapPin, Star, Share2, MoreHorizontal } from 'lucide-react';
import { MOCK_TASKS } from '../constants';
import { TaskStatus } from '../types';

const TaskDetail: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const task = MOCK_TASKS.find(t => t.id === id) || MOCK_TASKS[0]; // Fallback for demo

  const [status, setStatus] = useState<TaskStatus>(task.status);

  const handleAction = () => {
    if (status === TaskStatus.PENDING) {
      setStatus(TaskStatus.IN_PROGRESS);
    } else if (status === TaskStatus.IN_PROGRESS) {
      setStatus(TaskStatus.COMPLETED);
    }
  };

  const getButtonText = () => {
    switch (status) {
      case TaskStatus.PENDING: return '接受任务';
      case TaskStatus.IN_PROGRESS: return '完成任务';
      case TaskStatus.COMPLETED: return '任务已完成';
      default: return '联系客服';
    }
  };

  return (
    <div className="min-h-screen bg-white pb-24">
      {/* Header */}
      <div className="sticky top-0 bg-white/95 backdrop-blur-sm z-10 px-4 py-3 flex justify-between items-center border-b border-gray-100">
        <button onClick={() => navigate(-1)} className="p-2 -ml-2 text-gray-600">
          <ArrowLeft size={24} />
        </button>
        <div className="flex space-x-2">
          <button className="p-2 text-gray-600"><Share2 size={20} /></button>
          <button className="p-2 text-gray-600"><MoreHorizontal size={20} /></button>
        </div>
      </div>

      <div className="p-5 space-y-6">
        {/* Title & Price */}
        <div>
          <div className="flex items-start justify-between mb-2">
             <span className={`text-xs font-bold px-2 py-1 rounded text-white ${task.isCharity ? 'bg-orange-500' : 'bg-teal-500'}`}>
               {task.isCharity ? '公益活动' : '个人需求'}
             </span>
             <div className="flex items-center text-teal-600 font-bold text-xl">
               <Clock size={20} className="mr-1" />
               {task.coins} 币
             </div>
          </div>
          <h1 className="text-2xl font-bold text-gray-900 leading-tight mb-4">{task.title}</h1>
          <div className="flex items-center text-gray-500 text-sm">
             <MapPin size={16} className="mr-1" />
             {task.location}
          </div>
        </div>

        {/* Author Card */}
        <div className="bg-gray-50 p-4 rounded-xl flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <img src={task.author.avatar} alt={task.author.nickname} className="w-12 h-12 rounded-full" />
            <div>
              <p className="text-sm font-bold text-gray-900">{task.author.nickname}</p>
              <div className="flex items-center text-xs text-orange-500 font-bold mt-1">
                <Star size={12} className="fill-current mr-1" />
                {task.author.rating}
              </div>
            </div>
          </div>
          <button className="text-teal-600 text-sm font-medium border border-teal-200 px-3 py-1 rounded-full">
            联系
          </button>
        </div>

        {/* Details */}
        <div>
          <h3 className="text-sm font-bold text-gray-900 mb-2">详情描述</h3>
          <p className="text-gray-600 text-sm leading-relaxed whitespace-pre-line">
            {task.description}
          </p>
        </div>

        <div>
          <h3 className="text-sm font-bold text-gray-900 mb-2">所需技能</h3>
          <div className="flex flex-wrap gap-2">
            {task.tags.map(tag => (
              <span key={tag} className="bg-gray-100 text-gray-600 px-3 py-1 rounded-full text-xs font-medium">
                #{tag}
              </span>
            ))}
          </div>
        </div>
      </div>

      {/* Floating Action Bar */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 p-4 md:max-w-md md:mx-auto">
        <button 
          onClick={handleAction}
          disabled={status === TaskStatus.COMPLETED}
          className={`w-full py-4 rounded-xl font-bold text-white shadow-lg transition-all ${
            status === TaskStatus.COMPLETED ? 'bg-gray-400 cursor-not-allowed' : 'bg-teal-600 hover:bg-teal-700 active:scale-95'
          }`}
        >
          {getButtonText()}
        </button>
      </div>
    </div>
  );
};

export default TaskDetail;