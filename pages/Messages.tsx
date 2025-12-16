import React from 'react';

const Messages: React.FC = () => {
  return (
    <div className="p-4 bg-white min-h-screen">
      <h1 className="text-xl font-bold text-slate-800 mb-6">消息</h1>
      
      <div className="flex flex-col items-center justify-center h-64 text-center">
        <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mb-4">
          <span className="text-2xl">📭</span>
        </div>
        <h3 className="text-gray-900 font-medium mb-1">暂无消息</h3>
        <p className="text-sm text-gray-500 max-w-xs">当您接受任务或有人接受您的任务时，可以在此聊天。</p>
      </div>
    </div>
  );
};

export default Messages;