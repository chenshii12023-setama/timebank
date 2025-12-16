import React from 'react';
import { Clock, MapPin, Heart, Star } from 'lucide-react';
import { Task } from '../types';

interface TaskCardProps {
  task: Task;
  onClick: () => void;
}

const TaskCard: React.FC<TaskCardProps> = ({ task, onClick }) => {
  return (
    <div 
      onClick={onClick}
      className="bg-white p-4 rounded-xl shadow-sm border border-gray-100 mb-4 active:scale-[0.98] transition-transform duration-100 cursor-pointer"
    >
      <div className="flex justify-between items-start mb-3">
        <div className="flex items-center space-x-2">
          <img src={task.author.avatar} alt={task.author.nickname} className="w-8 h-8 rounded-full object-cover" />
          <div>
            <p className="text-xs font-semibold text-gray-800 flex items-center">
              {task.author.nickname}
              {task.author.verified && <span className="ml-1 text-blue-500 text-[10px]">✓</span>}
            </p>
            <div className="flex items-center text-[10px] text-orange-500 font-medium">
               <Star size={10} className="fill-current mr-0.5" />
               {task.author.rating.toFixed(1)}
               <span className="text-gray-400 font-normal ml-2">{task.createdAt}</span>
            </div>
          </div>
        </div>
        <div className="flex items-center space-x-1 bg-teal-50 px-2 py-1 rounded-lg">
          <Clock size={12} className="text-teal-600" />
          <span className="text-sm font-bold text-teal-700">{task.coins} 币</span>
        </div>
      </div>

      <h3 className="font-bold text-gray-900 mb-1">{task.title}</h3>
      <p className="text-sm text-gray-600 line-clamp-2 mb-3">{task.description}</p>

      <div className="flex items-center justify-between">
        <div className="flex flex-wrap gap-2">
          {task.isCharity && (
             <span className="text-[10px] px-2 py-0.5 bg-orange-100 text-orange-600 rounded-full font-medium flex items-center">
               <Heart size={8} className="mr-1 fill-current" /> 公益
             </span>
          )}
          <span className="text-[10px] px-2 py-0.5 bg-gray-100 text-gray-600 rounded-full flex items-center">
            <MapPin size={8} className="mr-1" /> {task.location}
          </span>
        </div>
      </div>
    </div>
  );
};

export default TaskCard;