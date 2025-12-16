import React, { useState } from 'react';
import { ArrowLeft, Clock, MapPin, Tag } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const Publish: React.FC = () => {
  const navigate = useNavigate();
  const [coins, setCoins] = useState(1);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // In a real app, this would submit to API
    navigate('/');
  };

  return (
    <div className="bg-white min-h-screen">
      {/* Header */}
      <div className="px-4 py-4 flex items-center border-b border-gray-100 sticky top-0 bg-white z-10">
        <button onClick={() => navigate(-1)} className="p-2 -ml-2 text-gray-600">
          <ArrowLeft size={24} />
        </button>
        <h1 className="ml-2 text-lg font-bold text-slate-800">发布需求</h1>
      </div>

      <form onSubmit={handleSubmit} className="p-6 space-y-6">
        
        {/* Title */}
        <div className="space-y-2">
          <label className="text-sm font-semibold text-gray-700">您需要什么帮助？</label>
          <input 
            type="text" 
            placeholder="例如：修打印机，帮忙遛狗" 
            className="w-full text-lg border-b border-gray-200 py-2 outline-none focus:border-teal-500 placeholder:text-gray-300 transition-colors"
          />
        </div>

        {/* Description */}
        <div className="space-y-2">
          <label className="text-sm font-semibold text-gray-700">描述</label>
          <textarea 
            rows={4}
            placeholder="描述详情、要求以及所需工具..." 
            className="w-full bg-gray-50 rounded-xl p-4 text-sm outline-none focus:ring-2 focus:ring-teal-100 transition-all resize-none"
          />
        </div>

        {/* Location */}
        <div className="space-y-2">
          <label className="text-sm font-semibold text-gray-700 flex items-center">
            <MapPin size={16} className="mr-1 text-gray-400" /> 地点
          </label>
          <input 
            type="text" 
            defaultValue="当前位置"
            className="w-full bg-gray-50 rounded-lg px-4 py-3 text-sm outline-none"
          />
        </div>

        {/* Time Coins & Duration */}
        <div className="grid grid-cols-2 gap-4">
          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700 flex items-center">
              <Clock size={16} className="mr-1 text-gray-400" /> 悬赏 (时间币)
            </label>
            <div className="flex items-center space-x-3">
              <button 
                type="button"
                onClick={() => setCoins(Math.max(0.5, coins - 0.5))}
                className="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 font-bold"
              >
                -
              </button>
              <span className="text-xl font-bold text-teal-600">{coins}</span>
              <button 
                type="button"
                onClick={() => setCoins(coins + 0.5)}
                className="w-8 h-8 rounded-full bg-teal-100 flex items-center justify-center text-teal-700 font-bold"
              >
                +
              </button>
            </div>
          </div>
          
          <div className="space-y-2">
             <label className="text-sm font-semibold text-gray-700 flex items-center">
              <Tag size={16} className="mr-1 text-gray-400" /> 分类
            </label>
            <select className="w-full bg-gray-50 rounded-lg px-2 py-3 text-sm outline-none text-gray-600">
              <option>通用求助</option>
              <option>技术支持</option>
              <option>教育辅导</option>
              <option>家庭生活</option>
            </select>
          </div>
        </div>

        {/* Submit Button */}
        <div className="pt-6">
          <button 
            type="submit"
            className="w-full bg-teal-600 text-white font-semibold py-4 rounded-xl shadow-lg hover:bg-teal-700 active:scale-95 transition-all"
          >
            发布任务
          </button>
          <p className="text-center text-xs text-gray-400 mt-4">
            发布即表示您同意我们的社区准则。
          </p>
        </div>

      </form>
    </div>
  );
};

export default Publish;