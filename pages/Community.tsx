import React from 'react';
import { MessageCircle, Heart, Share2 } from 'lucide-react';
import { MOCK_POSTS } from '../constants';

const Community: React.FC = () => {
  return (
    <div className="bg-gray-50 min-h-screen p-4 space-y-4">
      <header className="flex justify-between items-center mb-2">
        <h1 className="text-xl font-bold text-slate-800">社区</h1>
        <button className="text-sm text-teal-600 font-medium">发帖</button>
      </header>

      {/* Topics */}
      <div className="flex space-x-3 overflow-x-auto pb-2 no-scrollbar">
        {['#分享', '#求助', '#活动', '#问答'].map(tag => (
          <div key={tag} className="bg-white px-4 py-2 rounded-lg text-sm font-medium text-gray-600 shadow-sm whitespace-nowrap border border-gray-100">
            {tag}
          </div>
        ))}
      </div>

      {/* Feed */}
      {MOCK_POSTS.map(post => (
        <div key={post.id} className="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
          <div className="flex items-center space-x-3 mb-3">
            <img src={post.author.avatar} className="w-10 h-10 rounded-full" alt="avatar" />
            <div>
              <p className="text-sm font-bold text-gray-900">{post.author.nickname}</p>
              <p className="text-xs text-gray-400">{post.date}</p>
            </div>
          </div>
          
          <p className="text-gray-700 text-sm mb-3 leading-relaxed">
            {post.content}
          </p>

          <div className="flex gap-2 mb-3">
            {post.tags.map(tag => (
              <span key={tag} className="text-xs text-teal-600 bg-teal-50 px-2 py-0.5 rounded">#{tag}</span>
            ))}
          </div>

          <div className="flex items-center justify-between pt-3 border-t border-gray-50 text-gray-400">
             <button className="flex items-center space-x-1 hover:text-red-500 transition-colors">
               <Heart size={18} /> <span className="text-xs">{post.likes}</span>
             </button>
             <button className="flex items-center space-x-1 hover:text-blue-500 transition-colors">
               <MessageCircle size={18} /> <span className="text-xs">{post.comments}</span>
             </button>
             <button className="flex items-center space-x-1 hover:text-gray-600">
               <Share2 size={18} />
             </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Community;