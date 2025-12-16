import React, { useState } from 'react';
import { ArrowLeft, UploadCloud } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const Arbitration: React.FC = () => {
  const navigate = useNavigate();
  const [submitted, setSubmitted] = useState(false);

  if (submitted) {
    return (
      <div className="min-h-screen bg-white p-6 flex flex-col items-center justify-center text-center">
        <div className="w-20 h-20 bg-green-100 rounded-full flex items-center justify-center text-green-600 mb-6">
          <svg className="w-10 h-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
          </svg>
        </div>
        <h2 className="text-2xl font-bold text-gray-900 mb-2">申请已提交</h2>
        <p className="text-gray-600 mb-8">我们的仲裁团队将在48小时内审核并联系您。</p>
        <button onClick={() => navigate('/profile')} className="text-teal-600 font-bold hover:underline">
          返回个人中心
        </button>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="bg-white px-4 py-4 flex items-center border-b border-gray-100 sticky top-0">
        <button onClick={() => navigate(-1)} className="p-2 -ml-2 text-gray-600">
          <ArrowLeft size={24} />
        </button>
        <h1 className="ml-2 text-lg font-bold text-slate-800">争议仲裁中心</h1>
      </div>

      <div className="p-6">
        <div className="bg-blue-50 p-4 rounded-xl text-sm text-blue-700 mb-6 leading-relaxed">
          请提供清晰的证据。虚假申诉可能导致您的信用分降低。
        </div>

        <form className="space-y-6" onSubmit={(e) => { e.preventDefault(); setSubmitted(true); }}>
          
          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700">订单编号</label>
            <input 
              type="text" 
              placeholder="#123456" 
              className="w-full bg-white border border-gray-200 rounded-lg px-4 py-3 outline-none focus:border-red-400"
            />
          </div>

          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700">申诉理由</label>
            <select className="w-full bg-white border border-gray-200 rounded-lg px-4 py-3 outline-none text-gray-600">
               <option>未完成任务</option>
               <option>服务质量问题</option>
               <option>支付/结算纠纷</option>
               <option>骚扰/安全问题</option>
            </select>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700">详细描述</label>
            <textarea 
              rows={4}
              placeholder="请描述具体发生了什么..." 
              className="w-full bg-white border border-gray-200 rounded-lg px-4 py-3 outline-none resize-none"
            />
          </div>

          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700">证据 (截图/照片)</label>
            <div className="border-2 border-dashed border-gray-300 rounded-xl p-8 flex flex-col items-center justify-center text-gray-400 bg-white cursor-pointer hover:bg-gray-50 transition-colors relative">
               <input type="file" className="absolute inset-0 opacity-0 w-full h-full cursor-pointer" multiple />
               <UploadCloud size={32} className="mb-2" />
               <span className="text-xs">点击上传</span>
            </div>
          </div>

          <button className="w-full bg-red-500 text-white font-bold py-4 rounded-xl shadow-lg hover:bg-red-600 mt-4">
            提交申诉
          </button>
        </form>
      </div>
    </div>
  );
};

export default Arbitration;